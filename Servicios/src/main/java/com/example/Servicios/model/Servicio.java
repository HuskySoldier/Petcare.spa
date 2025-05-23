package com.example.Servicios.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="servicio") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Servicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idServicio;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private int precio;
}
