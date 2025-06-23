package com.example.resenas.Dto;

import lombok.Data;

@Data
public class ServicioDto {
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private int precio;

    public ServicioDto(Long idServicio, String nombre, int precio) {
    this.idServicio = idServicio;
    this.nombre = nombre;
    this.precio = precio;
}
}
