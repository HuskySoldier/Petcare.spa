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

import com.example.HistorialMedico.dto.InventarioDTO;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.repository.TratamientoRepository;
import com.example.HistorialMedico.service.HistorialMedicoService;
import com.example.HistorialMedico.service.TratamientoService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/historialmedico")
public class HistorialMedicoController {
    @Autowired
    private HistorialMedicoService historialmedicoservice;

    @Autowired
    private TratamientoService tratamientoService;

    @Autowired
    private HistorialMedicoRepository historialRepo;

    @Autowired
    private TratamientoRepository tratamientoRepo;
    // llama a todos los historiales
    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listarHistorialMedico() {
        List<HistorialMedico> historialMedicos = historialmedicoservice.listarHistorialMedico();

        if (historialMedicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historialMedicos);
    }

    @PostMapping
    public ResponseEntity<HistorialMedico> saveHistorialMedico(@Valid @RequestBody HistorialMedico hm) {
        HistorialMedico historialMedico2 = historialmedicoservice.agregarHistorialMedico(hm);
        return ResponseEntity.status(HttpStatus.CREATED).body(historialMedico2);
    }

    @PostMapping("/{id}/tratamientos")
    public ResponseEntity<Tratamiento> agregarTratamiento(
            @PathVariable Long id,
            @RequestBody Tratamiento tratamiento) {

        HistorialMedico historial = historialRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Historial no encontrado"));

        tratamiento.setHistorialMedico(historial);
        Tratamiento nuevo = tratamientoRepo.save(tratamiento);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorialMedicoPorId(@PathVariable Long id) {
        try {
            historialmedicoservice.eliminarHistorialMedico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> modificarHistorialMedico(@PathVariable Long id,
            @Valid @RequestBody HistorialMedico historialMedico2) {
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
    public ResponseEntity<InventarioDTO> getInventarioDelTratamiento(@PathVariable Long id) {
        try {
            InventarioDTO inventarioDTO = tratamientoService.obtenerInventarioPorTratamiento(id);
            return ResponseEntity.ok(inventarioDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
