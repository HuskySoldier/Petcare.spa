package com.example.Reportes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.repository.ReporteRepository;


@Service
public class ReporteService {
    @Autowired
    private ReporteRepository reporteRepository;
       
    // Crear nuevo reporte
    public Reporte crearReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public Optional<Reporte> obtenerReportePorId(Long idReporte) {
        return reporteRepository.findById(idReporte);
    }

    public List<Reporte> allReporte(){
    return reporteRepository.findAll();
    }

    // Actualizar el reporte
    public Reporte actualizarReporte(Long idReporte, Reporte reporteActualizada) {
        if (reporteRepository.existsById(idReporte)) {
            reporteActualizada.setIdReporte(idReporte);
            return reporteRepository.save(reporteActualizada);
        } else {
            throw new RuntimeException("reporte no encontrado");
        }
    }
    
}