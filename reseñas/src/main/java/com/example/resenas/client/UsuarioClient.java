package com.example.resenas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.resenas.Dto.UsuarioDto;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    UsuarioDto obtenerUsuarioPorId(@PathVariable("id") Long id);
}
