package com.upc.crediApp.service.inter;

import com.upc.crediApp.dto.PlanPagoDto;
import com.upc.crediApp.model.Cronograma;
import com.upc.crediApp.model.PlanPago;

import java.util.List;

public interface PlanPagoService {
    List<PlanPago> getAllPlanPagosByCustomerId(Long customerId); //Obtener todos los planes de pago mediante el id del customer
    PlanPago createPlanPagos(PlanPagoDto planPagoDto, Long customerId); //Guardar un plan de pago mediante el id del customer y el plan de pago
}
