package com.example.Categoria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Categoria.model.Categoria;
import com.example.Categoria.service.CategoriaService;

@RestController
@RequestMapping("/api/v1/categoria")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<Categoria>> getCategoria() {
        List<Categoria> categoria = categoriaService.allCategoria();
        if (categoria.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {
        try {
            Categoria categoria2 = categoriaService.crearCategoria(categoria);
            return ResponseEntity.status(201).body(categoria2);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(@PathVariable("id") Long idCategoria,
            @RequestBody Categoria categoriaActualizado) {
        try {
            categoriaService.actualizarCategoria(idCategoria, categoriaActualizado);
            return ResponseEntity.ok("Categoria ha sido actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable("id") Long idCategoria) {
        try {
            categoriaService.deleteCategoria(idCategoria);
            return ResponseEntity.ok("Categoria eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
