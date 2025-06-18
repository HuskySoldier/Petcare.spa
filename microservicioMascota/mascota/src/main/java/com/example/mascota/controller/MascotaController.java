package com.example.mascota.controller;

import com.example.mascota.model.Mascota;
import com.example.mascota.service.MascotaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    // Listar todas las mascotas
    @GetMapping
    public List<Mascota> listarTodas() {
        return mascotaService.listarMacotas();
    }

    // Buscar mascota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(mascotaService.buscarMascotaPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Crear nueva mascota
    @PostMapping
    public ResponseEntity<Mascota> crearMascota(@RequestBody Mascota mascota) {
        try {
            Mascota nueva = mascotaService.agregarMascota(mascota);
            return ResponseEntity.ok(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    // Eliminar mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            mascotaService.eliminarMascota(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    //  Listar mascotas por ID de usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Mascota>> listarPorUsuario(@PathVariable Long idUsuario) {
        List<Mascota> mascotas = mascotaService.obtenerPorIdUsuario(idUsuario);
        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mascotas);
    }
}
