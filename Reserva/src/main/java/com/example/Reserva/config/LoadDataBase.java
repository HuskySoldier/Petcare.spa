package com.example.Reserva.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Reserva.model.Estado;
import com.example.Reserva.repository.EstadoRepository;

@Configuration
public class LoadDataBase {
     @Bean
    public CommandLineRunner loadInitialData(EstadoRepository estadoRepository) {
        return args -> {
            if (estadoRepository.count() == 0) {
                Estado pendiente = new Estado(null, "Pendiente", "La reserva está pendiente", null);
                Estado confirmada = new Estado(null, "Confirmada", "La reserva ha sido confirmada", null);
                Estado cancelada = new Estado(null, "Cancelada", "La reserva ha sido cancelada", null);

                estadoRepository.save(pendiente);
                estadoRepository.save(confirmada);
                estadoRepository.save(cancelada);

                System.out.println("Estados iniciales cargados en la base de datos.");
            } else {
                System.out.println("Ya existen estados en la base de datos. No se cargaron nuevos.");
            }
        };
    }

}
