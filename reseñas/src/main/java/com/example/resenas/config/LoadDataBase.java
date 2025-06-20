package com.example.resenas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.resenas.Dto.UsuarioDto;
import com.example.resenas.client.UsuarioClient;
import com.example.resenas.model.Resenia;
import com.example.resenas.repository.ReseniaRepository;

import feign.FeignException;

@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(ReseniaRepository reseniaRepository, UsuarioClient usuarioClient) {
        return args -> {
            if (reseniaRepository.count() == 0) {
                try {
                    // Obtener los usuarios para asegurar que existen y usar sus IDs
                    UsuarioDto usuario1 = usuarioClient.obtenerUsuarioPorId(1L);
                    UsuarioDto usuario2 = usuarioClient.obtenerUsuarioPorId(2L);
                    UsuarioDto usuario3 = usuarioClient.obtenerUsuarioPorId(3L);

                    // Insertar reseñas con IDs correctos
                    Resenia r1 = new Resenia(null, "Muy buen servicio", 9, 1L, usuario1.getId());
                    Resenia r2 = new Resenia(null, "Excelente atención", 10, 2L, usuario2.getId());
                    Resenia r3 = new Resenia(null, "Podría ser mejor", 6, 3L, usuario3.getId());

                    reseniaRepository.save(r1);
                    reseniaRepository.save(r2);
                    reseniaRepository.save(r3);

                    System.out.println("✅ Reseñas iniciales cargadas correctamente.");
                } catch (FeignException.NotFound e) {
                    System.out.println("❌ Alguno de los usuarios no existe. No se cargaron reseñas.");
                } catch (Exception e) {
                    System.out.println("❌ Error inesperado: " + e.getMessage());
                }
            } else {
                System.out.println("ℹ Las reseñas ya existen. No se han creado nuevas");
            }
        };
    }
}