package com.upc.crediApp.service.inter;

import com.upc.crediApp.dto.VehiculoDto;
import com.upc.crediApp.model.Vehiculo;

import java.util.List;

public interface VehiculoService {

    List<Vehiculo> getAllVehiculos();
    Vehiculo createVehiculo(VehiculoDto vehiculoDto);
    void deleteVehiculo(Long id);

}
