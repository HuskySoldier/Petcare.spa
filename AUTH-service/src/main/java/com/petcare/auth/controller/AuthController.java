package com.petcare.auth.controller;

import com.petcare.auth.dto.AuthRequest;
import com.petcare.auth.dto.AuthResponse;
import com.petcare.auth.dto.RegisterRequest;
import com.petcare.auth.dto.RegisterResponse;
import com.petcare.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody @Valid RegisterRequest request) {
    return authService.register(request);
    }

}
