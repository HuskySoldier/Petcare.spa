package com.example.Inventario.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReporteDto {
    private int idProducto;
    private String nombreInv;
    private String comentario; 
    private Date fechaCreacion;

    public ReporteDto(int idProducto, String comentario, Date fechaCreacion) {
    this.idProducto = idProducto;
    this.comentario = comentario;
    this.fechaCreacion = fechaCreacion;
    }

}
