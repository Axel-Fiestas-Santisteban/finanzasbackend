package com.upc.crediApp.controller;

import com.upc.crediApp.dto.CustomerDto;
import com.upc.crediApp.model.Customer;
import com.upc.crediApp.service.inter.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crediApp/v1/Customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Transactional(readOnly = true)
    @GetMapping()
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), org.springframework.http.HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        return new ResponseEntity<>(customerService.getCustomerById(id), org.springframework.http.HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.createCustomer(customerDto), org.springframework.http.HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,@RequestBody CustomerDto customerDto){
        return new ResponseEntity<>(customerService.updateCustomer(id,customerDto), org.springframework.http.HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }


}
