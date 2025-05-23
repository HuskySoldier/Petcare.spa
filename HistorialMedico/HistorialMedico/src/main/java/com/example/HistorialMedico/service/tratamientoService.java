package com.example.HistorialMedico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.HistorialMedico.client.InventarioClient;
import com.example.HistorialMedico.dto.InventarioDTO;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.TratamientoRepository;


@Service
public class TratamientoService {


    @Autowired
    private TratamientoRepository tratamientorepository;

    @Autowired
    private  InventarioClient Inventario;

    public List<Tratamiento> listarTratamientos() {
        return tratamientorepository.findAll();
    }

    public Tratamiento guardarTratamiento(Tratamiento tratamiento) {
        return tratamientorepository.save(tratamiento);
    }

    public InventarioDTO obtenerInventarioPorTratamiento(Long tratamientoId) {
       Tratamiento tratamiento = tratamientorepository.findById(tratamientoId)
            .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));

        return Inventario.obtenerInventarioPorId(tratamiento.getIdInventario());
    }

    

}
