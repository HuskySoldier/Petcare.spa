package com.example.Servicios.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.model.Servicio;
import com.example.Servicios.repository.CategoriaRepository;
import com.example.Servicios.repository.ServicioRepository;


@Configuration
public class LoadDataBase {

    @Bean
    CommandLineRunner initDatabase(CategoriaRepository categoriaRepository, ServicioRepository servicioRepository) {
        return args -> {
            // Si no hay registros en la tabla SERVICIO (para evitar duplicados)
            if (servicioRepository.count() == 0) {

                // Crear y guardar categorías primero
                Categoria banio = new Categoria();
                banio.setNombreCategoria("Baño");
                categoriaRepository.save(banio);

                Categoria unias = new Categoria();
                unias.setNombreCategoria("Corte de uñas");
                categoriaRepository.save(unias);

                Categoria pelo = new Categoria();
                pelo.setNombreCategoria("Limpieza de pelo");
                categoriaRepository.save(pelo);

                // Crear y guardar servicios asociados a las categorías ya guardadas
                servicioRepository.save(new Servicio(null, "Baño", "Limpieza de cuerpo completo", 9900, banio));
                servicioRepository.save(new Servicio(null, "Uñas", "Cortar uñas", 15990, unias));
                servicioRepository.save(new Servicio(null, "Pelo", "Cortar el pelo", 12990, pelo));

                System.out.println("✅ Datos iniciales cargados");
            } else {
                System.out.println("ℹ️  Datos ya existen. No se han creado nuevos.");
            }
        };
    }
}