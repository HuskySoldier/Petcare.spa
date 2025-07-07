package com.example.Reserva.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public Reserva crearReserva(Reserva reserva, Long usuarioIdHeader) {
        ResponseEntity<UsuarioDTO> usuarioResponse = usuarioClient.getUsuarioById(reserva.getUsuarioId());
        if (!usuarioResponse.getStatusCode().is2xxSuccessful() || usuarioResponse.getBody() == null) {
            throw new RuntimeException("El usuario no existe.");
        }
        UsuarioDTO usuario = usuarioResponse.getBody();
        if (!"CLIENTE".equalsIgnoreCase(usuario.getRol())) {
            throw new RuntimeException("Solo los usuarios con rol CLIENTE pueden crear reservas.");
        }

        ResponseEntity<VeterinarioDTO> vetResponse = veterinarioClient.obtenerVeterinarioPorId(
                reserva.getVeterinarioId(), usuarioIdHeader);
        if (!vetResponse.getStatusCode().is2xxSuccessful() || vetResponse.getBody() == null) {
            throw new RuntimeException("El veterinario no existe.");
        }

        return reservaRepository.save(reserva);
    }

    public List<VeterinarioDTO> obtenerTodosLosVeterinarios(Long usuarioId) {
        return veterinarioClient.listarVeterinarios(usuarioId);
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

    public List<Reserva> listarReservasPorUsuarioId(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }
}