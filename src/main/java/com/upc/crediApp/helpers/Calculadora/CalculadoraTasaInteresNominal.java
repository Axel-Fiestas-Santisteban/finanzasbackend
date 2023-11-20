package com.upc.crediApp.helpers.Calculadora;

import com.upc.crediApp.helpers.Utilidades.Utilidades;

public class CalculadoraTasaInteresNominal {

    public static double convertirATasaEfectivaDeAcuerdoALaFrecuenciaPago(String plazo, double tasaNominalPorcentaje, String tipoCapitalizacion, String frecuenciaPago){

        //Necesito saber el plazo de la tasa nominal,su porcentaje de valor , su capitalizacion y el valor de la frecuencia de pago en dias
        double tasaNominalDecimal = Utilidades.devolverPorcentajeEnFormaDecimal(tasaNominalPorcentaje);
        int plazoEnDias = CalculadoraPlazoEnDias.devolverPlazoEnDias(plazo);
        int capitalizacionEnDias = CalculadoraPlazoEnDias.devolverPlazoEnDias(tipoCapitalizacion);
        int frecuenciaPagoEnDias = CalculadoraPlazoEnDias.devolverPlazoEnDias(frecuenciaPago);

        double m= (double) plazoEnDias /capitalizacionEnDias;
        double n= (double) frecuenciaPagoEnDias /capitalizacionEnDias;

        double TEM= Math.pow(1+(tasaNominalDecimal/m),n)-1;

        //Por consiguiente la TE recibida es del mismo plazo que la TN //YA NO ES NECESARIO
        //Por ultimo, paso la TE a TE de acuerdo a la frecuencia de pago que se elije //TAMPOCO , CON FORMULA SALE TODO

        //formula: TEP = (1 + (i_nominal/m))^n - 1
        //m =  dias del plazo de la tasa nominal/capitalizacion
        //n = dias a trasladar tasa efectiva(como es mensual, entonces sería 30) / capitalizacion
        return Utilidades.porcentajeDecimalEnFormaPorcentaje(TEM);
    }

    public static double convertirATasaEfectivaMensual(String plazo, double tasaNominalPorcentaje, String tipoCapitalizacion){

        //Necesito saber el plazo de la tasa nominal,su porcentaje de valor y su capitalizacion
        double tasaNominalDecimal = Utilidades.devolverPorcentajeEnFormaDecimal(tasaNominalPorcentaje);
        int plazoEnDias = new CalculadoraPlazoEnDias().devolverPlazoEnDias(plazo);
        int capitalizacionEnDias = new CalculadoraPlazoEnDias().devolverPlazoEnDias(tipoCapitalizacion);

        double m= (double) plazoEnDias /capitalizacionEnDias;
        double n= (double) 30 /capitalizacionEnDias;

        double TEM= Math.pow(1+(tasaNominalDecimal/m),n)-1;

        //Por consiguiente la TE recibida es del mismo plazo que la TN //YA NO ES NECESARIO
        //Por ultimo, paso la TE a mensual //TAMPOCO , CON FORMULA SALE TODO

        //formula: TEP = (1 + (i_nominal/m))^n - 1
        //m =  dias del plazo de la tasa nominal/capitalizacion
        //n = dias a trasladar tasa efectiva(como es mensual, entonces sería 30) / capitalizacion
        return Utilidades.porcentajeDecimalEnFormaPorcentaje(TEM);
    }

    public static double convertirTasaNominal(String tiempo, int tiempoObjetivoEnDias){

        return 0.0;

    }
}
