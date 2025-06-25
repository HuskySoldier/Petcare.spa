package com.example.Reserva.model;

import java.sql.Date;

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
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "Reserva",
    description = "Entidad que representa una reserva de servicio"
)
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la reserva", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long reservaId;

    @Column(nullable = false)
    @Schema(description = "Fecha de creación de la reserva", example = "2024-05-01", required = true)
    private Date fechaCreacion;

    @Column(nullable = false)
    @Schema(description = "Fecha de la reserva", example = "2024-05-10", required = true)
    private Date fechareserva;

    @Column(name = "veterinario_id")
    @Schema(description = "Identificador del veterinario asignado", example = "7")
    private Long veterinarioId;

    @Column(name = "usuario_id", nullable = false)
    @Schema(description = "Identificador del usuario que realiza la reserva", example = "3", required = true)
    private Long usuarioId;

    @Min(value = 0, message = "El total no puede ser negativo")
    @Column(nullable = false)
    @Schema(description = "Total a pagar por la reserva", example = "20000", required = true)
    private int total;

    @ManyToOne
    @JoinColumn(name = "estadoId")
    @JsonIgnoreProperties("reserva")
    @Schema(description = "Estado actual de la reserva")
    private Estado estado;
}
