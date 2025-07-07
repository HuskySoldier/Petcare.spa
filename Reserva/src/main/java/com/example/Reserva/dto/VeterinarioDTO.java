package com.example.Reserva.dto;



import lombok.Data;

@Data
public class VeterinarioDTO {
    private Long veterinarioId;
    private Integer rut;
    private String nombre;
    private String apellido;
    private String especialidad;
    private String correo;
    private Long usuarioId;
}

