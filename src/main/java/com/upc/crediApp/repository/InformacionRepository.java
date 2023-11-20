package com.upc.crediApp.repository;

import com.upc.crediApp.model.Informacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformacionRepository extends JpaRepository<Informacion, Long> {
}
