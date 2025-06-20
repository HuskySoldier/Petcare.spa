package com.example.resenas.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long id; 
    private String nombre;
    private String correo;
    private String rol;

}
