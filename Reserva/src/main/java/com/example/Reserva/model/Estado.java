package com.example.Reserva.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    name = "Estado",
    description = "Entidad que representa el estado de una reserva"
)
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del estado", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long estadoId;

    @NotBlank(message = "El nombre del estado no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Nombre del estado", example = "Confirmada", required = true)
    private String nombreEstado;
    
    @Column(nullable = false, length = 100)
    @Schema(description = "Descripción del estado", example = "Reserva confirmada por el sistema", required = true)
    private String descripcion;

    @OneToMany(mappedBy = "estado",cascade= CascadeType.ALL)
    @JsonIgnore
    @Schema(description = "Lista de reservas asociadas a este estado")
    List<Reserva> reserva;
}
