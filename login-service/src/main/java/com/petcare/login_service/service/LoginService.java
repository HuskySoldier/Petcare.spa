package com.petcare.login_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petcare.login_service.client.UsuarioClient;
import com.petcare.login_service.dto.LoginRequest;
import com.petcare.login_service.dto.LoginResponse;
import com.petcare.login_service.dto.UsuarioDTO;

@Service
public class LoginService {

    @Autowired
    private UsuarioClient usuarioClient;

    public LoginResponse login(LoginRequest request) {
        UsuarioDTO usuario = null;
        try {
            // Llamada a UsuarioService a través de Feign
            usuario = usuarioClient.findByEmail(request.getEmail());
        } catch (Exception e) {
            return new LoginResponse("Error al conectar con el servicio de usuario", false);
        }

        if (usuario == null) {
            return new LoginResponse("Usuario no encontrado", false);
        }

        if (usuario.getPassword().equals(request.getPassword())) {
            return new LoginResponse("Login exitoso", true);
        } else {
            return new LoginResponse("Contraseña incorrecta", false);
        }
    }
}
