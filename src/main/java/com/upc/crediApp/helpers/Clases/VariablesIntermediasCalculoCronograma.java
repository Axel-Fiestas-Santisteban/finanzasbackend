package com.upc.crediApp.helpers.Clases;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariablesIntermediasCalculoCronograma {
    public double porcentajePrestamoAFinanciar;
    public String fechaInicio;
    public double tasaEfectiva;
    public double tasaDesgravamen;
    public double tasaSeguroVehicular;
    public double montoAFinanciar;
    public double cuotaInicial;
    public double cuotaFinal;
    public int numeroCuotas;
    public int numeroCuotasPlazoGraciaParcial;
    public int numeroCuotasPlazoGraciaTotal;
}
