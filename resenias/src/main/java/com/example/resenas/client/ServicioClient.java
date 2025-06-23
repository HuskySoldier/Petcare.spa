package com.example.resenas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.resenas.Dto.ServicioDto;

@FeignClient(name = "Servicios", url = "http://localhost:8090")
public interface ServicioClient {

    @GetMapping("/api/v1/servicio/{id}")
    ServicioDto getServicioById(@PathVariable("id") Long id);
}
