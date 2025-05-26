package com.example.mascota.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascota.model.Mascota;
import com.example.mascota.repository.MascotaRepository;

@Service
public class MascotaService {
    @Autowired
    private MascotaRepository mascotarepository;

    public List<Mascota> listarMacotas() {
        return mascotarepository.findAll();
    }

    public Mascota agregarMascota(Mascota mascota) {
        if (mascota.getIdMascota() != null) {
            throw new IllegalArgumentException("No debe enviar el idMascota al crear una nueva mascota.");
        }

        if (mascota.getEspecie() != null && mascota.getEspecie().getIdEspecie() != null) {
            throw new IllegalArgumentException(
                    "No debe enviar el idEspecie dentro del objeto especie al crear una nueva mascota.");
        }

        if (mascota.getRaza() != null && mascota.getRaza().getIdRaza() != null) {
            throw new IllegalArgumentException(
                    "No debe enviar el idRaza dentro del objeto raza al crear una nueva mascota.");
        }

        return mascotarepository.save(mascota);
    }

    public Mascota buscarMascotaPorId(Long id) {
        return mascotarepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }

    public void eliminarMascota(Long id) {
        mascotarepository.deleteById(id);
    }

}
