package com.example.HistorialMedico.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tratamiento")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Tratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tratamientoId;

    @NotNull(message = "la fecha de tratamiento es obligatoria")
    @Column(nullable = false)
    private Date fechaTratamiento;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Long idInventario;

    @ManyToOne
    @JoinColumn(name = "historialId", nullable = false)
    @JsonIgnoreProperties("tratamientos")
    private HistorialMedico historialMedico;

}
