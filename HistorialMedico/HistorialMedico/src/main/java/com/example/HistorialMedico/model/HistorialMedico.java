package com.example.HistorialMedico.model;

import java.time.LocalDate; 
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

    @NotNull(message = "La fecha de registro es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaRegistro;

    
    @NotBlank(message = "Los antecedentes son obligatorios")
    @Column(nullable = false)
    private String antecedentes;

    
    private String comentario;

    @Column(nullable = false)
    private Long idMascota;

    @NotBlank(message = "Debe tener un diagnostico")
    @Column(nullable = false)
    private String diagnostico;

    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    /*con ophera se elimina de la base de datos */
    @JsonIgnore()
    private List<Tratamiento> tratamientos;

}
