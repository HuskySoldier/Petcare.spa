package com.example.Reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Reserva.dto.MascotaDTO;

@FeignClient(name="microservicioMascota" ,url = "http://localhost:8083/api/mascota")
public interface MascotaClient {
    @GetMapping("/IdMascota/{IdMascota}")
    MascotaDTO findByIdMascota(@PathVariable String IdMascota);

}
