package com.petcare.login_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.petcare.login_service.dto.LoginRequest;
import com.petcare.login_service.dto.LoginResponse;
import com.petcare.login_service.service.LoginService;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public LoginResponse login(@RequestBody LoginRequest request) {
        return loginService.login(request);
    }
}
