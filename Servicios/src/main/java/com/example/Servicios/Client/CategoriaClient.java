package com.example.Servicios.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Servicios.dto.CategoriaDto;
@FeignClient(name = "reporte-service", url = "http://localhost:8089")
public class CategoriaClient {
    @PostMapping("/Categoria")
    CategoriaDto crearReporte(@RequestBody CategoriaDto categoria);

}
