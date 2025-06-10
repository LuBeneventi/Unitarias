package com.pruebas.Unitarias.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.Unitarias.model.Mascotas;
import com.pruebas.Unitarias.service.MascotaService;

@RestController
@RequestMapping("/api/v1/mascotas")
public class MascotaController {
    @Autowired
    MascotaService mService;

    @PostMapping
    public Mascotas creMascotas(@RequestBody Mascotas mascota){
        return mService.guardarMascotas(mascota);
    }
    @GetMapping
    public List<Mascotas> listarMascotas(){
        return mService.listarMascotas();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Mascotas> obtenerPorID(@PathVariable Long id){
        return mService.obtenerMascota(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Mascotas> actualizar(@PathVariable Long id, @RequestBody Mascotas mascota){
        try{
            Mascotas actualizada = mService.actualizarMascotas(id, mascota);
            return ResponseEntity.ok(actualizada);
        }catch(RuntimeException e){
            return ResponseEntity.noContent().build();
        }
    }
   @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mService.eliminarMascota(id);
        return ResponseEntity.noContent().build();
    }
}
