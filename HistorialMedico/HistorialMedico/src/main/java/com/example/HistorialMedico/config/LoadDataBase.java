package com.example.HistorialMedico.config;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.HistorialMedico.model.HistorialMedico;
import com.example.HistorialMedico.model.Tratamiento;
import com.example.HistorialMedico.repository.HistorialMedicoRepository;
import com.example.HistorialMedico.repository.TratamientoRepository;

@Configuration
public class LoadDataBase {

    @Bean
    public CommandLineRunner loadInitialData(
            HistorialMedicoRepository historialRepo,
            TratamientoRepository tratamientoRepo) {
        return args -> {
            // Crear historial
            HistorialMedico historial1 = new HistorialMedico();
            historial1.setIdMascota(null);
            historial1.setFechaRegistro(Date.valueOf(LocalDate.now()));
            historial1 = historialRepo.save(historial1);

            // Tratamientos para el historial
            Tratamiento tratamiento1 = new Tratamiento();
            tratamiento1.setFechaTratamiento(Date.valueOf(LocalDate.now()));
            tratamiento1.setDescripcion("Vacunación contra la rabia");
            tratamiento1.setIdInventario(1L); // Debe coincidir con Inventario
            tratamiento1.setHistorialMedico(historial1);

            Tratamiento tratamiento2 = new Tratamiento();
            tratamiento2.setFechaTratamiento(Date.valueOf(LocalDate.now()));
            tratamiento2.setDescripcion("Aplicación de desparasitante");
            tratamiento2.setIdInventario(2L);
            tratamiento2.setHistorialMedico(historial1);

            tratamientoRepo.saveAll(Arrays.asList(tratamiento1, tratamiento2));
        };
    }
}