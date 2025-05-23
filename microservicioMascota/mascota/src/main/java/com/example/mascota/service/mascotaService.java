package com.example.mascota.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascota.model.Mascota;
import com.example.mascota.repository.mascotaRepository;

@Service
public class mascotaService {
    @Autowired
    private mascotaRepository mascotarepository;

    public List<Mascota>listarMacotas(){
        return mascotarepository.findAll();
    }

    public Mascota buscarMascotaPorId(Long id){
        return mascotarepository.findById(id).get();
        
    }

    public Mascota agregarMascota(Mascota mascota){
        return mascotarepository.save(mascota);
    }

    public void eliminarMascota (Long id){
        mascotarepository.deleteById(id);
    }
    


}
