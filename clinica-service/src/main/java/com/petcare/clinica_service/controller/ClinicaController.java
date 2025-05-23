package com.petcare.clinica_service.controller;

import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.service.ClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinicas")
public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;

    @GetMapping
    public ResponseEntity<List<Clinica>> listar() {
        return ResponseEntity.ok(clinicaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clinica> obtener(@PathVariable Long id) {
        return clinicaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Clinica> crear(@Valid @RequestBody Clinica clinica) {
        return ResponseEntity.ok(clinicaService.guardar(clinica));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clinica> actualizar(@PathVariable Long id, @Valid @RequestBody Clinica nuevaClinica) {
        return clinicaService.obtenerPorId(id)
                .map(c -> {
                    c.setNombre(nuevaClinica.getNombre());
                    c.setDireccion(nuevaClinica.getDireccion());
                    c.setComuna(nuevaClinica.getComuna());
                    c.setCapacidad(nuevaClinica.getCapacidad());
                    return ResponseEntity.ok(clinicaService.guardar(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clinicaService.obtenerPorId(id).isPresent()) {
            clinicaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Clinica> actualizarParcial(@PathVariable Long id, @RequestBody Clinica clinicaParcial) {
        return clinicaService.obtenerPorId(id)
                .map(c -> {
                    if (clinicaParcial.getNombre() != null) {
                        c.setNombre(clinicaParcial.getNombre());
                    }
                    if (clinicaParcial.getDireccion() != null) {
                        c.setDireccion(clinicaParcial.getDireccion());
                    }
                    if (clinicaParcial.getComuna() != null) {
                        c.setComuna(clinicaParcial.getComuna());
                    }
                    if (clinicaParcial.getCapacidad() != null) {
                        c.setCapacidad(clinicaParcial.getCapacidad());
                    }
                    return ResponseEntity.ok(clinicaService.guardar(c));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}


//Consulta en Postman
//{"direccion": "Av. Siempre Viva 742","contacto": "+56 9 1234 5678","capacidad": 50}
