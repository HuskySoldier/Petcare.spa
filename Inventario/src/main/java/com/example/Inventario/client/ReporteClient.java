package com.example.Inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Inventario.dto.ReporteDto;

@FeignClient(name = "Reportesinv", url = "http://localhost:8088")
public interface ReporteClient {

    @PostMapping("/api/v1/reporte")
    ReporteDto crearReporte(@RequestBody ReporteDto reporte);
}
