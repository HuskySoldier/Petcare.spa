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

    public List<Mascota>listarMacotas(){
        return mascotarepository.findAll();
    }

    public Mascota buscarMascotaPorId(Long id){
    return mascotarepository.findById(id)
           .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));
    }

    public Mascota agregarMascota(Mascota mascota){
        return mascotarepository.save(mascota);
    }

    public void eliminarMascota (Long id){
        mascotarepository.deleteById(id);
    }

   
}
    



