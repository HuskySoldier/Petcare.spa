package com.example.Inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Inventario.dto.ReporteDto;

@FeignClient(name = "reporte-service", url = "http://localhost:8083")
public interface ReporteClient {

    @PostMapping("/reporte")
    ReporteDto crearReporte(@RequestBody ReporteDto reporte);
}
