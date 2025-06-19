package com.example.Reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Reserva.dto.VeterinarioDTO;

@FeignClient(name = "veterinario", url = "http://localhost:8083/api/v1/veterinario")
public interface VeterinarioClient {
    @GetMapping("/{id}")
    VeterinarioDTO obtenerVeterinarioPorId(@PathVariable Long id);
}
