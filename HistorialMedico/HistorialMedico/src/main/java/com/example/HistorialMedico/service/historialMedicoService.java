package com.example.HistorialMedico.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.repository.historialMedicoRepository;

@Service
public class historialMedicoService {
    @Autowired
    private historialMedicoRepository historialmedicorepository;

    public List<HistorialMedico> listarHistorialMedico(){
        return historialmedicorepository.findAll();
    }

    public HistorialMedico buscarHistorialMedicoPorId(Long id){
        return historialmedicorepository.findById(id).get();
    }

    public HistorialMedico agregarHistorialMedico(HistorialMedico historialmedico){
        return historialmedicorepository.save(historialmedico);
    }

    public void eliminarHistorialMedico(Long id){
        historialmedicorepository.deleteById(id);
    }
    


}
