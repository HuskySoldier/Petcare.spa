package com.example.Inventario;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.Inventario.client.ReporteClient;
import com.example.Inventario.client.UsuarioClient;

@SpringBootTest
class InventarioApplicationTests {

	@MockBean
    private UsuarioClient usuarioClient;

    @MockBean
    private ReporteClient reporteClient;
	
	
}
