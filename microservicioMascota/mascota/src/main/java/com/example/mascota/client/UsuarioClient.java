package com.example.mascota.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.mascota.dto.UsuarioDTO;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    UsuarioDTO getUsuarioById(@PathVariable("id") Long id);
}

