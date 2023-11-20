package com.upc.crediApp.controller;

import com.upc.crediApp.dto.TasaInteresDto;
import com.upc.crediApp.model.TasaInteres;
import com.upc.crediApp.service.inter.TasaInteresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crediApp/v1/tasaInteres")
public class TasaInteresController {

    @Autowired
    private TasaInteresService tasaInteresService;

    //URL : http://localhost:8080/api/crediApp/v1/tasaInteres
    //Method: GET
    @GetMapping()
    public ResponseEntity<List<TasaInteres>> getAllTasaInteres(){
        return new ResponseEntity<>(tasaInteresService.getAllTasaInteres(), org.springframework.http.HttpStatus.OK);
    }

    //URL : http://localhost:8080/api/crediApp/v1/tasaInteres
    //Method: POST
    @PostMapping()
    public ResponseEntity<TasaInteres> createTasaInteres(@RequestBody TasaInteresDto tasaInteresDTO){
        return new ResponseEntity<>(tasaInteresService.createTasaInteres(tasaInteresDTO), org.springframework.http.HttpStatus.CREATED);
    }

    //URL : http://localhost:8080/api/crediApp/v1/tasaInteres/{id}
    //Method: DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTasaInteres(@PathVariable Long id){
        tasaInteresService.deleteTasaInteres(id);
        return new ResponseEntity<>(org.springframework.http.HttpStatus.NO_CONTENT);
    }



}
