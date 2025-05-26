package com.example.HistorialMedico.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.HistorialMedico.dto.InventarioDTO;

@FeignClient(name = "Inventario", url="http://localhost:8087/api/v1/inventario")
public interface InventarioClient {

    @GetMapping("/{idInventario}")
    InventarioDTO obtenerInventarioPorId(@PathVariable("idInventario") Long idInventario);


}
