package com.example.HistorialMedico.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tratamientoId;

    @Column(nullable = false)
    private Date fechaTratamiento;

    @Column(nullable = false)
    private String descripcion;

}
