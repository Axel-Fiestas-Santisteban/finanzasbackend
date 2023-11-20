package com.upc.crediApp.service.inter;

import com.upc.crediApp.dto.DatosEntradaCronograma;
import com.upc.crediApp.model.Cronograma;
import com.upc.crediApp.model.Cuota;
import com.upc.crediApp.model.Informacion;

import java.util.List;

public interface CronogramaService {

    List<Cronograma> getAllCronogramas();
    List<Cuota>getAllCuotasByCronograma(Long id);
    Informacion getInformacionByCronograma(Long id);
    void deleteCronograma(Long id);

    Cronograma saveCronograma(Long planPagoId, DatosEntradaCronograma variablesCalculo);

    List<Cronograma> getAllCronogramasByPlanPagoId(Long customerId);
}
