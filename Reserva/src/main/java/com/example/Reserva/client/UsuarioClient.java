package com.example.Reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Reserva.dto.UsuarioDTO;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/api/usuario")
public interface UsuarioClient {
    
    @GetMapping("/email/{email}")
    UsuarioDTO findByEmail(@PathVariable String email);

}
