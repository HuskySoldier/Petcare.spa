package com.example.mascota.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.mascota.dto.UsuarioDTO;

@FeignClient(name = "usuario", url = "http://localhost:8082/usuarios")
public interface UsuarioClient {

    @GetMapping("/{id}")
    UsuarioDTO obtenerUsuarioPorId(@PathVariable("id") Long id);

    @GetMapping("/email/{email}")
    UsuarioDTO findByEmail(@PathVariable String email);

   
}

