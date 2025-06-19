package com.petcare.usuario.config;


import com.petcare.usuario.model.Rol;
import com.petcare.usuario.model.Usuario;
import com.petcare.usuario.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepo) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            if (usuarioRepo.findByEmail("admin@petcare.com").isEmpty()) {
                usuarioRepo.save(Usuario.builder()
                        .nombre("Admin")
                        .apellido("Principal")
                        .email("admin@petcare.com")
                        .password(encoder.encode("admin123"))
                        .telefono("111111111")
                        .rol(Rol.ADMINISTRADOR)
                        .build());
            }

            if (usuarioRepo.findByEmail("maria@petcare.com").isEmpty()) {
                usuarioRepo.save(Usuario.builder()
                        .nombre("María")
                        .apellido("López")
                        .email("maria@petcare.com")
                        .password(encoder.encode("maria123"))
                        .telefono("222222222")
                        .rol(Rol.CLIENTE)
                        .build());
            }

            if (usuarioRepo.findByEmail("carlos.vet@petcare.com").isEmpty()) {
                usuarioRepo.save(Usuario.builder()
                        .nombre("Carlos")
                        .apellido("Vet")
                        .email("carlos.vet@petcare.com")
                        .password(encoder.encode("vet123"))
                        .telefono("333333333")
                        .rol(Rol.VETERINARIO)
                        .build());
            }

            System.out.println("✅ Verificación completa: usuarios precargados si no existían.");
        };
    }
}
