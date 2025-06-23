package com.example.resenas.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.example.resenas.Dto.ReseniaDto;
import com.example.resenas.Dto.ReseniaResponseDto;
import com.example.resenas.model.Resenia;
import com.example.resenas.service.ReseniaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Resenias", description = "Operaciones relacionadas con la gestion de las resenias")
@RestController
@RequestMapping("/api/v1/resena")
public class ReseniaController {

    @Autowired
    private ReseniaService reseniaService;
    @Operation(
        summary = "Listar todas las resenia ",
        description = "Obtiene toda la lista de las resenias.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Resenia obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resenia.class))
            )
        }
    )
    // buscar todos las reseñas
    @GetMapping("/total")
    public ResponseEntity<?> getTodasLasResenas() {
        List<Resenia> resenias = reseniaService.allResenia();

        List<ReseniaResponseDto> respuestas = resenias.stream()
                .map(reseniaService::construirRespuestaConServicio)
                .collect(Collectors.toList());

        return ResponseEntity.ok(respuestas);
    }

    @Operation(
        summary = "Listar una resenia por id ",
        description = "Obtiene la lista de todas las resenias.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Resenia obtenido exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resenia.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Resenia no encontrada"
            )
        }
    )
    // buscar un reseña por id
    @GetMapping("/{id}")
    public ResponseEntity<?> getResenia(@PathVariable("id") Long id) {
        Optional<Resenia> res = reseniaService.obtenerReseniaPorId(id);

        if (res.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ReseniaResponseDto respuesta = reseniaService.construirRespuestaConServicio(res.get());
        return ResponseEntity.ok(respuesta);
    }


    @Operation(
        summary = "Crear una  resenia ",
        description = "Se crea una resenia.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Resenia creada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resenia.class))
            )
        }
    )
    // Se crea una reseña por el dto para no tener problemas con el idresenia
    @PostMapping
    public ResponseEntity<?> crearResenia(@Valid @RequestBody ReseniaDto dto) {
        try {
            Resenia nueva = reseniaService.crearReseniaDesdeDto(dto);
            return ResponseEntity.status(201).body(nueva);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(
        summary = "Actualizar una resenia por id ",
        description = "Se actualiza la resenia por id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Resenia actualizada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resenia.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Resenia no encontrada"
            )
        }
    )
    // Se busca una reseña por mediante el id especifico
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarResenia(@PathVariable("id") Long idResenia,
            @RequestBody Resenia reseniaActualizado) {
        try {
            reseniaService.actualizarResenia(idResenia, reseniaActualizado);
            return ResponseEntity.ok("La reseña se ha sido actualizado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // se verifica que el usuario entrege un dato correcto a los campos
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleConversionError(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(400).body("Error uno ed los campos tiene un dato incorrecto.");
    }

    @Operation(
        summary = "Se elimina una resenia por id ",
        description = "Se elimina una resenia resenias por id.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Resenia eliminada exitosamente",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = Resenia.class))
            ),
            @ApiResponse(
                responseCode = "404",
                description = "Resenia no encontrada"
            )
        }
    )
    // Eliminamos la reseña que deseemos
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarResenia(@PathVariable("id") Long idResenia) {
        try {
            reseniaService.deleteResenia(idResenia);
            return ResponseEntity.ok("Reseña eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
