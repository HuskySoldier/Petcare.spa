package com.example.Reserva.model;

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
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservaId;

    @Column(nullable = false)
    private Date fechaCreacion;

    @Column(nullable = false)
    private Date fechareserva;

    @Column(name = "veterinario_id")
    private Long veterinarioId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Min(value = 0, message = "El total no puede ser negativo")
    @Column(nullable = false)
    private int total;

    @ManyToOne
    @JoinColumn(name = "estadoId")
    @JsonIgnoreProperties("reserva")
    private Estado estado;

}
