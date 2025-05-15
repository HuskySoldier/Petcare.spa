package com.example.mascota.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="" ,url "")
public interface mascotaCliente {

    @GetMapping("/")
    UsuarioDTO findByEmail(@RequestParam("email") String email);
    


}
