package com.upc.crediApp.helpers.Calculadora;

import com.upc.crediApp.helpers.Calculadora.CalculadoraPlazoEnDias;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class CalculadoraTasaInteresEfectiva{

    public static double convertirNominal(int tiempoObjetivoEnDias){
        return 0.0;
    }

    public static double convertirEfectivaAEfectivaDeAcuerdoALaFrecuenciaPago(double porcentajeTasaEfectivaConocida, String plazoTasaConocida, String frecuenciaPago){

        //Calculamos la frecuencia de pago en dias
        double frecuenciaPagoDias= CalculadoraPlazoEnDias.devolverPlazoEnDias(frecuenciaPago);
        double diasPlazoTasaConocida= CalculadoraPlazoEnDias.devolverPlazoEnDias(plazoTasaConocida);

        double tasaPorcentuada= porcentajeTasaEfectivaConocida/100;

        return (Math.pow(1 + tasaPorcentuada, (double) frecuenciaPagoDias / diasPlazoTasaConocida) - 1)*100;
    }
    public static double convertirEfectivaAEfectivaMensual(double tasaEfectiva, String plazo){

        String plazoMayuscula= plazo.toUpperCase();
        double tasaPorcentuada= tasaEfectiva/100;

        return switch (plazoMayuscula) {
            case "ANUAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 360) - 1)*100; // ESTABA EN 365, QUE PASA SI LO CAMBIAMOS A 360
            case "SEMESTRAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 180) - 1)*100;
            case "TRIMESTRAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 90) - 1)*100;
            case "BIMESTRAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 60) - 1)*100;
            case "MENSUAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 30) - 1)*100;
            case "QUINCENAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 15) - 1)*100;
            case "SEMANAL" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 7) - 1)*100;
            case "DIARIA" -> (Math.pow(1 + tasaPorcentuada, (double) 30 / 1) - 1)*100;
            default -> 0.0;
        };
    }
}
