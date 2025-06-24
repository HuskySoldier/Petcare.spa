package com.example.HistorialMedico.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.HistorialMedico.client.MascotaClient;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.repository.TratamientoRepository;
import com.example.HistorialMedico.service.HistorialMedicoService;
import com.example.HistorialMedico.service.TratamientoService;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@Tag(name = "HistorialMedico", description = "Operaciones sobre el historial médico")
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

    @Autowired
    private MascotaClient mascotaClient;

    @Operation(summary = "Permite obtener una lista con todos los Historiales Medicos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generó la lista con todos los Historiales Medicos", content = @Content(schema = @Schema(implementation = HistorialMedico.class)))
    })

    // llama a todos los historiales
    @GetMapping
    public ResponseEntity<List<HistorialMedico>> listarHistorialMedico() {
        List<HistorialMedico> historialMedicos = historialmedicoservice.listarHistorialMedico();

        if (historialMedicos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historialMedicos);
    }

    @Operation(summary = "Permite agregar un Historial medico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generó El nuevo historial Medico", content = @Content(schema = @Schema(implementation = HistorialMedico.class)))
    })

    // agregar un historial medico
    @PostMapping
    public ResponseEntity<?> agregarHistorialMedico(
            @RequestBody HistorialMedico hm,
            @RequestHeader(value = "X-USER-ID", required = false) Long userId) {
        try {
            HistorialMedico historialMedico2 = historialmedicoservice.agregarHistorialMedico(hm, userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(historialMedico2);
        } catch (feign.FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + userId + " no encontrado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @Operation(summary = "Permite agregar un nuevo tratamiento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generó El nuevo tratamiento ", content = @Content(schema = @Schema(implementation = HistorialMedico.class)))
    })

    // para agregar un tratamiento
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

    @Operation(summary = "Permite eliminar un Historial medico por su Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimino el Historial medico por su id", content = @Content(schema = @Schema(implementation = HistorialMedico.class)))
    })

    // eliminar un Historial medico por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorialMedicoPorId(@PathVariable Long id) {
        try {
            historialmedicoservice.eliminarHistorialMedico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Permite modificar un Historial medico por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se Generó la modificacion del historial Medico", content = @Content(schema = @Schema(implementation = HistorialMedico.class)))
    })

    // modificar un Historial medico por su id
    @PutMapping("/{id}")
    public ResponseEntity<?> modificarHistorialMedico(
            @PathVariable Long id,
            @RequestBody HistorialMedico historialActualizado,
            @RequestHeader(value = "X-USER-ID", required = false) Long userId) {
        try {
            HistorialMedico actualizado = historialmedicoservice.modificarHistorialMedico(id, historialActualizado,
                    userId);
            return ResponseEntity.ok(actualizado);
        } catch (feign.FeignException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + userId + " no encontrado.");
        } catch (jakarta.persistence.EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @Operation(summary = "Permite Obtener un Historial medico por la mascota y us Id ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se genero la informacion del historial medico de la mascota", content = @Content(schema = @Schema(implementation = HistorialMedico.class)))
    })

    // obtener un historial medico por la mascota
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<HistorialMedico>> obtenerHistorialPorMascota(@PathVariable Long idMascota) {
        List<HistorialMedico> historiales = historialmedicoservice.buscarPorIdMascota(idMascota);
        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historiales);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body("Datos inválidos: " + ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> handleFeignNotFound(FeignException.NotFound ex,
            org.springframework.web.context.request.WebRequest request) {
        // Intenta extraer el userId del header si está presente
        String userId = request.getHeader("X-USER-ID");
        if (userId != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + userId + " no encontrado.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}
