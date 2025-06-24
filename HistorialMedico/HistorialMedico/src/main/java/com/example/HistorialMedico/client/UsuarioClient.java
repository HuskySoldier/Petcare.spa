package com.example.HistorialMedico.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.HistorialMedico.dto.UsuarioDTO;


@FeignClient(name = "usuario-service", url = "http://localhost:8082/usuarios")
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);
}
