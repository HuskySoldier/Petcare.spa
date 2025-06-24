package com.example.resenas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Resenas.client")
public class ReseniasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReseniasApplication.class, args);
	}
}
//http://localhost:8091/swagger-ui/index.html#/

/*http://localhost:8091/api/v1/resena
 * 
 * {
    "comentario": "asd",
    "calificacion": 4,
    "servicioId":4
}
 */