package com.upc.crediApp.controller;

import com.upc.crediApp.model.Moneda;
import com.upc.crediApp.service.inter.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/crediApp/v1/Moneda")
public class MonedaController {

    @Autowired
    private MonedaService monedaService;

    @Transactional(readOnly = true)
    @GetMapping()
    public ResponseEntity<List<Moneda>> getAllMonedas(){
        return new ResponseEntity<>(monedaService.getAllMonedas(), org.springframework.http.HttpStatus.OK);
    }

}
