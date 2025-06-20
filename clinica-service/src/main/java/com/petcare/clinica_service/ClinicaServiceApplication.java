package com.petcare.clinica_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClinicaServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicaServiceApplication.class, args);
    }
}
//SWAGGER
//http://localhost:8019/swagger-ui/index.html#/
//http://localhost:8019/clinicas
//consulta en Postman
//{
// "nombre": "PetCare Central",
//"direccion": "Av. Siempre Viva 742",
//"comuna": "Santiago Centro",
//"capacidad": "100 pacientes"
//}