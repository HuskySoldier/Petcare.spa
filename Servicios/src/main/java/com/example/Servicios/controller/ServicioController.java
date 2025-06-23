package com.example.Servicios.controller;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

    @Tag(name = "Servicios y Categorias", description = "Operaciones relacionadas con la gestion de los servicios y categorias")

@RestController
@RequestMapping("/api/v1/servicio")
public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private CategoriaService categoriaService;

    @Operation(
        summary = "Listar todas los servicios ",
        description = "Obtiene toda la lista de los servicios.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Servicio obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))
            )
        }
    )
    // buscar todos los servicios
    @GetMapping("/total")
    public ResponseEntity<List<Servicio>> getServicio() {
        List<Servicio> servicio = servicioService.allServicio();
        if (servicio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(servicio);
    }

    @Operation(
        summary = "Buscar un servicio por id ",
        description = "Se busca un servicio por id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Servicio obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Servicio no encontrado"
            )
        }
    )
    // buscar un servicio por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getServicio(@PathVariable("id") Long idServicio) {
        Optional<Servicio> servicio = servicioService.obtenerServicioPorId(idServicio);
        if (servicio.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El servicio con ID " + idServicio + " no existe o ya fue eliminado.");
        }
        return ResponseEntity.ok(servicio.get());
    }
    
    @Operation(
        summary = "Se crea un servicio ",
        description = "Se crea un servicio.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Servicio creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))
            )
        }
    )
    // crear un servicio
    @PostMapping
    public ResponseEntity<?> crearServicio(@Valid @RequestBody Servicio servicio) {
        try {
            Servicio servicio2 = servicioService.crearServicio(servicio);
            return ResponseEntity.status(201).body(servicio2);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
    
    // validar el precio a que no recibir un dato que deberia ser int
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleConversionError(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(400).body("Error el precio tiene un dato incorrecto.");
    }

    @Operation(
        summary = "Se crea una categoria ",
        description = "Se crea una categoria.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Categoria creado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))
            )
        }
    )
    // crear una categoria
    @PostMapping("/categoria")
    public ResponseEntity<Categoria> crearCategoria(@Valid @RequestBody Categoria categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crearCategoria(categoria));
    }

    @Operation(
        summary = "Listar todas las categorias ",
        description = "Obtiene toda la lista de las categorias.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Categoria obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Categoria.class))
            )
        }
    )
    // obtener todas las categoria
    @GetMapping("/categoria")
    public ResponseEntity<List<Categoria>> listarCategoria() {
        return ResponseEntity.ok(categoriaService.listarCategoria());
    }

    @Operation(
        summary = "Actualiza un servicio por id ",
        description = "Se actualiza un servicio por id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Servicio actualizado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Servicio no encontrado"
            )
        }
    )
    // actualizar un servicio por id
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarServicio(@Valid @PathVariable("id") Long idServicio,
            @RequestBody Servicio servicioActualizado) {
        try {
            servicioService.actualizarServicio(idServicio, servicioActualizado);
            return ResponseEntity.ok("Servicio ha sido actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Elimina un servicio por id ",
        description = "Se elimina un servicio por id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Servicio eliminado exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Servicio.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Servicio no encontrado"
            )
        }
    )
    // eliminar un servicio por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarServicio(@PathVariable("id") Long idServicio) {
        try {
            servicioService.deleteServicio(idServicio);
            return ResponseEntity.ok("Servicio eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
