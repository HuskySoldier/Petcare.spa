package com.petcare.clinica_service.controller;

import com.petcare.clinica_service.PatchDTO.ClinicaDTO;
import com.petcare.clinica_service.PatchDTO.ClinicaPatchDTO;
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
    private ClinicaService service;

    @PostMapping
    public ResponseEntity<Clinica> crear(@Valid @RequestBody ClinicaDTO dto) {
    Clinica clinica = service.guardarDesdeDTO(dto);
    return ResponseEntity.ok(clinica);
    }

    @GetMapping
    public List<Clinica> listar() {
        return service.listarClinicas();
    }

    @GetMapping("/{id}")
    public Clinica obtener(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarClinica(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Clinica> patchClinica(@PathVariable Long id, @RequestBody ClinicaPatchDTO patchDTO) {
    Clinica updated = service.updateParcial(id, patchDTO);
    return ResponseEntity.ok(updated);
    }

  
    @PutMapping("/{id}")
    public ResponseEntity<Clinica> update(@PathVariable Long id,@Valid @RequestBody ClinicaDTO dto) {
    Clinica actualizada = service.updateCompleto(id, dto);
    return ResponseEntity.ok(actualizada);}



}
//Consulta en Postman
//{"direccion": "Av. Siempre Viva 742","contacto": "+56 9 1234 5678","capacidad": 50}
