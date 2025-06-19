package com.example.Reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Reserva.client.UsuarioClient;
import com.example.Reserva.client.VeterinarioClient;
import com.example.Reserva.dto.UsuarioDTO;
import com.example.Reserva.dto.VeterinarioDTO;
import com.example.Reserva.model.Reserva;
import com.example.Reserva.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private VeterinarioClient veterinarioClient;

    @Autowired
    private UsuarioClient usuarioClient;

    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    public Reserva buscarReservaPorId(Long id) {
        return reservaRepository.findById(id).orElse(null);
    }

    public Reserva crearReserva(Reserva reserva) {
        if (reserva == null) {
            throw new RuntimeException("La reserva no puede ser nula");
        }

        // Validar que el veterinario exista
        VeterinarioDTO vet;
        try {
            vet = veterinarioClient.obtenerVeterinarioPorId(reserva.getVeterinarioId());
        } catch (Exception e) {
            throw new IllegalArgumentException("El veterinario no existe.");
        }

        // Validar que el correo del veterinario corresponda a un usuario válido
        try {
            UsuarioDTO usuario = usuarioClient.findByEmail(vet.getCorreo());
        } catch (Exception e) {
            throw new IllegalArgumentException("El usuario relacionado con el veterinario no existe.");
        }

        // Guardar la reserva si todo está bien
        return reservaRepository.save(reserva);
    }

    public Reserva actualizarReserva(Long id, Reserva nuevaReserva) {
        return reservaRepository.findById(id).map(reserva -> {
            reserva.setFechaCreacion(nuevaReserva.getFechaCreacion());
            reserva.setFechareserva(nuevaReserva.getFechareserva());
            reserva.setTotal(nuevaReserva.getTotal());
            reserva.setEstado(nuevaReserva.getEstado());
            return reservaRepository.save(reserva);
        }).orElse(null);
    }

    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}