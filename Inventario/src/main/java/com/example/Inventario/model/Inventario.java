package com.example.Inventario.model;

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
@Table(name="inventario") 
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idInventario;

    @Column(nullable = false) 
    private int idProducto;


    @Column(nullable = false)
    private String nombreInv;

    @Column(nullable = false)
    private int stockActual;

    @Column(nullable = false)
    private int stockMinimo = 5;

    @Column(nullable = false)
    private Date fechaUltimaActualizacion;
}
