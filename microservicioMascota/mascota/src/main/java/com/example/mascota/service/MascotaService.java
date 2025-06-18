package com.example.mascota.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascota.client.UsuarioClient;
import com.example.mascota.model.Especie;
import com.example.mascota.model.Mascota;
import com.example.mascota.model.Raza;
import com.example.mascota.repository.EspecieRepository;
import com.example.mascota.repository.MascotaRepository;
import com.example.mascota.repository.RazaRepository;

@Service
public class MascotaService {

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private RazaRepository razaRepository;

    @Autowired
    private EspecieRepository especieRepository;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Mascota> listarMacotas() {
        return mascotaRepository.findAll();
    }

    public Mascota agregarMascota(Mascota mascota) {
        if (mascota.getIdMascota() != null) {
            throw new IllegalArgumentException("No debe enviar el idMascota al crear una nueva mascota.");
        }

        if (mascota.getIdUsuario() == null) {
            throw new IllegalArgumentException("Debe enviar el id del usuario dueño de la mascota.");
        }

        // Validar que el usuario exista usando el microservicio de usuario
        try {
            usuarioClient.getUsuarioById(mascota.getIdUsuario());
        } catch (Exception e) {
            throw new RuntimeException("Usuario con ID " + mascota.getIdUsuario() + " no existe.");
        }

        if (mascota.getRaza() == null || mascota.getRaza().getNombreRaza() == null) {
            throw new IllegalArgumentException("Debe enviar el nombre de la raza.");
        }

        if (mascota.getEspecie() == null || mascota.getEspecie().getNombreEspecie() == null) {
            throw new IllegalArgumentException("Debe enviar el nombre de la especie.");
        }

        // Buscar o crear raza
        Raza raza = razaRepository.findByNombreRaza(mascota.getRaza().getNombreRaza())
                .orElseGet(() -> {
                    Raza nueva = new Raza();
                    nueva.setNombreRaza(mascota.getRaza().getNombreRaza());
                    return razaRepository.save(nueva);
                });

        // Buscar o crear especie
        Especie especie = especieRepository.findByNombreEspecie(mascota.getEspecie().getNombreEspecie())
                .orElseGet(() -> {
                    Especie nueva = new Especie();
                    nueva.setNombreEspecie(mascota.getEspecie().getNombreEspecie());
                    return especieRepository.save(nueva);
                });

        mascota.setRaza(raza);
        mascota.setEspecie(especie);

        return mascotaRepository.save(mascota);
    }

    public Mascota buscarMascotaPorId(Long id) {
        return mascotaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }

    public void eliminarMascota(Long id) {
        mascotaRepository.deleteById(id);
    }

    // Nuevo método para filtrar por usuario
    public List<Mascota> obtenerPorIdUsuario(Long idUsuario) {
        return mascotaRepository.findByIdUsuario(idUsuario);
    }
}
