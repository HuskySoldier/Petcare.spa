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
import com.example.veterinario.service.veterinarioService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/veterinario")
public class veterinarioController {
    @Autowired
    private veterinarioService veterinarioservice;

     @GetMapping
    public ResponseEntity<List<Veterinario>> listarVeterinarios(){
        List <Veterinario> veterinarios=veterinarioservice.listarVeterinarios();

        if(veterinarios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(veterinarios);
    }

    @PostMapping
    public ResponseEntity<Veterinario> saveVeterinario(@RequestBody Veterinario vt){
        Veterinario veterinario2=veterinarioservice.agregarVeterinario(vt);
        return ResponseEntity.status(HttpStatus.CREATED).body(veterinario2);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVeterinarioPorId(@PathVariable Long id){
        try {
            
            veterinarioservice.eliminarVeterinario(id);
            return ResponseEntity.noContent().build();
            
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veterinario> modificarVeterinario(@PathVariable Long id,@Valid  @RequestBody Veterinario veterinario2){
        try {
            Veterinario vet = veterinarioservice.buscarVeterinarioPorId(id);

            vet.setVeterinario_id(id);
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
