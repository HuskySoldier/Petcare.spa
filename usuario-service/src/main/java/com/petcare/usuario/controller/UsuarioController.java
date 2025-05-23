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

    // Crear nuevo usuario
    @PostMapping
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
    public void deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Usuario updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        if (usuarioRepository.existsById(id)) {
            usuario.setId(id);
            return usuarioRepository.save(usuario);
        } else {
            return null; // O lanzar una excepción
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Usuario> partialUpdateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
    Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
    if (existingUsuario.isPresent()) {
        Usuario updatedUsuario = existingUsuario.get();

        if (usuario.getNombre() != null) {
            updatedUsuario.setNombre(usuario.getNombre());
        }
        if (usuario.getEmail() != null) {
            updatedUsuario.setEmail(usuario.getEmail());
        }
        if (usuario.getRol() != null) {
            updatedUsuario.setRol(usuario.getRol());
        }

        usuarioRepository.save(updatedUsuario);
        return ResponseEntity.ok(updatedUsuario); // Retorna 200 OK con el usuario actualizado
        }   else  {
        return ResponseEntity.notFound().build(); // Retorna 404 Not Found si no existe usuario
        }
    }

}
