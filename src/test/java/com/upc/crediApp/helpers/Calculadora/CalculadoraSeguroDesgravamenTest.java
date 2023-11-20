package com.upc.crediApp.helpers.Calculadora;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraSeguroDesgravamenTest {

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsAnualThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("ANUAL", 10.0);
        assertEquals(0.8333333333333334, result);
    }

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsSemestralThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("SEMESTRAL", 10.0);
        assertEquals(1.6666666666666667, result);
    }

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsMensualThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("MENSUAL", 10.0);
        assertEquals(10.0, result);
    }

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsQuincenalThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("QUINCENAL", 10.0);
        assertEquals(20.0, result);
    }

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsSemanalThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("SEMANAL", 10.0);
        assertEquals(40.0, result);
    }

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsDiariaThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("DIARIA", 10.0);
        assertEquals(300.0, result);
    }

    @Test
    public void testCalcularTasaSeguroMensualWhenTiempoIsNotPredefinedThenReturnMonthlyRate() {
        double result = CalculadoraSeguroDesgravamen.calcularTasaSeguroMensual("NOT PREDEFINED", 10.0);
        assertEquals(10.0, result);
    }
}