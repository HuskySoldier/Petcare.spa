package com.petcare.login_service.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestBCrypt {
    public static void main(String[] args) {
        String raw = "secreta123";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(raw);
        System.out.println("Nuevo hash: " + hash);
        System.out.println("Verifica: " + encoder.matches(raw, hash)); // Debe imprimir true
    }
}
