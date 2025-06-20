package com.example.resenas.Dto;

import lombok.Data;

@Data
public class ReseniaDto {
    private String comentario;
    private int calificacion;
    private Long servicioId;
    private Long usuarioId;
}
