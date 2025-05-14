package com.petcare.auth.service;

import com.petcare.auth.Client.UsuarioClient;
import com.petcare.auth.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioClient usuarioClient;

    public AuthResponse login(AuthRequest request) {
        UsuarioDTO usuario = usuarioClient.findByEmail(request.getEmail());

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        if (!BCrypt.checkpw(request.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return new AuthResponse("Login exitoso", usuario.getEmail(), usuario.getRol());
    }

    public RegisterResponse register(RegisterRequest request) {
        // Validar si ya existe
        UsuarioDTO existente = usuarioClient.findByEmail(request.getEmail());
        if (existente != null) {
            throw new RuntimeException("Ya existe un usuario con este email");
        }

        // Encriptar la contraseña
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // Crear nuevo DTO
        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombre(request.getNombre());
        nuevoUsuario.setApellido(request.getApellido());
        nuevoUsuario.setEmail(request.getEmail());
        nuevoUsuario.setTelefono(request.getTelefono());
        nuevoUsuario.setPassword(hashedPassword);
        nuevoUsuario.setRol("CLIENTE"); // Forzado

        UsuarioDTO creado = usuarioClient.crearUsuario(nuevoUsuario);

        return new RegisterResponse("Registro exitoso", creado.getEmail(), creado.getRol());
    }
}
