package com.example.Reserva.client;



import com.example.Reserva.dto.UsuarioDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://localhost:8082/usuarios")
public interface UsuarioClient {

    @GetMapping("/{id}")
    ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable("id") Long id);
}


