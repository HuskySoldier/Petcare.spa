package com.example.mascota.controller;

import com.example.mascota.model.Mascota;
import com.example.mascota.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mascota")
public class MascotaController {

    @Autowired
    private MascotaService mascotaService;

    @Operation(summary = "Permite obtener una lista con todas las mascotas que estan en la clinica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generó la lista con todas las mascotas", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })

    // Listar todas las mascotas
    @GetMapping
    public List<Mascota> listarTodas() {
        return mascotaService.listarMacotas();
    }

    @Operation(summary = "Permite buscar una mascota por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se genero la busqueda de la informaciond e la mascota", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })
    // Buscar mascota por ID
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> obtenerPorId(
            @PathVariable("id") Long idMascota,
            @RequestHeader("X-USER-ID") Long idUsuario) {
        try {
            Mascota mascota = mascotaService.buscarMascotaPorId(idMascota);
            if (mascota == null) {
                return ResponseEntity.notFound().build(); // no existe la mascota
            }
            return ResponseEntity.ok(mascota); // retorna la mascota encontrada
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(null); // acceso denegado u otro error
        }
    }

    @Operation(summary = "Permite crear una nueva mascota")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generó el ingreso d la nueva mascota", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })

    // Crear nueva mascota
    @PostMapping
    public ResponseEntity<?> crearMascota(@Valid @RequestBody Mascota mascota) {
        try {
            Mascota nueva = mascotaService.agregarMascota(mascota);
            // Cambia a 201 solo si no es null, si es null devuelve 400
            if (nueva == null) {
                return ResponseEntity.badRequest().body("No se pudo crear la mascota.");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Operation(summary = "Permite eliminar una mascota por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimino la mascota por su id", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })
    // Eliminar mascota
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            mascotaService.eliminarMascota(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Permite busacar una mascota por el id del dueño")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se Genero la informacion de la mascota", content = @Content(schema = @Schema(implementation = Mascota.class)))
    })
    // Listar mascotas por ID de usuario
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Mascota>> listarPorUsuario(@PathVariable Long idUsuario) {
        List<Mascota> mascotas = mascotaService.obtenerPorIdUsuario(idUsuario);
        if (mascotas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mascotas);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}

