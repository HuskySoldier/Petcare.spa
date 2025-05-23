package com.example.Inventario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventario.model.Inventario;
import com.example.Inventario.service.InventarioService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventario(@PathVariable("id") Long idInventario) {
        Inventario inventario = inventarioService.obtenerInventarioPorId(idInventario).get();
        if (inventario == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventario);
    }

    //
    @GetMapping("/total")
    public ResponseEntity<List<Inventario>> getInventarioTotal() {
        List<Inventario> inventario = inventarioService.allInventario();
        if (inventario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventario);
    }

    //
    @PostMapping
    public ResponseEntity<?> crearInventario(@RequestBody Inventario inventario) {
        try {
            Inventario inventarioCreado = inventarioService.crearInventario(inventario);

            //Verificar stock después de guardar
            inventarioService.verificarYReportarStock(inventarioCreado);

            return ResponseEntity.status(201).body(inventarioCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInventario(@PathVariable("id") Long idInventario,
            @RequestBody Inventario inventarioActualizado) {
        try {
            inventarioService.actualizarInventario(idInventario, inventarioActualizado);
            return ResponseEntity.ok("Inventario ha sido actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Eliminamos el inventario que deseemos
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInventario(@PathVariable("id") Long idInventario) {
        try {
            inventarioService.deleteInventario(idInventario);
            return ResponseEntity.ok("Inventario eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}