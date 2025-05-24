package com.example.Inventario.config;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.Inventario.model.Inventario;
import com.example.Inventario.repository.InventarioRepository;
import com.example.Inventario.service.InventarioService;

@Configuration
public class LoadDataBase {
   @Bean
    CommandLineRunner initDatabase(InventarioService inventarioService, InventarioRepository inventarioRepo) {
        return args -> {
            if (inventarioRepo.count() == 0) {
                // Producto 1: Stock bajo
                Inventario desparasitante = new Inventario();
                desparasitante.setIdProducto(101);
                desparasitante.setNombreInv("Desparasitante oral");
                desparasitante.setStockActual(3); // stock bajo
                desparasitante.setStockMinimo(5);
                desparasitante.setFechaUltimaActualizacion(Date.valueOf(LocalDate.now()));
                inventarioRepo.save(desparasitante);
                inventarioService.verificarYReportarStock(desparasitante);

                // Producto 2: Stock normal
                Inventario shampoo = new Inventario();
                shampoo.setIdProducto(102);
                shampoo.setNombreInv("Shampoo medicado");
                shampoo.setStockActual(12);
                shampoo.setStockMinimo(5);
                shampoo.setFechaUltimaActualizacion(Date.valueOf(LocalDate.now()));
                inventarioRepo.save(shampoo);
                inventarioService.verificarYReportarStock(shampoo);

                // Producto 3: Stock bajo
                Inventario jeringas = new Inventario();
                jeringas.setIdProducto(103);
                jeringas.setNombreInv("Jeringas 5ml");
                jeringas.setStockActual(4); // stock bajo
                jeringas.setStockMinimo(5);
                jeringas.setFechaUltimaActualizacion(Date.valueOf(LocalDate.now()));
                inventarioRepo.save(jeringas);
                inventarioService.verificarYReportarStock(jeringas);

                System.out.println("Datos iniciales cargados y verificación de stock ejecutada.");
            } else {
                System.out.println("ℹLos datos del inventario ya existen. No se cargaron nuevos registros.");
            }
        };
    }

}
