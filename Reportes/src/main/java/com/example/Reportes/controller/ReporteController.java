package com.example.Reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Reportes.model.Reporte;
import com.example.Reportes.service.ReporteService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Reportes", description = "Operaciones relacionadas con la gestión de los reportes")
@RestController
@RequestMapping("/api/v1/reporte")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Operation(
        summary = "Listar todos los reportes",
        description = "Obtiene toda la lista de los reportes asociados a un usuario con rol válido",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Reportes obtenidos exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reporte.class))
            )
        }
    )
    @GetMapping("/total")
    public ResponseEntity<?> getReporte(@RequestHeader("X-USER-ID") Long idUsuario) {
        try {
            List<Reporte> reporte = reporteService.allReporte(idUsuario);
            if (reporte.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(reporte);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Usuario con ID " + idUsuario + " no encontrado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Crear un nuevo reporte",
        description = "Crea un nuevo reporte si el usuario tiene rol autorizado",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Reporte creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reporte.class))
            )
        }
    )
    @PostMapping
    public ResponseEntity<?> crearReporte(
            @RequestBody Reporte reporte,
            @RequestHeader("X-USER-ID") Long idUsuario) {
        try {
            Reporte reporteCreado = reporteService.crearReporte(reporte, idUsuario);
            return ResponseEntity.status(201).body(reporteCreado);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Usuario con ID " + idUsuario + " no encontrado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
