package com.example.HistorialMedico.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
@Table(name = "historialMedico")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class HistorialMedico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historialId;

    @Column(nullable = false)
    private Date fechaRegistro;

    @Column(nullable = false)
    private String antecedentes;

    @Column(nullable = false)
    private String comentario;

    @NotBlank(message = "Debe tener un diagnostico")
    @Column(nullable = false)
    private String diagnostico;

    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    /*con ophera se elimina de la base de datos */
    @JsonIgnore()
    private List<Tratamiento> tratamientos;

}
