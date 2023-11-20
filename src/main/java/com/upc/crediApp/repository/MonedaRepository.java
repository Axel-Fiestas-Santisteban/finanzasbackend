package com.upc.crediApp.repository;

import com.upc.crediApp.model.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonedaRepository extends JpaRepository<Moneda, Long> {

    Moneda findByNombre(String nombre);
}
