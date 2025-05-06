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
    private Integer inventario_id;

    @Column(nullable = false)
    private int medicamento_id;

    @Column(nullable = false)
    private int stock_actual;

    @Column(nullable = false)
    private int stock_minimo;

    @Column(nullable = false)
    private Date fecha_ultima_actualizacion;
}
