
package com.example.mascota.controller;

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

import com.example.mascota.model.Mascota;
import com.example.mascota.service.mascotaService;

@RestController
@RequestMapping("/api/v1/mascota")
public class mascotaController {
    @Autowired
    private mascotaService mascotaservice;

    @GetMapping("/total")
    public ResponseEntity<List<Mascota>> listarMascotas() {
        List<Mascota> mascota = mascotaservice.listarMacotas();

        if (mascota.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mascota);
    }

    @PostMapping
    public ResponseEntity<Mascota> saveMascota(@RequestBody Mascota masc) {
        Mascota mascota2 = mascotaservice.agregarMascota(masc);
        return ResponseEntity.status(HttpStatus.CREATED).body(mascota2);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> deleteMascotaById(@PathVariable Long id){
        try {
            Mascota masc= mascotaservice.buscarMascotaPorId(id);
            mascotaservice.eliminarMascota(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMascotaPorId(@PathVariable Long id){
        try {
            Mascota masc=mascotaservice.buscarMascotaPorId(id);
            mascotaservice.eliminarMascota(id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //
            
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> modificarMascota(@PathVariable Long id, @RequestBody
    Mascota mascota2){
        try {
            Mascota masc = mascotaservice.buscarMascotaPorId(id);

            masc.setIdMascota(id);
            masc.setNombre(mascota2.getNombre());
            masc.setEspecie(mascota2.getEspecie());
            masc.setRaza(mascota2.getRaza());
            masc.setEdad(mascota2.getEdad());
            masc.setSexo(mascota2.getSexo());

            mascotaservice.agregarMascota(masc);
            return ResponseEntity.ok(masc);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        
    }

}
