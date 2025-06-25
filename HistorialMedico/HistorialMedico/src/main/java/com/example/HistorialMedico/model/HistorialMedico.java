package com.example.HistorialMedico.model;

import java.time.LocalDate; 
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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "historialMedico")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(
    name = "HistorialMedico",
    description = "Entidad que representa el historial médico de una mascota"
)
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
        description = "Identificador único del historial médico",
        example = "1",
        accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long historialId;

    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(nullable = false)
    @Schema(
        description = "Fecha de registro del historial",
        example = "2024-05-01",
        required = true
    )
    private LocalDate fechaRegistro;

    @NotBlank(message = "Los antecedentes son obligatorios")
    @Column(nullable = false)
    @Schema(
        description = "Antecedentes médicos de la mascota",
        example = "Alergia a penicilina",
        required = true
    )
    private String antecedentes;

    @Schema(
        description = "Comentario adicional sobre el historial",
        example = "Paciente estable"
    )
    private String comentario;

    @Column(nullable = false)
    @Schema(
        description = "Identificador de la mascota asociada",
        example = "10",
        required = true
    )
    private Long idMascota;

    @NotBlank(message = "Debe tener un diagnostico")
    @Column(nullable = false)
    @Schema(
        description = "Diagnóstico médico",
        example = "Otitis",
        required = true
    )
    private String diagnostico;

    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore()
    @Schema(
        description = "Lista de tratamientos asociados al historial"
    )
    private List<Tratamiento> tratamientos;

    

}
