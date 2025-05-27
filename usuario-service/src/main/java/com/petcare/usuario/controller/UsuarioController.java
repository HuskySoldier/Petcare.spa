package com.petcare.usuario.controller;

import com.petcare.usuario.model.Usuario;
import com.petcare.usuario.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    // Buscar usuario por email
    @GetMapping("/by-email")
    public ResponseEntity<Usuario> findByEmail(@RequestParam String email) {
        return usuarioRepository.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        // Encriptar contraseña antes de guardar
        usuario.setPassword(encoder.encode(usuario.getPassword()));
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

            // Si viene nueva password, encriptarla
            if (usuarioActualizado.getPassword() != null) {
                usuario.setPassword(encoder.encode(usuarioActualizado.getPassword()));
            }

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

            if (usuarioParcial.getNombre() != null) {
                usuario.setNombre(usuarioParcial.getNombre());
            }
            if (usuarioParcial.getEmail() != null) {
                usuario.setEmail(usuarioParcial.getEmail());
            }
            if (usuarioParcial.getPassword() != null) {
                usuario.setPassword(encoder.encode(usuarioParcial.getPassword()));
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

    // PUT: Cambiar solo la contraseña (encriptada)
    @PutMapping("/{id}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long id, @RequestBody Map<String, String> request) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            String nuevaPassword = request.get("password");
            if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La contraseña no puede estar vacía.");
            }

            usuario.setPassword(encoder.encode(nuevaPassword));
            usuarioRepository.save(usuario);

            return ResponseEntity.ok("Contraseña actualizada exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }
    }
}
