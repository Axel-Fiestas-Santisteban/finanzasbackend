package com.upc.crediApp.helpers.Calculadora;

import com.upc.crediApp.helpers.Utilidades.Utilidades;

public class CalculadoraSeguroVehicular{

    public static double calculoSeguroVehicularDelVehiculo(double precioVehiculo,double porcentajeSeguroVehicularMensual){
        double porcentajeConvertido= porcentajeSeguroVehicularMensual/100;
        return precioVehiculo*(porcentajeConvertido);
    }

    public static double calcularTasaSeguroVehicularMensual(String tiempo, double porcentaje){

        String tiempoMayuscula = tiempo.toUpperCase();
        double porcentajeConvertido= porcentaje/100;
        double diasPlazo = CalculadoraPlazoEnDias.devolverPlazoEnDias(tiempoMayuscula);

        if(tiempoMayuscula.equals("ANUAL")) {
            diasPlazo = diasPlazo + 5; //que pasa si no sumamos nada (estaba en 5)
        }

        return ((porcentajeConvertido*31)/diasPlazo)*100; //estaba en 31

    }

    public static double calcularTasaSeguroVehicularDadoFrecuenciaPago(String frecuenciaPago, String tiempoSeguroVehicular, double porcentajeSeguro){

        double porcentajeConvertido= Utilidades.devolverPorcentajeEnFormaDecimal(porcentajeSeguro);
        double diasPlazoSeguroVehicular = CalculadoraPlazoEnDias.devolverPlazoEnDias(tiempoSeguroVehicular);
        double diasFrecuenciaPago = CalculadoraPlazoEnDias.devolverPlazoEnDias(frecuenciaPago);

/*        if(tiempoSeguroVehicular.equalsIgnoreCase("ANUAL")) {
            diasPlazoSeguroVehicular = diasPlazoSeguroVehicular + 5; //que pasa si no sumamos nada (estaba en 5)
        }*/

        return Utilidades.porcentajeDecimalEnFormaPorcentaje(((porcentajeConvertido*diasFrecuenciaPago)/diasPlazoSeguroVehicular)); //estaba en 31
    }

}