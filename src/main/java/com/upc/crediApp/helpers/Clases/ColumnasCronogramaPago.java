package com.upc.crediApp.helpers.Clases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ColumnasCronogramaPago {
    public int numeroCuota;
    public String fechaVencimiento;
    public String tipoPlazo;
    public double saldoInicial;
    public double interes;
    public double cuota;
    public double amortizacion;
    public double seguroDesgravamen;
    public double seguroVehicular;
    public double portes;
    public double costosNotariales;
    public double costosRegistrales;
    public double saldoFinal;
    public double flujo;
}
