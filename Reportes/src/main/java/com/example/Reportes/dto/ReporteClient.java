package com.example.Reportes.dto;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Reportes.model.Reporte;

@FeignClient(name = "Reportesinv", url = "http://localhost:8088")
public interface ReporteClient {
    @PostMapping("/reporte")
    Reporte crearReporte(@RequestBody Reporte reporte);
}
