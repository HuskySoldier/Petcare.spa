package com.petcare.usuario.controller;

import com.petcare.usuario.model.Usuario;
import com.petcare.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Buscar usuario por email
    @GetMapping("/by-email")
    public Usuario findByEmail(@RequestParam String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElse(null);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // PUT: Reemplazar todos los campos del usuario
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRol(usuarioActualizado.getRol());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente (PUT)");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // PATCH: Actualizar solo campos específicos del usuario
    @PatchMapping("/{id}")
    public ResponseEntity<String> patchUsuario(@PathVariable Long id, @RequestBody Usuario usuarioParcial) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Solo actualiza los campos que no estén nulos
            if (usuarioParcial.getNombre() != null) {
                usuario.setNombre(usuarioParcial.getNombre());
            }
            if (usuarioParcial.getEmail() != null) {
                usuario.setEmail(usuarioParcial.getEmail());
            }
            if (usuarioParcial.getPassword() != null) {
                usuario.setPassword(usuarioParcial.getPassword());
            }
            if (usuarioParcial.getRol() != null) {
                usuario.setRol(usuarioParcial.getRol());
            }

            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente (PATCH)");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

