package com.example.resenas.Dto;

import lombok.Data;

@Data
public class ReseniaResponseDto {

    private Long idResenia;
    private String comentario;
    private int calificacion;
    private Long usuarioId;
    // Datos del servicio (usando el Feign Client)
    private Long servicioId;
    private String servicioNombre;
    private int servicioPrecio;

}
