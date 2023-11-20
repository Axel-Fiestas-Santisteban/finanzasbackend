package com.upc.crediApp.helpers.Calculadora;

public class CalculadoraPlazoEnDias {
    public static int devolverPlazoEnDias(String plazo){

        String plazoMayuscula= plazo.toUpperCase();
        switch (plazoMayuscula){
            case "DIARIA":
                return 1;
            case "SEMANAL":
                return 7;
            case "QUINCENAL":
                return 15;
            case "MENSUAL":
                return 30;
            case "BIMESTRAL":
                return 60;
            case "TRIMESTRAL":
                return 90;
            case "CUATRIMESTRAL":
                return 120;
            case "SEMESTRAL":
                return 180;
            case "ANUAL":
                return 360;
            default:
                return 1;
        }
    }
}
