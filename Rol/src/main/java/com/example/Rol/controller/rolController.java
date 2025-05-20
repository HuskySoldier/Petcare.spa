package com.example.Rol.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Rol.model.Rol;
import com.example.Rol.service.rolService;

@RestController
@RequestMapping("/api/v1/rol")
public class rolController {

    @Autowired
    private rolService rolservice;

    @GetMapping
    public ResponseEntity<List<Rol>> listarRol(){
        List<Rol> roles = rolservice.listarRol();
        if(roles.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Rol> saveCliente(@RequestBody Rol rol){
        Rol rol2 = rolservice.agregarRol(rol);
        return ResponseEntity.status(HttpStatus.CREATED).body(rol2);
    }
}
