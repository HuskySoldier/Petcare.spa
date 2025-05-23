package com.example.Servicios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Servicios.model.Servicio;
import com.example.Servicios.repository.ServicioRepository;

@Service
public class ServicioService {
    @Autowired
    private ServicioRepository servicioRepository;

    public Servicio crearServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public List<Servicio> allServicio(){
        return servicioRepository.findAll();
    }

    public Servicio actualizarServicio(Long idServicio, Servicio servicioActualizada) {
        if (servicioRepository.existsById(idServicio)) {
            servicioActualizada.setIdServicio(idServicio);
            return servicioRepository.save(servicioActualizada);
        } else {
            throw new RuntimeException("Servicio no encontrado");
        }
    }

    // Borrar un Servicio
    public Boolean deleteServicio(Long idServicio) {
        if (!servicioRepository.existsById(idServicio)) {
            throw new RuntimeException("Servicio no encontrado");
        }
        servicioRepository.deleteById(idServicio);
        return true;
    }

}
