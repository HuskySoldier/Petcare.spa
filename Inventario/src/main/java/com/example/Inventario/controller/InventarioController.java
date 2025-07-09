package com.example.Inventario.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Inventario.model.Inventario;
import com.example.Inventario.service.InventarioService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PutMapping;

@Tag(name = "Inventario", description = "Operaciones relacionadas con la gestion del inventario")
@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;
    
    @Operation(
        summary = "Listar un inventario por id ",
        description = "Obtiene la lista de todo los inventarios.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Inventario obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Inventario no encontrado"
            )
        }
    )
    @GetMapping("/{id}") // obtener un inventario por su id, solo si el usuario tiene un rol valido
    public ResponseEntity<Inventario> getInventario(
        @PathVariable("id") Long idInventario, // id del inventario  (parte de la URL)
        @RequestHeader("X-USER-ID") Long idUsuario) { //id del usuario autenticado
    try {
        // se llama al service y valida el rol
        Optional<Inventario> inventarioOpt = inventarioService.obtenerInventarioPorId(idInventario, idUsuario);
        if (inventarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build(); // inventario no se ha encontrado
        }
        return ResponseEntity.ok(inventarioOpt.get());
    } catch (FeignException.NotFound e) {
        return ResponseEntity.status(404).build();
    } catch (RuntimeException e) {          
        return ResponseEntity.status(403).body(null);
    }
}

    @Operation(
        summary = "Listar todos los inventarios",
        description = "Obtiene la lista de todo los inventarios.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de inventario obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))
            )
        }
    )
    @GetMapping("/total")
    public ResponseEntity<List<Inventario>> getInventarioTotal(@RequestHeader("X-USER-ID") Long idUsuario) {
    try {
        List<Inventario> inventario = inventarioService.allInventario(idUsuario);
        if (inventario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(inventario);
    } catch (FeignException.NotFound e) {
        return ResponseEntity.status(404).body(null); // O puedes retornar un mensaje
    } catch (RuntimeException e) {
        return ResponseEntity.status(403).body(null);
    }
}

    @Operation(
        summary = "Crear un nuevo inventario",
        description = "Crea una invnetario con los datos proporcionados.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Inventario creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))
            )
        }
    )
    // se esta creando el inventario
    @PostMapping
    public ResponseEntity<?> crearInventario(
            @RequestBody Inventario inventario,
            @RequestHeader("X-USER-ID") Long idUsuario) { // se valida la entradas de los usuarios 
        try {
            Inventario creado = inventarioService.crearInventario(inventario, idUsuario);
            return ResponseEntity.status(201).body(creado);
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Usuario con ID " + idUsuario + " no encontrado."); // error si el usuario no se encuentra
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Actualizar completamente un inventario",
        description = "Actualiza todos los datos de un inventario existente.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Inventario actualizado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Inventario no encontrado"
            )
        }
    )
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarInventario(
            @PathVariable("id") Long idInventario, // id del inventario  (parte de la URL)
            @RequestBody Inventario inventarioActualizado, 
            @RequestHeader("X-USER-ID") Long idUsuario) { //id del usuario autenticado
        try {
            inventarioService.actualizarInventario(idInventario, inventarioActualizado, idUsuario);
            return ResponseEntity.ok("Inventario actualizado correctamente");
        } catch (FeignException.NotFound e) {
            return ResponseEntity.status(404).body("Usuario con ID " + idUsuario + " no encontrado."); // error si el usuario no se encuentra
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Elimina un inventario",
        description = "Elimina un inventario completamente.",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Inventario eliminado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Inventario.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Inventario no encontrada"
            )
        }
    )
    // Eliminamos el inventario que deseemos
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventario(
        @PathVariable("id") Long idInventario, // id del inventario  (parte de la URL)
        @RequestHeader("X-USER-ID") Long idUsuario) { //id del usuario autenticado
    try {
        inventarioService.deleteInventario(idInventario, idUsuario);
        return ResponseEntity.ok("Inventario eliminado correctamente");
    } catch (FeignException.NotFound e) {
        return ResponseEntity.status(404).body("Usuario con ID " + idUsuario + " no encontrado."); // error si el usuario no se encuentra
    } catch (RuntimeException e) {
        return ResponseEntity.status(403).body(e.getMessage());
    }
}
}