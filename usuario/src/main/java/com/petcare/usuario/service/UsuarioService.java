package com.petcare.usuario.service;

import com.petcare.usuario.entity.Usuario;
import com.petcare.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUsuario(Long id, Usuario updatedUsuario) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombre(updatedUsuario.getNombre());
                    usuario.setApellido(updatedUsuario.getApellido());
                    usuario.setEmail(updatedUsuario.getEmail());
                    usuario.setPassword(updatedUsuario.getPassword());
                    usuario.setRol(updatedUsuario.getRol());
                    return usuarioRepository.save(usuario);
                })
                .orElse(null);
    }

    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
