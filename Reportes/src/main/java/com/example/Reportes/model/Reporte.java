package com.example.Reportes.model;

import java.sql.Date;

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
@Table(name="reporte") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idReporte;

    @Column(length = 250, nullable = false)
    private String comentario;

    @Column(nullable = false)
    private Date fechaCreacion;

}
