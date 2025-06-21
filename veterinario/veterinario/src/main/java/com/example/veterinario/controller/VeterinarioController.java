package com.example.veterinario.controller;

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

import com.example.veterinario.model.Veterinario;
import com.example.veterinario.service.VeterinarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/veterinario")
public class VeterinarioController {
    @Autowired
    private VeterinarioService veterinarioservice;

    @Operation(summary="Permite obtener una lista con todos los Veterinarios que hay en la clinica")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Generó la lista con todos los Veterinarios",
        content=@Content(schema=@Schema(implementation=Veterinario.class)))
    })

    // llama a todos los veterinario
    @GetMapping("/Total")
    public ResponseEntity<List<Veterinario>> listarVeterinarios() {
        List<Veterinario> veterinarios = veterinarioservice.listarVeterinarios();

        if (veterinarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(veterinarios);
    }

    @Operation(summary="Permite obtener los datos del veteinario con solo el id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Genero los datos del veterinario llamados por su id",
        content=@Content(schema=@Schema(implementation=Veterinario.class)))
    })
    // este es para ver veterinario por id
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerVeterinarioPorId(@PathVariable Long id) {
        Veterinario veterinario = veterinarioservice.buscarVeterinarioPorId(id);
        if (veterinario != null) {
            return ResponseEntity.ok(veterinario);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Veterinario con ID " + id + " no encontrado.");
        }
    }
 
    @Operation(summary="Permite agregar unnuevo veterinario a la clinica")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Genero el ingreso del nuevo veterinario al sistema",
        content=@Content(schema=@Schema(implementation=Veterinario.class)))
    })
    // este es para agregar el veterinario
    @PostMapping
    public ResponseEntity<?> agregarVeterinario(@Valid @RequestBody Veterinario vt) {
        try {
            Veterinario veterinario2 = veterinarioservice.agregarVeterinario(vt);
            return ResponseEntity.status(HttpStatus.CREATED).body(veterinario2);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @Operation(summary="Permite eliminar el veterinario por su id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="elimino al veterinario por su id",
        content=@Content(schema=@Schema(implementation=Veterinario.class)))
    })

    // delete por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVeterinarioPorId(@PathVariable Long id) {
        try {
            veterinarioservice.eliminarVeterinario(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary="Permite Modificar los datos del veterinario por su Id")
    @ApiResponses( value = {
        @ApiResponse(responseCode="200", description="Genero el cambio de los nuevos datos del usuario",
        content=@Content(schema=@Schema(implementation=Veterinario.class)))
    })

    // modificar el veterinario
    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> modificarVeterinario(@PathVariable Long id,
            @RequestBody Veterinario veterinario2) {
        try {
            Veterinario vet = veterinarioservice.buscarVeterinarioPorId(id);

            vet.setVeterinarioId(id);
            vet.setNombre(veterinario2.getNombre());
            vet.setApellido(veterinario2.getApellido());
            vet.setEspecialidad(veterinario2.getEspecialidad());
            vet.setCorreo(veterinario2.getCorreo());

            veterinarioservice.agregarVeterinario(vet);
            return ResponseEntity.ok(vet);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    

}
