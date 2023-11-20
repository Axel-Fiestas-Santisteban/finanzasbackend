package com.upc.crediApp.service.inter;

import com.upc.crediApp.model.Cuota;

import java.util.List;

public interface CuotaService {

    List<Cuota> getAllCuotas();

    Cuota createCuota(Cuota cuota);
}

