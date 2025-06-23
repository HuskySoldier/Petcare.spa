package com.example.Servicios.controller;

import com.example.Servicios.model.Categoria;
import com.example.Servicios.model.Servicio;
import com.example.Servicios.service.CategoriaService;
import com.example.Servicios.service.ServicioService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServicioController.class)
class ServicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioService servicioService;

    @MockBean
    private CategoriaService categoriaService;

    // Test: Listar Categorías
    @Test
    void testListarCategoria() throws Exception {
        Categoria c1 = new Categoria(1L, "Vacunas", null);
        Categoria c2 = new Categoria(2L, "Baños", null);

        when(categoriaService.listarCategoria()).thenReturn(List.of(c1, c2));

        mockMvc.perform(get("/api/v1/servicio/categoria"))
                .andExpect(status().isOk());
    }

    // Test: Crear Categoría
    @Test
    void testCrearCategoria() throws Exception {
        Categoria nueva = new Categoria(null, "Baños", null);
        Categoria creada = new Categoria(1L, "Baños", null);

        when(categoriaService.crearCategoria(any(Categoria.class))).thenReturn(creada);

        mockMvc.perform(post("/api/v1/servicio/categoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "nombreCategoria": "Baños"
                            }
                        """))
                .andExpect(status().isCreated());
    }

    // (Opcional) Test: Obtener servicio por ID
    @Test
    void testObtenerServicioPorId_existente() throws Exception {
        Servicio servicio = new Servicio(1L, "Vacuna", "Vacunación anual", 15000, null);
        when(servicioService.obtenerServicioPorId(1L)).thenReturn(Optional.of(servicio));

        mockMvc.perform(get("/api/v1/servicio/1"))
                .andExpect(status().isOk());
    }

    // (Opcional) Test: Crear servicio
    @Test
    void testCrearServicio_valido() throws Exception {
        Categoria categoria = new Categoria(1L, "Vacunas", null);
        Servicio servicio = new Servicio(null, "Desparasitación", "Interna", 7000, categoria);
        Servicio creado = new Servicio(10L, "Desparasitación", "Interna", 7000, categoria);

        when(servicioService.crearServicio(any(Servicio.class))).thenReturn(creado);

        mockMvc.perform(post("/api/v1/servicio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                                "nombre": "Desparasitación",
                                "descripcion": "Interna",
                                "precio": 7000,
                                "categoria": {
                                    "idCategoria": 1,
                                    "nombreCategoria": "Vacunas"
                                }
                            }
                        """))
                .andExpect(status().isCreated());
    }
}