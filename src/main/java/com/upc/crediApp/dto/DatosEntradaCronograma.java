package com.upc.crediApp.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Data;

@Data
public class DatosEntradaCronograma {

    //Informacion correspondiente al vehiculo
    public double precioVehiculo;
    public String marcaVehiculo;
    public String modeloVehiculo;

    //Informacion correspondiente a la moneda
    public String tipoMoneda;

    //Informacion correspondiente al cronograma
    public int numeroAnios;
    public double porcentajeCuotaInicial;
    public String tipoTasaInteres;
    public String plazoTasaInteres;
    public double porcentajeTasaInteres;
    public String capitalizacion;

    //Plazo de Gracia
    @Nullable
    public String plazoDeGracia;
    //Controlar la validación, si plazoDeGracia es nulo, el plazo de gracia parcial y total son 0
    public Integer tiempoPlazoDeGraciaParcial; //Parcial
    public Integer tiempoPlazoDeGraciaTotal;// Total


    public double porcentajeSeguroDesgravamen;
    public String tiempoSeguroDesgravamen;
    public double porcentajeSeguroVehicular;
    public String tiempoSeguroVehicular;
    public double portes;
    public double costosRegistrales;
    public double costosNotariales;
    public String frecuenciaPago;
    public String fechaInicio;
    public double porcentajeCuotaFinal;
    public double cokAnual;

    //Información correspondiente al tipo de cronograma
    public String tipoCronograma;

    //Informacion correspondiente al auto tasado
    public double valorAutoTasado;

    //Renovacion/devolver/comprarlo -> decision
    //Saldo anterior = 0
    //Saldo siguiente = 0 -> se calcularia en la cuota 25
}
