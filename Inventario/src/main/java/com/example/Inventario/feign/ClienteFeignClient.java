package com.example.Inventario.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.Inventario.dto.ClientesDTO;

@FeignClient(name = "clientesFeign", url = "${clientes.url}")
public interface ClienteFeignClient {

    @GetMapping("/api/v1/pedidos/getPedidos/id")
    List<ClientesDTO>obtenerClientes(@PathVariable("id") Long id);
    
}
