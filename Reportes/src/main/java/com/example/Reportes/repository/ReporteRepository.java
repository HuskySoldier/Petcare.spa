package com.example.Reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Reportes.model.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Long>{
   List<Reporte> findByIdReporte(int idReporte); 

}
