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
            // Log the exception (optional for debugging)
            e.printStackTrace();
            return new LoginResponse("Error al conectar con el servicio de usuario: " + e.getMessage(), false);
        }

        // Verificar si el usuario no existe
        if (usuario == null) {
            return new LoginResponse("Usuario no encontrado", false);
        }

        // Verificar si la contraseña es correcta
        if (usuario.getPassword() == null || !usuario.getPassword().equals(request.getPassword())) {
            return new LoginResponse("Contraseña incorrecta", false);
        }

        return new LoginResponse("Login exitoso", true);
    }
}

