package com.pruebas.Unitarias.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pruebas.Unitarias.model.Mascotas;
import com.pruebas.Unitarias.repository.MascotaRepository;

@Service
public class MascotaService {
    @Autowired
    MascotaRepository mRepo;

    public Mascotas guardarMascotas(Mascotas mascota){
        return mRepo.save(mascota);
    }
    public List<Mascotas> listarMascotas(){
        return mRepo.findAll();
    }
    public Optional<Mascotas> obtenerMascota(Long id){
        return mRepo.findById(id);
    }
    public Mascotas actualizarMascotas(Long id, Mascotas mascota){
        Mascotas existente = mRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No existe la mascota"));
        existente.setNombre(mascota.getNombre());
        existente.setTipo(mascota.getTipo());
        existente.setEdad(mascota.getEdad());
        return mRepo.save(existente);
    }
    public void eliminarMascota(Long id){
        mRepo.deleteById(id);
    }
}
