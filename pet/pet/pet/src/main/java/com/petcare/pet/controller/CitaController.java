package com.petcare.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.petcare.pet.model.Cita;
import com.petcare.pet.service.CitaService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    // Crear cita
    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.crearCita(cita);
        return new ResponseEntity<>(nuevaCita, HttpStatus.CREATED);
    }

    // Obtener todas las citas de un cliente
    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<Cita>> obtenerCitasPorCliente(@PathVariable String cliente) {
        List<Cita> citas = citaService.obtenerCitasPorCliente(cliente);
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    // Obtener una cita por ID
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Long id) {
        Optional<Cita> cita = citaService.obtenerCitaPorId(id);
        return cita.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar una cita
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable Long id, @RequestBody Cita citaActualizada) {
        Cita cita = citaService.actualizarCita(id, citaActualizada);
        return new ResponseEntity<>(cita, HttpStatus.OK);
    }

    // Borrar una cita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrarCita(@PathVariable Long id) {
        citaService.borrarCita(id);
        return ResponseEntity.noContent().build();
    }

    // Reagendar cita
    @PutMapping("/reagendar/{id}")
    public ResponseEntity<Cita> reagendarCita(@PathVariable Long id, @RequestParam("fecha") String nuevaFechaStr) {
        LocalDateTime nuevaFecha = LocalDateTime.parse(nuevaFechaStr);
        Cita citaReagendada = citaService.reagendarCita(id, nuevaFecha);
        return new ResponseEntity<>(citaReagendada, HttpStatus.OK);
    }
}
