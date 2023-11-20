package com.upc.crediApp.helpers.Calculadora;

import com.upc.crediApp.helpers.Utilidades.Utilidades;

import java.util.List;

import org.apache.poi.ss.formula.functions.Npv;

public class CalculadoraVAN {

    public static double calcularCOKDeAcuerdoFrecuenciaPago(double COKAnual,String frecuenciaPago){

        double COKDecimal= Utilidades.devolverPorcentajeEnFormaDecimal(COKAnual);
        //LA COK SIEMPRE DEBE SER ANUAL
        double diasFrecuenciaPago= CalculadoraPlazoEnDias.devolverPlazoEnDias(frecuenciaPago);
        double diasAnio=360;
        return Utilidades.porcentajeDecimalEnFormaPorcentaje(Math.pow(1+COKDecimal,diasFrecuenciaPago/diasAnio)-1);
    }
    public static double calcularVAN(double COK, String frecuenciaPago,double prestamo,List<Double>flujos){

        //Parece que tenemos que recibir la COK como una tasa de descuento anual
        //En base al resultado que se obtenga, tenemos que convertirlo de acuerdo a la frecuencia de pago
        //Por ejemplo, si la COK es anual, pero los flujos son mensuales, entonces la COK se convierte a mensual
        double COKSegunFrecuenciaDePago= calcularCOKDeAcuerdoFrecuenciaPago(COK,frecuenciaPago);
        double COKFormaDecimal= Utilidades.devolverPorcentajeEnFormaDecimal(COKSegunFrecuenciaDePago);

        double sumaFlujos=0;
        double potencia=1;

        //List<Double> flujoParaFormula=flujos;
        //No debe tomar el flujo 0 , por lo que borramos el primer elemento
        //flujoParaFormula.remove(0);

        //Calcular la suma de los todos los flujos
        for(double flujo:flujos){
            double denominador= Math.pow(1+COKFormaDecimal,potencia);
            double operacion= flujo / denominador;
            sumaFlujos+=operacion;
            potencia++;
        }

        //Con la suma total solo queda restarle la inversion inicial
        return sumaFlujos+prestamo;
    }
}
