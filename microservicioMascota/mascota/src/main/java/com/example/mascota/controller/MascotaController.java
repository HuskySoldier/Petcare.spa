
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
import com.example.mascota.service.MascotaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/mascota")
public class MascotaController {
    @Autowired
    private MascotaService mascotaservice;

    // llama a todas las mascotas
    @GetMapping("/Total")
    public ResponseEntity<List<Mascota>> listarMascotas() {
        return ResponseEntity.ok(mascotaservice.listarMacotas());
    }

    // para buscar mascota por id
    @GetMapping("/{id}")
    public ResponseEntity<Mascota> buscarMascotaId(@PathVariable Long id) {
        Mascota mascota = mascotaservice.buscarMascotaPorId(id);

        if (mascota == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(mascota);
    }
    // para guardar la mascota
    @PostMapping
    public ResponseEntity<Mascota> saveMascota(@RequestBody Mascota masc) {
        Mascota mascota2 = mascotaservice.agregarMascota(masc);
        return ResponseEntity.status(HttpStatus.CREATED).body(mascota2);
    }

    //eliminar la mascota por id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMascotaPorId(@PathVariable Long id) {
        try {
            mascotaservice.eliminarMascota(id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //

        }
    }

    //es para modificar la mascota
    @PutMapping("/{id}")
    public ResponseEntity<Mascota> modificarMascota(@Valid @PathVariable Long id, @RequestBody Mascota mascota2) {
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
