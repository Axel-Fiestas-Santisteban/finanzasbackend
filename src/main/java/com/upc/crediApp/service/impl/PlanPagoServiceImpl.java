package com.upc.crediApp.service.impl;

import com.upc.crediApp.dto.PlanPagoDto;
import com.upc.crediApp.exception.ValidationException;
import com.upc.crediApp.model.PlanPago;
import com.upc.crediApp.repository.CustomerRepository;
import com.upc.crediApp.repository.PlanPagoRepository;
import com.upc.crediApp.service.inter.PlanPagoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanPagoServiceImpl implements PlanPagoService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PlanPagoRepository planPagoRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PlanPagoServiceImpl(){
        this.modelMapper = new ModelMapper();
    }

    private PlanPago DtoToEntity(PlanPagoDto planPagoDto){
        return modelMapper.map(planPagoDto, PlanPago.class);
    }
    private PlanPagoDto EntityToDto(PlanPago planPago){
        return modelMapper.map(planPago, PlanPagoDto.class);
    }
    @Override
    public PlanPago createPlanPagos(PlanPagoDto planPagoDto,Long customerId) {
        validateCustomer(customerId);
        PlanPago planPago = DtoToEntity(planPagoDto);
        planPago.setCustomer(customerRepository.findById(customerId).get());
        return planPagoRepository.save(planPago);
    }

    private void validateCustomer(Long customerId){
        customerRepository.findById(customerId).orElseThrow(() -> new ValidationException("No existe el customer"));
    }

    @Override
    public List<PlanPago> getAllPlanPagosByCustomerId(Long customerId) {
        validateCustomer(customerId);
        return planPagoRepository.findAllByCustomerId(customerId);
    }

}
