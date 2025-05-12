package com.petcare.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String mensaje;
    private String email;
    private String rol;
}
