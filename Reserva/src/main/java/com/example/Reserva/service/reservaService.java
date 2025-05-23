package com.example.Reserva.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Reserva> obtenerReservaPorId(Long id) {
        return ReservaRepository.findById(id);
    }

    public Reserva crearReserva(Reserva reserva) {
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
