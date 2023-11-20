package com.upc.crediApp.service.inter;

import com.upc.crediApp.dto.TasaInteresDto;
import com.upc.crediApp.model.TasaInteres;

import java.util.List;

public interface TasaInteresService {

    List<TasaInteres> getAllTasaInteres();

     TasaInteres createTasaInteres(TasaInteresDto tasaInteres);

        void deleteTasaInteres(Long id);

}
