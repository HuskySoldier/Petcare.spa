package com.example.HistorialMedico.model;


import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tratamiento")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "Tratamiento",
    description = "Entidad que representa un tratamiento médico aplicado a una mascota"
)
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Identificador único del tratamiento",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long tratamientoId;

    @Column(nullable = false)
    @Schema(
        description = "Fecha en que se realizó el tratamiento",
        example = "2024-05-01",
        required = true
    )
    private LocalDate fechaTratamiento;

    @Column(nullable = false)
    @Schema(
        description = "Descripción del tratamiento realizado",
        example = "Aplicación de antibiótico",
        required = true
    )
    private String descripcion;

    @Column(nullable = false)
    @Schema(
        description = "Identificador del inventario utilizado",
        example = "5",
        required = true
    )
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "historialId", nullable = false)
    @JsonIgnoreProperties("tratamientos")
    @Schema(
        description = "Historial médico asociado al tratamiento"
    )
    private HistorialMedico historialMedico;
}
