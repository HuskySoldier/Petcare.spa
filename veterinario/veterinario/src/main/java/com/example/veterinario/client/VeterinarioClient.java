package com.example.veterinario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.veterinario.dto.UsuarioDTO;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/usuarios")
public interface VeterinarioClient {

    @GetMapping("/email/{email}")
    ResponseEntity<UsuarioDTO> findByEmail(@PathVariable String email);

    @GetMapping("/{id}")
    ResponseEntity<UsuarioDTO> findById(@PathVariable("id") Long id);
}
