package com.upc.crediApp.helpers.Calculadora;

import com.upc.crediApp.helpers.Utilidades.Utilidades;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculadoraTIRTest {

    @Test
    @DisplayName("Test the method when the input array is empty")
    public void testCalcularTIRWhenInputArrayIsEmptyThenReturnZero() {
        // Arrange
        double[] flujo = new double[0];

        // Act
        double result = CalculadoraTIR.calcularTIR(flujo);

        // Assert
        assertEquals(0.0, result, "The IRR of an empty array should be 0");
    }

    @Test
    @DisplayName("Test the method when the input array contains only one element")
    public void testCalcularTIRWhenInputArrayContainsOneElementThenReturnSameValue() {
        // Arrange
        double[] flujo = new double[]{100.0};

        // Act
        double result = CalculadoraTIR.calcularTIR(flujo);

        // Assert
        assertEquals(100.0, result, "The IRR of a single cash flow should be equal to the cash flow itself");
    }

    @Test
    @DisplayName("Test the method when the input array contains multiple elements")
    public void testCalcularTIRWhenInputArrayContainsMultipleElementsThenReturnApproximateIRR() {
        // Arrange
        double[] flujo = new double[]{-100.0, 60.0, 60.0, 60.0};

        // Act
        double result = CalculadoraTIR.calcularTIR(flujo);

        // Assert
        // The IRR of the cash flows is approximately 43.41%
        assertEquals(43.41, Utilidades.redondear(result, 2), "The IRR should be approximately 43.41%");
    }
}