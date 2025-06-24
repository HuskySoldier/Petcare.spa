package com.example.veterinario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.veterinario.client.VeterinarioClient;
import com.example.veterinario.dto.UsuarioDTO;
import com.example.veterinario.model.Veterinario;
import com.example.veterinario.repository.VeterinarioRepository;

@Service
public class VeterinarioService {
    @Autowired
    private VeterinarioRepository veterinariorepository;

    public List<Veterinario> listarVeterinarios() {
        return veterinariorepository.findAll();
    }

    @Autowired
    private VeterinarioClient veterinarioClient;

    public Veterinario agregarVeterinario(Veterinario veterinario, Long idUsuario) {
        // Validar que el usuario que hace la petición tenga rol ADMINISTRADOR o
        // JEFE_CLINICA
        UsuarioDTO usuario = obtenerUsuarioDesdeClient(idUsuario);
        validarAccesoPorRol(usuario.getRol(), List.of("ADMINISTRADOR", "JEFE_CLINICA"));

        // Validar que el veterinarioId no esté seteado (crear nuevo solo)
        if (veterinario.getVeterinarioId() != null) {
            throw new IllegalArgumentException("No debe enviar el veterinarioId al crear un nuevo veterinario.");
        }

        // Validar que el usuarioId del veterinario exista en microservicio usuario
        try {
            ResponseEntity<UsuarioDTO> response = veterinarioClient.findById(veterinario.getUsuarioId());
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new IllegalArgumentException("El ID del usuario no existe en el microservicio de usuario.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al validar el ID del usuario: " + e.getMessage());
        }

        return veterinariorepository.save(veterinario);
    }

    // Método auxiliar para obtener usuario desde cliente
    private UsuarioDTO obtenerUsuarioDesdeClient(Long idUsuario) {
        ResponseEntity<UsuarioDTO> response = veterinarioClient.findById(idUsuario);
        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Usuario autenticado no encontrado o no válido");
        }
        return response.getBody();
    }

    // Método para validar roles
    private void validarAccesoPorRol(String rolUsuario, List<String> rolesPermitidos) {
        if (rolUsuario == null || !rolesPermitidos.contains(rolUsuario.toUpperCase())) {
            throw new RuntimeException("Acceso denegado: no tienes permisos para realizar esta acción.");
        }
    }

    public Veterinario buscarVeterinarioPorId(Long id) {
        return veterinariorepository.findById(id).orElse(null);
    }

    public void eliminarVeterinario(Long id) {
        veterinariorepository.deleteById(id);
    }

}
