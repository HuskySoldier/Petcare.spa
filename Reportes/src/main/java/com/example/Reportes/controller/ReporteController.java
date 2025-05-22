package com.example.Reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.service.ReporteService;

@RestController
@RequestMapping("api/v1/reporte")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;


    @GetMapping
    public ResponseEntity<List<Reporte>> getReporte(){
        List<Reporte> reporte = reporteService.allReporte();
        if(reporte.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> crearReporte(@RequestBody Reporte reporte){
        try {
            Reporte reporte2 = reporteService.crearReporte(reporte);
            return ResponseEntity.status(201).body(reporte2);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
