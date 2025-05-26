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
    public CommandLineRunner loadInitialData(InventarioService inventarioService,InventarioRepository inventarioRepository) {
        return args -> {
            Inventario inv1 = new Inventario();
            inv1.setIdProducto(1);
            inv1.setNombreInv("Vacuna antirrábica");
            inv1.setStockActual(10);
            inv1.setStockMinimo(5);
            inv1.setFechaUltimaActualizacion(Date.valueOf(LocalDate.now()));

            Inventario inv2 = new Inventario();
            inv2.setIdProducto(2);
            inv2.setNombreInv("Desparasitante oral");
            inv2.setStockActual(3); // Bajo stock para probar reportes
            inv2.setStockMinimo(5);
            inv2.setFechaUltimaActualizacion(Date.valueOf(LocalDate.now()));
            Inventario guardado = inventarioRepository.save(inv2);
            inventarioService.verificarYReportarStock(guardado);

            Inventario inv3 = new Inventario();
            inv3.setIdProducto(3);
            inv3.setNombreInv("Alimento seco para gatos");
            inv3.setStockActual(20);
            inv3.setStockMinimo(5);
            inv3.setFechaUltimaActualizacion(Date.valueOf(LocalDate.now()));

            inventarioRepository.save(inv1);
            inventarioRepository.save(inv2);
            inventarioRepository.save(inv3);
            
        };
    }

}
