package com.upc.crediApp.service.inter;

import com.upc.crediApp.dto.CustomerDto;
import com.upc.crediApp.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAllCustomers();

    Customer createCustomer(CustomerDto customerDto);

    Customer getCustomerById(Long id);

    Customer updateCustomer(Long id,CustomerDto customerDto);

    void deleteCustomer(Long id);
}
