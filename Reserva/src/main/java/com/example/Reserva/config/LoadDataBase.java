package com.example.Reserva.config;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Reserva.model.Estado;
import com.example.Reserva.model.Reserva;
import com.example.Reserva.repository.EstadoRepository;
import com.example.Reserva.repository.ReservaRepository;

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

                System.out.println(" Estados iniciales cargados en la base de datos.");
            } else {
                System.out.println(" Ya existen estados en la base de datos.");
            }
        };
    }

    @Bean
    public CommandLineRunner loadReservaData(ReservaRepository reservaRepository, EstadoRepository estadoRepository) {
        return args -> {
            if (reservaRepository.count() == 0) {
                Estado estado = estadoRepository.findById(1L).orElse(null);
                if (estado == null) {
                    System.out.println(" Estado con ID 1 no encontrado. No se pueden cargar reservas.");
                    return;
                }

                Reserva reserva1 = new Reserva(null,
                        Date.valueOf(LocalDate.now()),
                        Date.valueOf(LocalDate.now().plusDays(2)),
                        1L,
                        1L,
                        25000,
                        estado);

                Reserva reserva2 = new Reserva(null,
                        Date.valueOf(LocalDate.now()),
                        Date.valueOf(LocalDate.now().plusDays(3)),
                        1L,
                        2L,
                        30000,
                        estado);

                reservaRepository.save(reserva1);
                reservaRepository.save(reserva2);

                System.out.println(" Reservas de prueba cargadas correctamente.");
            } else {
                System.out.println(" Ya existen reservas en la base de datos.");
            }
        };
    }
}