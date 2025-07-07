package com.example.Reserva.client;


import com.example.Reserva.dto.VeterinarioDTO;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "veterinario", url = "http://localhost:8084/api/v1/veterinario")
public interface VeterinarioClient {

    @GetMapping("/{id}")
    ResponseEntity<VeterinarioDTO> obtenerVeterinarioPorId(@PathVariable("id") Long id, 
        @RequestHeader("X-USER-ID") Long usuarioId);

    @GetMapping("/Total")
    List<VeterinarioDTO> listarVeterinarios(@RequestHeader("X-USER-ID") Long usuarioId);

}






