package com.example.Reserva.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/reserva/**").permitAll()  // 🔓 Deja pasar todas las rutas de reserva
                .anyRequest().permitAll() // 🔓 Si quieres, puedes abrir todo temporalmente
            );

        return http.build();
    }
}

