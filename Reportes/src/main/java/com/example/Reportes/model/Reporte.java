package com.example.Reportes.model;

import java.util.Date;

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
@Table(name="reporte") 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Reporte",
    description = "Entidad que representa un reporte realizado por un usuario"
)
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Schema(description = "Identificador único del reporte", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long idReporte;

    @Column(length = 250, nullable = false)
    @Schema(description = "Comentario del reporte", example = "El servicio fue excelente", required = true)
    private String comentario;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación del reporte", example = "2024-05-01", required = true)
    private Date fechaCreacion;

}
