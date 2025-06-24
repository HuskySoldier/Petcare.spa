package com.example.Reserva;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.Reserva.client")
public class ReservaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservaApplication.class, args);
	}

}
//http://localhost:8085/swagger-ui/index.html#/
//http://localhost:8085/api/v1/reserva
//SE TIENE QUE AGREGAR AL HEADERS X-USER-ID y el numero el id del usuario
/* {
  "fechaCreacion": "2025-06-24",
  "fechareserva": "2025-06-30",
  "veterinarioId": 3,
  "usuarioId": 2,
  "total": 15000,
  "estado": {
    "estadoId": 1
  }
}*/
