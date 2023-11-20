package com.upc.crediApp.repository;

import com.upc.crediApp.model.TasaInteres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasaInteresRepository extends JpaRepository<TasaInteres, Long> {
    TasaInteres findByTipoAndPlazo(String tipo, String plazo);
}
