package com.example.Inventario.model;

import java.sql.Date;


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

    @Min(value = 0, message = "El ID del producto no puede ser negativo")
    @Column(nullable = false) 
    private int idProducto;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúñÑ ]+$", message = "El apellido solo puede contener letras")
    @Column(nullable = false)
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
}
