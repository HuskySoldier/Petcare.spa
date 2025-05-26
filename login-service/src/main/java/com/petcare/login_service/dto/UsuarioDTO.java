package com.petcare.login_service.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String rol;



public UsuarioDTO register(UsuarioDTO usuarioDTO) {
    // Crear una instancia de PasswordEncoder
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    // Cifra la contraseña antes de almacenarla
    String encodedPassword = passwordEncoder.encode(usuarioDTO.getPassword());
    usuarioDTO.setPassword(encodedPassword);

    // Guardar el usuario en la base de datos
    // Lógica para guardar el usuario en la base de datos
    return usuarioDTO;
}
}
