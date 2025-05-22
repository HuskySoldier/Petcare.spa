package com.example.HistorialMedico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HistorialMedico.client.inventarioClient;
import com.example.HistorialMedico.dto.inventarioDTO;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.tratamientoRepository;


@Service
public class tratamientoService {


    @Autowired
    private tratamientoRepository tratamientorepository;

    @Autowired
    private  inventarioClient Inventario;

    public List<Tratamiento> listarTratamientos() {
        return tratamientorepository.findAll();
    }

    public Tratamiento guardarTratamiento(Tratamiento tratamiento) {
        return tratamientorepository.save(tratamiento);
    }

    public inventarioDTO obtenerInventarioPorTratamiento(Long tratamientoId) {
       Tratamiento tratamiento = tratamientorepository.findById(tratamientoId)
            .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));

        return Inventario.obtenerInventarioPorId(tratamiento.getIdInventario());
    }

    

}
