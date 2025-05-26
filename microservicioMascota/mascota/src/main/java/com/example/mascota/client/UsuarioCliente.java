package com.example.mascota.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.example.mascota.dto.UsuarioDTO;


@FeignClient(name="usuario-service" ,url = "http://localhost:8082/api/usuario")
public interface UsuarioCliente {

    @GetMapping("/email/{email}")
    UsuarioDTO findByEmail(@PathVariable("email") String email);
    


}
