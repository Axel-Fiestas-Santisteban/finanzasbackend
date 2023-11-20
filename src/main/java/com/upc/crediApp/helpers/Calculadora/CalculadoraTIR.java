package com.upc.crediApp.helpers.Calculadora;


import com.upc.crediApp.helpers.Utilidades.Utilidades;
import org.apache.poi.ss.formula.functions.Irr;

import java.util.Arrays;

public class CalculadoraTIR {

    public static double calcularTIR(double[] flujo){
        return Utilidades.porcentajeDecimalEnFormaPorcentaje(Irr.irr(flujo));
    }
}
