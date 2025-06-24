package com.example.Reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Reserva.dto.UsuarioDTO;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/usuarios")
public interface UsuarioClient {

    @GetMapping("/email/{email}")
    UsuarioDTO findByEmail(@PathVariable String email);

    @GetMapping("/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long idUsuario);

}
