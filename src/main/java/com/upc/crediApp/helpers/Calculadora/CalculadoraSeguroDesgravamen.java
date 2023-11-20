package com.upc.crediApp.helpers.Calculadora;

public class CalculadoraSeguroDesgravamen{

    public static double calcularSeguroDesgravamenConPrestamo(double montoPrestamo, double porcentajeSeguroDesgravamenMensual){
        double porcentajeConvertido= porcentajeSeguroDesgravamenMensual/100;
        return montoPrestamo * porcentajeConvertido;
    }

    public static double calcularTasaSeguroMensual(String tiempo, double porcentaje){

        String tiempoMayuscula = tiempo.toUpperCase();
        double nuevoPorcentaje=porcentaje;

        switch (tiempoMayuscula) {
            case "ANUAL":
                nuevoPorcentaje = porcentaje / 12;
                break;
            case "SEMESTRAL":
                nuevoPorcentaje = porcentaje/ 6;
                break;
            case "TRIMESTRAL":
                nuevoPorcentaje = porcentaje / 3;
                break;
                case "BIMESTRAL":
                nuevoPorcentaje = porcentaje / 2;
                break;
            case "MENSUAL":
                break;
            case "QUINCENAL":
                nuevoPorcentaje = porcentaje * 2;
                break;
            case "SEMANAL":
                nuevoPorcentaje = porcentaje * 4;
                break;
            case "DIARIA":
                nuevoPorcentaje = porcentaje * 30;
                break;
            default:
                nuevoPorcentaje = porcentaje;
                break;
        }

        return nuevoPorcentaje;
    }

    public static double calcularTasaSeguroConFrecuenciaPago(String frecuenciaPago, String PlazoSeguroDesgravamen, double porcentajeSeguroDesgravamen){

        double tasaMensualSeguroDesgravamen = calcularTasaSeguroMensual(PlazoSeguroDesgravamen, porcentajeSeguroDesgravamen);

        switch (frecuenciaPago.toUpperCase()){
            case "ANUAL":
                return tasaMensualSeguroDesgravamen * 12;
            case "SEMESTRAL":
                return tasaMensualSeguroDesgravamen * 6;
            case "TRIMESTRAL":
                return tasaMensualSeguroDesgravamen * 3;
            case "BIMESTRAL":
                return tasaMensualSeguroDesgravamen * 2;
            case "MENSUAL":
                return tasaMensualSeguroDesgravamen;
            case "QUINCENAL":
                return tasaMensualSeguroDesgravamen / 2;
            case "SEMANAL":
                return tasaMensualSeguroDesgravamen / 4;
            case "DIARIA":
                return tasaMensualSeguroDesgravamen / 30;
            default:
                return tasaMensualSeguroDesgravamen;
        }

    }

    //frecuencia de pago : Bimestral-> 2 meses
    //porcentaje seguro desgravamen: 2.3
    //tiempo plazo seguro desgravamen: ANUAL-> 12 meses
    //para hallar el porcentaje que tendrá el seguro de desgravamen en el plazo de la frecuencia de pago se hace lo siguiente:
    // 2.3/12 = 0.19166666666666668
    // 0.19166666666666668 * 2 = 0.38333333333333336

    //otro caso
    //freuencia de pago: SEMANAL-> 1 semana
    //porcentaje seguro desgravamen: 2.3
    //tiempo plazo seguro desgravamen: ANUAL-> 12 meses
    //para hallar el porcentaje que tendrá el seguro de desgravamen en el plazo de la frecuencia de pago se hace lo siguiente:
    // 2.3/12 = 0.19166666666666668 (porcentaje mensual)
    // 0.19166666666666668 /4 =  (porcentaje semanal)
}
