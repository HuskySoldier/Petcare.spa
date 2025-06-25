package com.example.resenas.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table(name = "resenas")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Resenia",
    description = "Entidad que representa una reseña de un servicio"
)
public class Resenia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la reseña", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idResenia;

    @Column(length = 100)
    @Schema(description = "Comentario de la reseña", example = "Muy buen servicio")
    private String comentario;

    @Column(nullable = false)
    @Schema(description = "Calificación otorgada", example = "5", required = true)
    private int calificacion;

    @Column(nullable = false)
    @Schema(description = "Identificador del servicio reseñado", example = "2", required = true)
    private Long servicioId;

    @Column(nullable = false)
    @Schema(description = "Identificador del usuario que realizó la reseña", example = "3", required = true)
    private Long usuarioId;
}
