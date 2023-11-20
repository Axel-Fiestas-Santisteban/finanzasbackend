package com.upc.crediApp.helpers.Utilidades;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utilidades {
    public static double redondear(double numero, int decimales){
        BigDecimal decimal = new BigDecimal(numero);
        return decimal.setScale(decimales, RoundingMode.HALF_UP).doubleValue();
    }

    public static double calcularPorcentajePrestamoAFinanciar(double porcentajeCuotaInicial,double porcentajeCuotaFinal){
        double PORCENTAJE_TOTAL = 100;
        return PORCENTAJE_TOTAL - porcentajeCuotaInicial - porcentajeCuotaFinal;
    }

    public static double devolverPorcentajeEnFormaDecimal(double porcentaje){
        return porcentaje/100;
    }
    public static double porcentajeDecimalEnFormaPorcentaje(double porcentaje){
        return porcentaje*100;
    }

    public static double calcularMontoAplicandoPorcentaje(double monto,double porcentaje){
        return devolverPorcentajeEnFormaDecimal(porcentaje) * monto;
    }
}
