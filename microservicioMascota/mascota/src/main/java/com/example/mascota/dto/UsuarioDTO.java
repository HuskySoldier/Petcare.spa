package com.example.mascota.dto;


import lombok.Data;

@Data
public class UsuarioDTO {
    public UsuarioDTO(long l, String string, String string2, String string3) {
        //TODO Auto-generated constructor stub
    }
    private String nombre;
    private String apellido;
    private String rol;
    private String telefono;
    public void setRol(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRol'");
    }
    public void setRol(String l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setRol'");
    }
}
