package com.upc.crediApp.controller;

import com.upc.crediApp.dto.CustomerDto;
import com.upc.crediApp.dto.PlanPagoDto;
import com.upc.crediApp.model.Customer;
import com.upc.crediApp.model.PlanPago;
import com.upc.crediApp.service.inter.PlanPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crediApp/v1/planpago")
public class PlanPagoController {
    @Autowired
    private PlanPagoService planPagoService;

    @Transactional(readOnly = true)
    @GetMapping("/{customerId}/planpago")
    public ResponseEntity<List<PlanPago>>getAllPlanPagos(@PathVariable Long customerId){
        return new ResponseEntity<>(planPagoService.getAllPlanPagosByCustomerId(customerId), org.springframework.http.HttpStatus.OK);
    }

    @PostMapping("/{customerId}/planpago")
    public ResponseEntity<PlanPago> createPlanPago(@PathVariable Long customerId,@RequestBody PlanPagoDto planPagoDto){
        return new ResponseEntity<>(planPagoService.createPlanPagos(planPagoDto,customerId), org.springframework.http.HttpStatus.CREATED);
    }

}
