package com.petcare.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petcare.pet.model.Cita;
import com.petcare.pet.repository.CitaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    // Crear nueva cita
    public Cita crearCita(Cita cita) {
        return citaRepository.save(cita);
    }

    // Obtener todas las citas de un cliente
    public List<Cita> obtenerCitasPorCliente(String cliente) {
        return citaRepository.findByCliente(cliente);
    }

    // Obtener una cita por su ID
    public Optional<Cita> obtenerCitaPorId(Long id) {
        return citaRepository.findById(id);
    }

    // Actualizar una cita
    public Cita actualizarCita(Long id, Cita citaActualizada) {
        if (citaRepository.existsById(id)) {
            citaActualizada.setId(id);
            return citaRepository.save(citaActualizada);
        } else {
            throw new RuntimeException("Cita no encontrada");
        }
    }

    // Borrar una cita
    public void borrarCita(Long id) {
        if (citaRepository.existsById(id)) {
            citaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Cita no encontrada");
        }
    }

    // Reagendar cita
    public Cita reagendarCita(Long id, LocalDateTime nuevaFecha) {
        Optional<Cita> cita = citaRepository.findById(id);
        if (cita.isPresent()) {
            Cita citaExistente = cita.get();
            citaExistente.setFecha(nuevaFecha);
            return citaRepository.save(citaExistente);
        } else {
            throw new RuntimeException("Cita no encontrada");
        }
    }
}
