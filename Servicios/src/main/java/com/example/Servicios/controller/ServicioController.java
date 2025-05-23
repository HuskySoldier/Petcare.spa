package com.example.Servicios.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.model.Servicio;
import com.example.Servicios.service.CategoriaService;
import com.example.Servicios.service.ServicioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/servicio")
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping
    public ResponseEntity<List<Servicio>> getServicio(){
        List<Servicio> servicio = servicioService.allServicio();
        if(servicio.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicio);
    }

    @PostMapping
    public ResponseEntity<?> crearServicio(@RequestBody Servicio servicio){
        try {
            Servicio servicio2 = servicioService.crearServicio(servicio);
            return ResponseEntity.status(201).body(servicio2);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(categoria));
    }

    @GetMapping("/categoria")
    public ResponseEntity<List<Categoria>> listarCategoria() {
        return ResponseEntity.ok(categoriaService.listarCategoria());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(@PathVariable("id") Long idServicio, @RequestBody Servicio servicioActualizado) {
        try{
            servicioService.actualizarServicio(idServicio, servicioActualizado);
            return ResponseEntity.ok("Servicio ha sido actualizado correctamente");
        } catch (RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable("id") Long idServicio) {
        try{
            servicioService.deleteServicio(idServicio);
            return ResponseEntity.ok("Servicio eliminado correctamente");
        } catch (RuntimeException e){
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
