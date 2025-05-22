package com.example.mascota.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.mascota.dto.usuarioDTO;


@FeignClient(name="usuario-service" ,url = "http://localhost:8082/api/usuario")
public interface usuarioCliente {

    @GetMapping("/email/{email}")
    usuarioDTO findByEmail(@PathVariable("email") String email);
    


}
