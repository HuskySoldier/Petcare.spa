package com.example.Inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Inventario.dto.UsuarioDto;

@FeignClient(name = "usuario-service", url = "http://localhost:8081/usuarios")
public interface UsuarioClient {
    @GetMapping("/{id}")
    UsuarioDto obtenerUsuarioPorId(@PathVariable("id") Long id);
}
