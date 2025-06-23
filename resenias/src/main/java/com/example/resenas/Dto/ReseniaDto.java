package com.example.resenas.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReseniaDto {
    private String comentario;
    private int calificacion;
    private Long servicioId;
    private Long usuarioId;

    public ReseniaDto(String comentario, int calificacion, Long servicioId, Long usuarioId) {
    this.comentario = comentario;
    this.calificacion = calificacion;
    this.servicioId = servicioId;
    this.usuarioId = usuarioId;
}
}
