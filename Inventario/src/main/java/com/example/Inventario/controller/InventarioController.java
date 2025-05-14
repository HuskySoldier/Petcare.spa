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

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    //
    @GetMapping
    public ResponseEntity<List<Inventario>> getInventario(int id_inventario){
        List<Inventario> inventario = inventarioService.getInventario(id_inventario);
        if(inventario.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventario);
    }

    //
    @PostMapping
    public ResponseEntity<?> crearInventario(@RequestBody Inventario inventario){
        try {
            Inventario inventario2 = inventarioService.crearInventario(inventario);
            return ResponseEntity.status(201).body(inventario2);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }

    }

    //Eliminamos el inventario que deseemos
    @DeleteMapping("{id}")
    public ResponseEntity<?> eliminarInventario(@PathVariable Long id_inventario) {
        try{
            inventarioService.deleteInventario(id_inventario);
            return ResponseEntity.ok("Inventario eliminado correctamente");
        } catch (RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
