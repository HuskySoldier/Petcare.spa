package com.example.Reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Reserva.dto.MascotaDTO;

@FeignClient(name="HistorialMedico" ,url = "http://localhost:8083/api/HistorialMedico")
public interface HistorialClient {
    @GetMapping("/historialId/{historialId}")
    MascotaDTO findByidhistorial(@PathVariable String historialId);

}
