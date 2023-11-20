package com.upc.crediApp.service.impl;

import com.upc.crediApp.dto.CustomerDto;
import com.upc.crediApp.exception.ValidationException;
import com.upc.crediApp.model.Customer;
import com.upc.crediApp.repository.CustomerRepository;
import com.upc.crediApp.service.inter.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    private CustomerDto EntityToDto(Customer customer){
        return modelMapper.map(customer, CustomerDto.class);
    }

    private Customer DtoToEntity(CustomerDto customerDto){
        return modelMapper.map(customerDto, Customer.class);
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {

        validationCustomer(customerDto);

        return customerRepository.save(DtoToEntity(customerDto));
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new ValidationException("No existe el customer"));
    }

    @Override
    public Customer updateCustomer(Long idCustomer,CustomerDto customerDto) {

        Customer customer = customerRepository.findById(idCustomer).orElseThrow(() -> new ValidationException("No existe el customer"));
        validationCustomer(customerDto);

        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword());
        customer.setName(customerDto.getName());
        customer.setLastName(customerDto.getLastName());
        customer.setDni(customerDto.getDni());

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {

        if(!customerRepository.existsById(id)){
            throw new ValidationException("No existe el customer");
        }

        customerRepository.deleteById(id);
    }

    private void validationCustomer(CustomerDto customerDto){

        if (customerDto.getEmail() == null || customerDto.getEmail().isEmpty()) {
            throw new ValidationException("El email no puede ser nulo o vacio");
        }

        if(customerDto.getPassword() == null || customerDto.getPassword().isEmpty()){
            throw new ValidationException("El password no puede ser nulo o vacio");
        }

        if(customerDto.getName() == null || customerDto.getName().isEmpty()){
            throw new ValidationException("El nombre no puede ser nulo o vacio");
        }

        if(customerDto.getLastName() == null || customerDto.getLastName().isEmpty()){
            throw new ValidationException("El apellido no puede ser nulo o vacio");
        }

        if(customerDto.getDni() == null || customerDto.getDni().isEmpty()){
            throw new ValidationException("El dni no puede ser nulo o vacio");
        }
        if(customerDto.getUsername() == null || customerDto.getUsername().isEmpty()){
            throw new ValidationException("El username no puede ser nulo o vacio");
        }

        if(customerRepository.existsByUsername(customerDto.getUsername())){
            throw new ValidationException("El username "+customerDto.getUsername()+" ya existe");
        }

    }

}
