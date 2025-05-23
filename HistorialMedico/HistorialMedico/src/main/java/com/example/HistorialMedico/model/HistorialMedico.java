package com.example.HistorialMedico.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class HistorialMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historialId;

    @NotNull(message = "La fecha de registro no puede ser nula")
    @Column(nullable = false)
    private Date fechaRegistro;

    @NotBlank(message = "Los antecedentes no pueden estar en blanco")
    @Column(nullable = false)
    private String antecedente;

    
    @Column(nullable = false)
    private String comentario;

    @NotBlank(message = "El diagnóstico no puede estar en blanco")
    @Column(nullable = false)
    private String diagnostico;

    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("historialMedico")
    private List<Tratamiento> tratamientos = new ArrayList<>();
}
