package com.example.resenas.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.resenas.model.Resenia;
import com.example.resenas.repository.ReseniaRepository;

@Configuration
public class LoadDataBase {
    @Bean
    CommandLineRunner initDatabase(ReseniaRepository reseniaRepository) {
        return args -> {
            if (reseniaRepository.count() == 0) {
                // Insertar reseñas por defecto
                Resenia r1 = new Resenia(null, "Muy buen servicio", 9, 1L);
                Resenia r2 = new Resenia(null, "Excelente atención", 10, 2L);
                Resenia r3 = new Resenia(null, "Podría ser mejor", 6, 3L);

                reseniaRepository.save(r1);
                reseniaRepository.save(r2);
                reseniaRepository.save(r3);

                System.out.println("Reseñas iniciales cargadas");
            } else {
                System.out.println("ℹLas reseñas ya existen. No se han creado nuevas");
            }
        };
    }
}
