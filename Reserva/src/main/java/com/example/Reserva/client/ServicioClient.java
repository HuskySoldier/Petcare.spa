package com.example.Reserva.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Reserva.dto.MascotaDTO;

@FeignClient(name="Servicios" ,url = "http://localhost:8090/api/Servicios")
public interface ServicioClient {
    @GetMapping("/idServicio/{idServicio}")
    MascotaDTO findByIdServicio(@PathVariable String idServicio);

}
