package com.example.Reserva.controller;

import com.example.Reserva.model.Estado;
import com.example.Reserva.model.Reserva;
import com.example.Reserva.service.estadoService;
import com.example.Reserva.service.reservaService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reserva")
public class reservaController {

    @Autowired
    private reservaService ReservaService;

    @Autowired
    private estadoService EstadoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Reserva>> listarReservas() {
        return ResponseEntity.ok(ReservaService.listarReservas());
    }

    @PostMapping("/crear")
    public ResponseEntity<Reserva> crearReserva(@Valid @RequestBody Reserva reserva) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ReservaService.crearReserva(reserva));
    }

    @PostMapping("/estado")
    public ResponseEntity<Estado> crearEstado(@Valid @RequestBody Estado estado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(EstadoService.crearEstado(estado));
    }

    @GetMapping("/estado")
    public ResponseEntity<List<Estado>> listarEstados() {
        return ResponseEntity.ok(EstadoService.listarEstados());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteResevaPorId(@PathVariable Long id){
        try { 
            ReservaService.eliminarReserva(id);
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
