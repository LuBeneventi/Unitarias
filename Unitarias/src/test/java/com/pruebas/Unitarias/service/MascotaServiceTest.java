package com.pruebas.Unitarias.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void TestObtenerMascota() {
        Mascotas mascotaMock = new Mascotas();
        mascotaMock.setId(1L); 
        when(mRepo.findById(1L)).thenReturn(Optional.of(mascotaMock));

        Optional<Mascotas> result = mService.obtenerMascota(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(mRepo).findById(1L);
    }

    @Test
    public void TestMascotaNoExiste() {
        when(mRepo.findById(2L)).thenReturn(Optional.empty());

        Optional<Mascotas> result = mService.obtenerMascota(2L);

        assertFalse(result.isPresent());
        verify(mRepo).findById(2L);
    }

    @Test
    public void TestActualizarInfo() {
        Mascotas existente = new Mascotas(1L, "Cher", "Gato", 3);

        Mascotas nuevaMascota = new Mascotas(null, "Max", "Perro", 5);

        Mascotas mascotaActualizada = new Mascotas(1L, "Max", "Perro", 5);

        when(mRepo.findById(1L)).thenReturn(Optional.of(existente));
        when(mRepo.save(any(Mascotas.class))).thenReturn(mascotaActualizada);

        Mascotas resultado = mService.actualizarMascotas(1L, nuevaMascota);

        assertNotNull(resultado);
        assertThat(resultado.getNombre()).isEqualTo("Max");
        assertThat(resultado.getTipo()).isEqualTo("Perro");
        assertThat(resultado.getEdad()).isEqualTo(5);
        verify(mRepo).findById(1L);
        verify(mRepo).save(existente);
    }

    @Test
    public void TestErrorActualizar() {
        Mascotas nuevaMascota = new Mascotas(12L,"Pelusa","Conejo",2);

        when(mRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            mService.actualizarMascotas(99L, nuevaMascota);
        });

        assertEquals("No existe la mascota", exception.getMessage());

        verify(mRepo).findById(99L);
        verify(mRepo, never()).save(any()); 
    }

    @Test
    public void TestEliminarMascota() {
        Long id = 1L;

        mService.eliminarMascota(id);

        verify(mRepo, times(1)).deleteById(id);
    }

}
