package com.upc.crediApp.repository;

import com.upc.crediApp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByUsername(String username);
}
