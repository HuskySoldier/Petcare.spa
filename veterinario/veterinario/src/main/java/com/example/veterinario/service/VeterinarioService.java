package com.example.veterinario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.veterinario.model.Veterinario;
import com.example.veterinario.repository.VeterinarioRepository;

@Service
public class VeterinarioService {
    @Autowired
    private VeterinarioRepository veterinariorepository;

    public List<Veterinario> listarVeterinarios() {
        return veterinariorepository.findAll();
    }

    public Veterinario agregarVeterinario(Veterinario veterinario) {
        if (veterinario.getVeterinarioId() != null) {
            throw new IllegalArgumentException("No debe enviar el veterinarioId al crear un nuevo veterinario.");
        }
        return veterinariorepository.save(veterinario);
    }

    public Veterinario buscarVeterinarioPorId(Long id) {
        return veterinariorepository.findById(id).orElse(null);
    }

    public void eliminarVeterinario(Long id) {
        veterinariorepository.deleteById(id);
    }

}
