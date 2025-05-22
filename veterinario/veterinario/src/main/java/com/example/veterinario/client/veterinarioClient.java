package com.example.veterinario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.veterinario.dto.usuarioDTO;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/api/usuario")

public interface veterinarioClient {

    @GetMapping("/email/{email}")
    usuarioDTO findByEmail(@PathVariable String email);

}
