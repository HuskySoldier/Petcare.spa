package com.example.mascota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.mascota.client")
public class MascotaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MascotaApplication.class, args);
	}

}
//http://localhost:8083/swagger-ui/index.html#/
//HEADERS X-USER-ID y NUMERO CLIENTE 2
//http://localhost:8083/api/v1/mascota
/*{
  "nombre": "Fido",
  "edad": 3,
  "sexo": "Macho",
  "idUsuario": 1,
  "raza": {
    "nombreRaza": "Labrador"
  },
  "especie": {
    "nombreEspecie": "Perro"
  }
}
 */