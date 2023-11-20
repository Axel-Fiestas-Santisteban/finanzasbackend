package com.upc.crediApp.helpers.Calculadora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculadoraFechas {

    public static String calcularFechaDePago(String fechaInicio, int numeroCuota, String frecuenciaPago) {
        // Formatear la fecha de inicio
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicioParsed = LocalDate.parse(fechaInicio, formatter);

        // Calcular la nueva fecha de pago según la frecuencia de pago
        switch (frecuenciaPago.toUpperCase()) {
            case "DIARIA":
                fechaInicioParsed = fechaInicioParsed.plusDays(numeroCuota);
                break;
            case "SEMANAL":
                fechaInicioParsed = fechaInicioParsed.plusWeeks(numeroCuota);
                break;
            case "QUINCENAL":
                fechaInicioParsed = fechaInicioParsed.plusWeeks(numeroCuota * 2);
                break;
            case "MENSUAL":
                fechaInicioParsed = fechaInicioParsed.plusMonths(numeroCuota);
                break;
            case "BIMESTRAL":
                fechaInicioParsed = fechaInicioParsed.plusMonths(numeroCuota * 2);
                break;
            case "TRIMESTRAL":
                fechaInicioParsed = fechaInicioParsed.plusMonths(numeroCuota * 3);
                break;
            case "CUATRIMESTRAL":
                fechaInicioParsed = fechaInicioParsed.plusMonths(numeroCuota * 4);
                break;
            case "SEMESTRAL":
                fechaInicioParsed = fechaInicioParsed.plusMonths(numeroCuota * 6);
                break;
            case "ANUAL":
                fechaInicioParsed = fechaInicioParsed.plusYears(numeroCuota);
                break;
            default:
                // En caso de una frecuencia desconocida, mantener la fecha original
                break;
        }

        // Formatear la nueva fecha de pago
        String fechaPagoFormateada = fechaInicioParsed.format(formatter);
        return fechaPagoFormateada;
    }
}
