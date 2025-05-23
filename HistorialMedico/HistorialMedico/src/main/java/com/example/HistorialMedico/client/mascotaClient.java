package com.example.HistorialMedico.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.HistorialMedico.dto.mascotaDTO;

@FeignClient(name = "microservicioMascota", url = "http://localhost:8083/api/v1/mascota")
public interface mascotaClient {

    @GetMapping("/IdMascota/{IdMascota}")
    mascotaDTO findByIdMascota(@PathVariable("IdMascota") String IdMascota);

}
