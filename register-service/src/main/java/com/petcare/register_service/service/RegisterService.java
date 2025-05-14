package com.petcare.register_service.service;

import com.petcare.register_service.client.UsuarioClient;
import com.petcare.register_service.dto.*;

import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private UsuarioClient usuarioClient;

    public RegisterResponse register(RegisterRequest request) {
        try {
            // Intentar buscar si el usuario ya existe por su email
            UsuarioDTO existente = usuarioClient.findByEmail(request.getEmail());

            // Si no lanza excepción, significa que ya existe → lanzar error
            if (existente != null) {
                throw new RuntimeException("Ya existe un usuario con ese email");
            }

        } catch (FeignException.NotFound e) {
            // El usuario no existe, podemos continuar con el registro
        }

        // Encriptar la contraseña
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        // Crear nuevo usuario
        UsuarioDTO nuevo = new UsuarioDTO();
        nuevo.setNombre(request.getNombre());
        nuevo.setApellido(request.getApellido());
        nuevo.setEmail(request.getEmail());
        nuevo.setTelefono(request.getTelefono());
        nuevo.setPassword(hashedPassword);
        nuevo.setRol("CLIENTE");

        // Guardar nuevo usuario a través del microservicio usuario
        UsuarioDTO creado = usuarioClient.crearUsuario(nuevo);

        // Retornar respuesta de éxito
        return new RegisterResponse("Registro exitoso", creado.getEmail(), creado.getRol());
    }
}
