package com.example.HistorialMedico.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialMascotaDTO {
    private Long historialId;
    private Date fechaRegistro;
    private String antecedentes;
    private String comentario;
    private String diagnostico;

    private Long idMascota;
    private String nombreMascota;
    private int edadMascota;
    private String sexoMascota;

    private String resumenHistorial;
}

