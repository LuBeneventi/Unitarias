package com.pruebas.Unitarias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebas.Unitarias.model.Mascotas;
import com.pruebas.Unitarias.service.MascotaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerTodas() throws Exception {
        Mascotas m1 = new Mascotas(1L, "Toby", "Perro", 3);
        Mascotas m2 = new Mascotas(2L, "Michi", "Gato", 1);

        Mockito.when(mascotaService.listarMascotas()).thenReturn(Arrays.asList(m1, m2));

        mockMvc.perform(get("/api/v1/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Toby"))
                .andExpect(jsonPath("$[1].tipo").value("Gato"));
    }

    @Test
    void testCrearMascota() throws Exception {
        Mascotas nueva = new Mascotas(null, "Toby", "Perro", 3);
        Mascotas guardada = new Mascotas(1L, "Toby", "Perro", 3);

        Mockito.when(mascotaService.guardarMascotas(any(Mascotas.class)))
                .thenReturn(guardada);

        mockMvc.perform(post("/api/v1/mascotas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nueva)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("Toby"));
    }

    @Test
    void testObtenerPorIdNoExistente() throws Exception {
        Mockito.when(mascotaService.obtenerMascota(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/mascotas/99"))
                .andExpect(status().isNotFound());
    }
}
