package com.upc.crediApp.repository;

import com.upc.crediApp.model.PlanPago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanPagoRepository extends JpaRepository<PlanPago, Long> {

    List<PlanPago> findAllByCustomerId(Long customerId);
}
