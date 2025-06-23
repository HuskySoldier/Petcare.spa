package com.example.Inventario.model;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name="inventario") 

@NoArgsConstructor
@Data
public class Inventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long idInventario; 

    @NotBlank(message = "El nombre no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$", message = "El nombre solo puede contener letras")
    @Column(nullable = false, unique = true)
    private String nombreInv;

    @Min(value = 0, message = "El stock actual no puede ser negativo")
    @Column(nullable = false)
    private int stockActual;

    @Min(value = 0, message = "El stock actual no puede ser negativo")
    @Column(nullable = false)
    private int stockMinimo = 5;

    
    @PastOrPresent(message = "La fecha debe ser actual o pasada")
    @Column(nullable = false)
    private Date fechaUltimaActualizacion;
    
    public Inventario(Long idInventario, String nombreInv, int stockActual, int stockMinimo, java.util.Date fechaUltimaActualizacion) {
    this.idInventario = idInventario;
    this.nombreInv = nombreInv;
    this.stockActual = stockActual;
    this.stockMinimo = stockMinimo;
    this.fechaUltimaActualizacion = (@PastOrPresent(message = "La fecha debe ser actual o pasada") Date) fechaUltimaActualizacion;
}
}
