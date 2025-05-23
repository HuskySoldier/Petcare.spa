package com.example.mascota.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mascota.model.Raza;
import com.example.mascota.repository.razaRepository;

@Service
public class razaService {
    @Autowired
    private razaRepository razarepository;

    public List<Raza>listarRaza(){
        return razarepository.findAll();
    }

    public Raza buscarRaza(Long id){
        return razarepository.findById(id).get();
    }

    public Raza agregarRaza (Raza raza){
        return razarepository.save(raza);

    }

    public void eliminarRaza (Long id){
        razarepository.deleteById(id);
    }


}
