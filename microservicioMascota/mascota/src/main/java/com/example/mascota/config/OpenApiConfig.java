package com.example.mascota.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
        .info(new Info()
            .title("Registros de Mascotas")
            .version("1.0")
            .description("Registros de las mascotas que estan registradas o por registrar en la clinica"));
    }


}
