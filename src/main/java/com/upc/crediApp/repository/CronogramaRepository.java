package com.upc.crediApp.repository;

import com.upc.crediApp.model.Cronograma;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CronogramaRepository extends JpaRepository<Cronograma, Long> {
    //List<Cronograma> findAllByCustomerId(Long customerId);

    List<Cronograma> findAllByPlanPagoId(Long planPagoId);
}
