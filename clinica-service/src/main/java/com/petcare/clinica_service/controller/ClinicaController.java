package com.petcare.clinica_service.controller;

import com.petcare.clinica_service.model.Clinica;
import com.petcare.clinica_service.service.ClinicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Swagger OpenAPI imports
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Controlador REST para la gestión de clínicas.
 * 
 * Implementa endpoints con soporte para HATEOAS (en futuras versiones).
 */
@Tag(name = "Clínicas", description = "Operaciones relacionadas con la gestión de clínicas")
@RestController
@RequestMapping("/clinicas")
public class ClinicaController {

    @Autowired
    private ClinicaService clinicaService;

    /**
     * Obtiene la lista de todas las clínicas.
     * @return Lista de clínicas.
     */
    @Operation(
        summary = "Listar todas las clínicas",
        description = "Obtiene la lista de todas las clínicas registradas.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de clínicas obtenida exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clinica.class))
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<Clinica>> listar() {
        return ResponseEntity.ok(clinicaService.obtenerTodas());
    }

    /**
     * Obtiene una clínica por su ID.
     * @param id Identificador de la clínica.
     * @return La clínica encontrada o 404 si no existe.
     */
    @Operation(
        summary = "Obtener clínica por ID",
        description = "Obtiene una clínica específica usando su identificador.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Clínica encontrada",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clinica.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Clínica no encontrada"
            )
        }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Clinica> obtener(
            @Parameter(description = "ID de la clínica", required = true) @PathVariable Long id) {
        return clinicaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Crea una nueva clínica.
     * @param clinica Datos de la clínica a crear.
     * @return La clínica creada.
     */
    @Operation(
        summary = "Crear una nueva clínica",
        description = "Crea una nueva clínica con los datos proporcionados.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Clínica creada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clinica.class))
            ),
            @ApiResponse(
                responseCode = "409",
                description = "Clínica duplicada"
            )
        }
    )
    @PostMapping
    public ResponseEntity<Clinica> crear(
            @Parameter(description = "Datos de la clínica a crear", required = true) @Valid @RequestBody Clinica clinica) {
        List<Clinica> existentes = clinicaService.obtenerTodas();
        boolean duplicada = existentes.stream().anyMatch(c ->
            (c.getNombre().equalsIgnoreCase(clinica.getNombre()) && c.getComuna().equalsIgnoreCase(clinica.getComuna()))
            || (c.getDireccion().equalsIgnoreCase(clinica.getDireccion()) && c.getComuna().equalsIgnoreCase(clinica.getComuna()))
        );
        if (duplicada) {
            throw new com.petcare.clinica_service.exception.ClinicaDuplicadaException("Ya existe una clínica con el mismo nombre o dirección en la misma comuna.");
        }
        return ResponseEntity.ok(clinicaService.guardar(clinica));
    }

    /**
     * Actualiza completamente una clínica existente.
     * @param id Identificador de la clínica.
     * @param nuevaClinica Datos actualizados de la clínica.
     * @return La clínica actualizada o 404 si no existe.
     */
    @Operation(
        summary = "Actualizar completamente una clínica",
        description = "Actualiza todos los datos de una clínica existente.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Clínica actualizada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clinica.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Clínica no encontrada"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Clinica> actualizar(
            @Parameter(description = "ID de la clínica", required = true) @PathVariable Long id,
            @Parameter(description = "Datos actualizados de la clínica", required = true) @Valid @RequestBody Clinica nuevaClinica) {
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

    /**
     * Actualiza parcialmente una clínica existente.
     * @param id Identificador de la clínica.
     * @param clinicaParcial Datos parciales a actualizar.
     * @return La clínica actualizada o 404 si no existe.
     */
    @Operation(
        summary = "Actualizar parcialmente una clínica",
        description = "Actualiza uno o más campos de una clínica existente.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Clínica actualizada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Clinica.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Clínica no encontrada"
            )
        }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Clinica> actualizarParcial(
            @Parameter(description = "ID de la clínica", required = true) @PathVariable Long id,
            @Parameter(description = "Datos parciales de la clínica", required = true) @RequestBody Clinica clinicaParcial) {
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


