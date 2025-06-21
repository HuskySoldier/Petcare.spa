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

import com.example.HistorialMedico.client.MascotaClient;
import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.repository.TratamientoRepository;
import com.example.HistorialMedico.service.HistorialMedicoService;
import com.example.HistorialMedico.service.TratamientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Autowired
    private MascotaClient mascotaClient;

    @Operation(summary="Permite obtener una lista con todos los Historiales Medicos")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todos los Historiales Medicos",
        content=@Content(schema=@Schema(implementation=HistorialMedico.class)))
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

    @Operation(summary="Permite agregar un Historial medico")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó El nuevo historial Medico",
        content=@Content(schema=@Schema(implementation=HistorialMedico.class)))
    })

    // agregar un historial medico
    @PostMapping
    public ResponseEntity<HistorialMedico> agregarHistorialMedico(@Valid @RequestBody HistorialMedico hm) {
        HistorialMedico historialMedico2 = historialmedicoservice.agregarHistorialMedico(hm);
        return ResponseEntity.status(HttpStatus.CREATED).body(historialMedico2);
    }

    @Operation(summary="Permite agregar un nuevo tratamiento")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó El nuevo tratamiento ",
        content=@Content(schema=@Schema(implementation=HistorialMedico.class)))
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

    @Operation(summary="Permite eliminar un Historial medico por su Id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Se elimino el Historial medico por su id",
        content=@Content(schema=@Schema(implementation=HistorialMedico.class)))
    })
    
    //eliminar un Historial medico por su id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteHistorialMedicoPorId(@PathVariable Long id) {
        try {
            historialmedicoservice.eliminarHistorialMedico(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary="Permite modificar un Historial medico por su id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Se Generó la modificacion del historial Medico",
        content=@Content(schema=@Schema(implementation=HistorialMedico.class)))
    })

    //modificar un Historial medico por su id
    @PutMapping("/{id}")
    public ResponseEntity<HistorialMedico> modificarHistorialMedico(@PathVariable Long id,
            @Valid @RequestBody HistorialMedico historialActualizado) {
        try {
            HistorialMedico hismed = historialmedicoservice.buscarHistorialMedicoPorId(id);

            hismed.setFechaRegistro(historialActualizado.getFechaRegistro());
            hismed.setAntecedentes(historialActualizado.getAntecedentes());
            hismed.setComentario(historialActualizado.getComentario());
            hismed.setIdMascota(historialActualizado.getIdMascota());
            hismed.setDiagnostico(historialActualizado.getDiagnostico());

            HistorialMedico actualizado = historialmedicoservice.agregarHistorialMedico(hismed);
            return ResponseEntity.ok(actualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary="Permite Obtener un Historial medico por la mascota y us Id ")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Se genero la informacion del historial medico de la mascota",
        content=@Content(schema=@Schema(implementation=HistorialMedico.class)))
    })

    //obtener un historial medico por la mascota
    @GetMapping("/mascota/{idMascota}")
    public ResponseEntity<List<HistorialMedico>> obtenerHistorialPorMascota(@PathVariable Long idMascota) {
        List<HistorialMedico> historiales = historialmedicoservice.buscarPorIdMascota(idMascota);
        if (historiales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(historiales);
    }
}
