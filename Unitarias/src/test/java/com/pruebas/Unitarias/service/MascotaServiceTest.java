package com.pruebas.Unitarias.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pruebas.Unitarias.model.Mascotas;
import com.pruebas.Unitarias.repository.MascotaRepository;

class MascotaServiceTest {
    @Mock
    MascotaRepository mRepo;

    @InjectMocks
    MascotaService mService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarMascota() {
        Mascotas mascota = new Mascotas(null, "Rex", "Perro", 5);
        Mascotas mascotaGuardada = new Mascotas(1L, "Rex", "Perro", 5);
        when(mRepo.save(mascota)).thenReturn(mascotaGuardada);

        Mascotas resultado = mService.guardarMascotas(mascota);
        assertThat(resultado.getId()).isEqualTo(1L);
        verify(mRepo).save(mascota);
    }

    @Test
    void testListarMascotas() {
        Mascotas m1 = new Mascotas(1L, "Rex", "Perro", 5);
        Mascotas m2 = new Mascotas(2L, "Michi", "Gato", 2);
        when(mRepo.findAll()).thenReturn(Arrays.asList(m1, m2));

        List<Mascotas> resultado = mService.listarMascotas();
        assertThat(resultado).hasSize(2).contains(m1, m2);
        verify(mRepo).findAll();
    }
}
