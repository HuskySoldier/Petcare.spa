package com.petcare.register_service.client;

import com.petcare.register_service.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



@FeignClient(name = "usuario-service", url = "http://localhost:8082/api/usuario")
public interface UsuarioClient {
    @GetMapping("/email/{email}")
    UsuarioDTO findByEmail(@PathVariable String email);

    @PostMapping
    UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO);
}