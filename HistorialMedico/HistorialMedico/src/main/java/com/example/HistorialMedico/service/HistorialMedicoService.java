package com.example.HistorialMedico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class HistorialMedicoService {
    @Autowired
    private HistorialMedicoRepository historialmedicorepository;

    public List<HistorialMedico> listarHistorialMedico() {
        return historialmedicorepository.findAll();
    }

    public HistorialMedico buscarHistorialMedicoPorId(Long id) {
        return historialmedicorepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));
    }

    public HistorialMedico agregarHistorialMedico(HistorialMedico historialmedico) {
        return historialmedicorepository.save(historialmedico);
    }

    public void eliminarHistorialMedico(Long id) {
        historialmedicorepository.deleteById(id);
    }

    public List<HistorialMedico> buscarPorIdMascota(Long idMascota) {
        return historialmedicorepository.findByIdMascota(idMascota);
    }
    

}
