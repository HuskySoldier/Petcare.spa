package com.example.HistorialMedico.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.misc.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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
    private String antecedente;

    @Column(nullable = false)
    private String comentario;

    
    @Column(nullable = false)
    private String diagnostico;

    @OneToMany(mappedBy = "historialMedico", cascade = CascadeType.ALL, orphanRemoval = true)
    /*
     * orphanRemoval encarga de eliminar automáticamente los tratamientos ya
     * eliminados
     */
    @JsonIgnoreProperties("historialMedico")
    private List<Tratamiento> tratamientos = new ArrayList<>();

}
