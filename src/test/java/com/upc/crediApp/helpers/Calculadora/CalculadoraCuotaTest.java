package com.upc.crediApp.helpers.Calculadora;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraCuotaTest {

    @Test
    public void testCalcularNumeroCuotasTotalesWithValidInputs() {
        // Arrange
        int tiempoAnios = 2;
        String frecuenciaPago = "MENSUAL";

        // Act
        double result = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, frecuenciaPago);

        // Assert
        assertEquals(24, result, "The number of total installments should be 24");
    }

    @Test
    public void testCalcularNumeroCuotasTotalesWithInvalidFrequency() {
        // Arrange
        int tiempoAnios = 2;
        String frecuenciaPago = "INVALID";

        // Act
        double result = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, frecuenciaPago);

        // Assert
        assertEquals(0, result, "The number of total installments should be 0");
    }

    @Test
    public void testCalcularNumeroCuotasTotalesWithNegativeYears() {
        // Arrange
        int tiempoAnios = -2;
        String frecuenciaPago = "MENSUAL";

        // Act
        double result = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, frecuenciaPago);

        // Assert
        assertEquals(0, result, "The number of total installments should be 0");
    }

    @Test
    public void testCalcularNumeroCuotasTotalesWithDifferentFrequencies() {
        // Arrange
        int tiempoAnios = 2;

        // Act
        double resultDiaria = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, "DIARIA");
        double resultSemanal = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, "SEMANAL");
        double resultQuincenal = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, "QUINCENAL");
        double resultMensual = CalculadoraCuota.calcularNumeroCuotasTotales(tiempoAnios, "MENSUAL");

        // Assert
        assertEquals(730, resultDiaria);
        assertEquals(104, resultSemanal);
        assertEquals(52, resultQuincenal);
        assertEquals(24, resultMensual);
    }
}