package com.example.Reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.Reserva.model.Reserva;
import com.example.Reserva.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository ReservaRepository;

    public List<Reserva> listarReservas() {
        return ReservaRepository.findAll();
    }

    public Reserva buscarReservaPorId(Long id) {
        return ReservaRepository.findById(id).orElse(null);
    }

    public Reserva crearReserva(Reserva reserva) {
        if (reserva == null) {
            throw new RuntimeException("No se ah creado la Reserva");
        }
        return ReservaRepository.save(reserva);
    }

    public Reserva actualizarReserva(Long id, Reserva nuevaReserva) {
        return ReservaRepository.findById(id).map(reserva -> {
            reserva.setFechaCreacion(nuevaReserva.getFechaCreacion());
            reserva.setFechareserva(nuevaReserva.getFechareserva());
            reserva.setTotal(nuevaReserva.getTotal());
            reserva.setEstado(nuevaReserva.getEstado());
            return ReservaRepository.save(reserva);
        }).orElse(null);
    }

    public void eliminarReserva(Long id) {
        ReservaRepository.deleteById(id);
    }
}
