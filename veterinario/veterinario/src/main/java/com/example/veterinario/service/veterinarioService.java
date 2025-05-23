package com.example.veterinario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.veterinario.model.Veterinario;
import com.example.veterinario.repository.veterinarioRepository;

@Service
public class veterinarioService {
    @Autowired
    private veterinarioRepository veterinariorepository;

    public List<Veterinario>listarVeterinarios(){
        return veterinariorepository.findAll();
    }

    public Veterinario buscarVeterinarioPorId(Long id){
        return veterinariorepository.findById(id).orElse(null);
    }

    public Veterinario agregarVeterinario(Veterinario veterinario){
        return veterinariorepository.save(veterinario);
    }

    public void eliminarVeterinario (Long id){
        veterinariorepository.deleteById(id);
    }


}
