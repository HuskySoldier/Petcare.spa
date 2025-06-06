package com.example.Servicios.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Servicios.model.Servicio;

@Repository
public interface ServicioRepository extends  JpaRepository<Servicio, Long> {
    List<Servicio> findByIdServicio(int idServicio);
}
