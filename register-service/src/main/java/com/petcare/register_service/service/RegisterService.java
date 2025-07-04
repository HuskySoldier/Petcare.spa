package com.petcare.register_service.service;

import com.petcare.register_service.client.UsuarioClient;
import com.petcare.register_service.dto.*;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RegisterService {

    @Autowired
    private UsuarioClient usuarioClient;

    public RegisterResponse register(UsuarioDTO usuarioDTO) {

        // Validaciones adicionales por si las anotaciones @NotBlank fallan
        if (!StringUtils.hasText(usuarioDTO.getNombre()) ||
            !StringUtils.hasText(usuarioDTO.getApellido()) ||
            !StringUtils.hasText(usuarioDTO.getEmail()) ||
            !StringUtils.hasText(usuarioDTO.getPassword()) ||
            !StringUtils.hasText(usuarioDTO.getTelefono())) {
            throw new RuntimeException("Todos los campos son obligatorios");
        }

        try {
            UsuarioDTO existente = usuarioClient.buscarPorEmail(usuarioDTO.getEmail());
            if (existente != null) {
                throw new RuntimeException("Ya existe un usuario registrado con ese correo electrónico");
            }
        } catch (FeignException.NotFound e) {
            // OK: El usuario no existe, se puede registrar
        } catch (FeignException e) {
            throw new RuntimeException("Error al verificar si el usuario ya está registrado");
        }

        // Crear nuevo usuario
        UsuarioDTO nuevo = new UsuarioDTO();
        nuevo.setNombre(usuarioDTO.getNombre());
        nuevo.setApellido(usuarioDTO.getApellido());
        nuevo.setEmail(usuarioDTO.getEmail());
        nuevo.setTelefono(usuarioDTO.getTelefono());
        // Enviar la contraseña en texto plano
        nuevo.setPassword(usuarioDTO.getPassword());
        nuevo.setRol("CLIENTE");

        try {
            UsuarioDTO creado = usuarioClient.crearUsuario(nuevo);
            return new RegisterResponse("Registro exitoso", creado.getEmail(), creado.getRol());
        } catch (FeignException e) {
            throw new RuntimeException("Usuario ya registrado: " + e.getMessage());
        }
    }
}
