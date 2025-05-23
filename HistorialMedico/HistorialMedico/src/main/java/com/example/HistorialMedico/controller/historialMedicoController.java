package com.example.HistorialMedico.controller;

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

import com.example.HistorialMedico.dto.inventarioDTO;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.service.historialMedicoService;
import com.example.HistorialMedico.service.tratamientoService;

@RestController
@RequestMapping("/api/v1/historialmedico")
public class historialMedicoController {
    @Autowired
    private historialMedicoService historialmedicoservice;

    @Autowired
    private tratamientoService tratamientoService;

    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listarHistorialMedico() {
        List<HistorialMedico> historialMedicos = historialmedicoservice.listarHistorialMedico();

        if (historialMedicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historialMedicos);
    }

    @PostMapping
    public ResponseEntity<HistorialMedico> saveHistorialMedico(@RequestBody HistorialMedico hm) {
        HistorialMedico historialMedico2 = historialmedicoservice.agregarHistorialMedico(hm);
        return ResponseEntity.status(HttpStatus.CREATED).body(historialMedico2);
    }

    /*
     * manytoone,joincolumn,JsonIgnore--- para hacer en las tablas que dijo el profe
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> deleteHistorialMedicoById(@PathVariable Long id) {
        try {
            HistorialMedico hm = historialmedicoservice.buscarHistorialMedicoPorId(id);
            historialmedicoservice.eliminarHistorialMedico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorialMedicoPorId(@PathVariable Long id) {
        try {
            HistorialMedico hismed = historialmedicoservice.buscarHistorialMedicoPorId(id);
            historialmedicoservice.eliminarHistorialMedico(id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> modificarHistorialMedico(@PathVariable Long id,
            @RequestBody HistorialMedico historialMedico2) {
        try {
            HistorialMedico hismed = historialmedicoservice.buscarHistorialMedicoPorId(id);
            hismed.setHistorialId(id);
            hismed.setFechaRegistro(historialMedico2.getFechaRegistro());

            historialmedicoservice.agregarHistorialMedico(hismed);
            return ResponseEntity.ok(hismed);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tratamiento/{id}/inventario")
    public ResponseEntity<inventarioDTO> getInventarioDelTratamiento(@PathVariable Long id) {
        try {
            inventarioDTO inventarioDTO = tratamientoService.obtenerInventarioPorTratamiento(id);
            return ResponseEntity.ok(inventarioDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
    }
    
}
