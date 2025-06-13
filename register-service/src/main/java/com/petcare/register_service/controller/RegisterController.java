package com.petcare.register_service.controller;

import com.petcare.register_service.dto.RegisterResponse;
import com.petcare.register_service.dto.UsuarioDTO;
import com.petcare.register_service.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

   @PostMapping
    public RegisterResponse registrar(@RequestBody UsuarioDTO usuarioDTO) {
        return registerService.register(usuarioDTO);
    }
}

