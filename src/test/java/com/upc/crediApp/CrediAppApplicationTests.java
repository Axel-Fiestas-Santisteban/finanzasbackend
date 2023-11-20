package com.upc.crediApp;

import com.upc.crediApp.helpers.Calculadora.CalculadoraTIR;
import com.upc.crediApp.helpers.Calculadora.CalculadoraTasaInteresNominal;
import com.upc.crediApp.helpers.Calculadora.CalculadoraVAN;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CrediAppApplicationTests {

/*	@Test
	void Test1() {
		double COKAnual=25;
		String frecuenciaPago="mensual";
		String frecuenciaPago2="bimestral";
		double COKMensual= CalculadoraVAN.calcularCOKDeAcuerdoFrecuenciaPago(COKAnual,frecuenciaPago);
		double COKBimestral= CalculadoraVAN.calcularCOKDeAcuerdoFrecuenciaPago(COKAnual,frecuenciaPago2);
		System.out.println(COKMensual);
		System.out.println(COKBimestral);
	}

	@Test
	void TestVAN(){
		double COKAnual=4;
		String frecuenciaPago="anual";
		double inversion=50;
		double flujo1=-5;
		double flujo2=-10;
		double flujo3=-15;
		double flujo4=-20;
		double flujo5=-5;

		double VAN= CalculadoraVAN.calcularVAN(COKAnual,frecuenciaPago,inversion, List.of(flujo1,flujo2,flujo3,flujo4,flujo5));
		System.out.println(VAN);
	}

	@Test
	void TestTIR(){

		double inversion=50;
		double[] flujo = {inversion, -5, -10, -15, -20, -5};
		double TIR= CalculadoraTIR.calcularTIR(flujo);
		System.out.println(TIR);
	}*/

}
