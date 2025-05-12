package com.petcare.auth.Client;

import com.petcare.auth.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "usuario-service", url = "http://localhost:8082")
public interface UsuarioClient {

    @GetMapping("/usuarios/by-email")
    UsuarioDTO findByEmail(@RequestParam("email") String email);

    @PostMapping("/usuarios")
    UsuarioDTO crearUsuario(@RequestBody UsuarioDTO usuarioDTO);
}
