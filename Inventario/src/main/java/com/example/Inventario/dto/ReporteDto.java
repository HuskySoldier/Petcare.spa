package com.example.Inventario.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReporteDto {
    private Long idInventario;
    private String nombreInv;
    private String comentario; 
    private Date fechaCreacion;

    public ReporteDto(Long idInventario, String comentario, Date fechaCreacion) {
    this.idInventario = idInventario;
    this.comentario = comentario;
    this.fechaCreacion = fechaCreacion;
    }

}
