package com.upc.crediApp.service.impl;

import com.upc.crediApp.model.Moneda;
import com.upc.crediApp.repository.MonedaRepository;
import com.upc.crediApp.service.inter.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonedaServiceImpl implements MonedaService {

    @Autowired
    MonedaRepository monedaRepository;

    @Override
    public List<Moneda> getAllMonedas() {
        return monedaRepository.findAll();
    }
}
