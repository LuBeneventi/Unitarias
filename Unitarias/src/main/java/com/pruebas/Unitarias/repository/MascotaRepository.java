package com.pruebas.Unitarias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pruebas.Unitarias.model.Mascotas;

@Repository
public interface MascotaRepository extends JpaRepository<Mascotas, Long>{}
