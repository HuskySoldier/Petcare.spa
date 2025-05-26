package com.example.Reserva.controller;

import com.example.Reserva.model.Estado;
import com.example.Reserva.model.Reserva;
import com.example.Reserva.service.EstadoService;
import com.example.Reserva.service.ReservaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reserva")
public class ReservaController {

    @Autowired
    private ReservaService ReservaService;

    @Autowired
    private EstadoService EstadoService;
    //llama a todas las reservas
    @GetMapping("/Total")
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(ReservaService.listarReservas());
    }

    //los llama por id
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> buscarReservaPorId(@PathVariable Long id) {
        Reserva reserva = ReservaService.buscarReservaPorId(id);

        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reserva);
    }

    //este es para crear una nueva reserva
    @PostMapping("/crear")
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservaService.crearReserva(reserva));
    }

    // este es para crear un esatado
    @PostMapping("/estado")
    public ResponseEntity<Estado> crearEstado(@Valid @RequestBody Estado estado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(EstadoService.crearEstado(estado));
    }

    // este es para listar los estados
    @GetMapping("/estado")
    public ResponseEntity<List<Estado>> listarEstados() {
        return ResponseEntity.ok(EstadoService.listarEstados());
    }

    // este es para eliminar por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReservaPorId(@PathVariable Long id) {
        try {
            ReservaService.eliminarReserva(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("La reserva con ID " + id + " no existe.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar la reserva.");
        }
    }

}
