package com.upc.crediApp.helpers.Calculadora;

import com.upc.crediApp.dto.DatosEntradaCronograma;
import com.upc.crediApp.helpers.Clases.ColumnasCronogramaPago;
import com.upc.crediApp.helpers.Clases.VariablesIntermediasCalculoCronograma;
import com.upc.crediApp.helpers.Utilidades.Utilidades;
import com.upc.crediApp.model.Cuota;

import java.util.ArrayList;
import java.util.List;

public class CalculadoraCuota {

    public static String SIN_PLAZO_GRACIA="S";
    public static String PLAZO_GRACIA_TOTAL="T";
    public static String PLAZO_GRACIA_PARCIAL="P";
    public static String PLAZO_GRACIA_TOTAL_Y_PARCIAL="TP";
    public static int CUOTA_CERO=0;
    public static double realizarCalculoCuotaSegunFrecuenciaPago(double saldo, double tasaInteres, double tasaDesgravamen, int tiempoDeFinanciamiento,int tiempoCuotaActual,String estadoPlazoGracia) {

        //Hay 4 casos
        //Caso de calculo sin plazo de gracia
        //Caso de calculo de la cuota con plazo de gracia parcial
        //Caso de calculo de la cuota con plazo de gracia total
        //Caso de que se tiene que calcular la cuota cuando ya se ha sido afectado por algun plazo de gracia (total o parcial)

        //Si es ambos , entonces primera se calcula el plazo de gracia total y luego con el plazo de gracia parcial

        if(estadoPlazoGracia.equalsIgnoreCase(SIN_PLAZO_GRACIA)){
            //SI ES SIN PLAZO DE GRACIA
            //=+(montoPrestamos*(tasaInteres+tasaDesgravamen))/(1-(1+(tasaInteres+tasaDesgravamen))^-tiempoDeFinanciamiento)
            double tasaTotalSegunFrecuenciaPago = (tasaInteres / 100) + (tasaDesgravamen / 100);
            double denominador = 1 - Math.pow((1 + tasaTotalSegunFrecuenciaPago), -(tiempoDeFinanciamiento-(tiempoCuotaActual-1)));
            return (saldo * tasaTotalSegunFrecuenciaPago) / denominador;

        } else if (estadoPlazoGracia.equalsIgnoreCase(PLAZO_GRACIA_PARCIAL)) {
            return calculoInteres(saldo,tasaInteres)+CalculadoraSeguroDesgravamen.calcularSeguroDesgravamenConPrestamo(saldo,tasaDesgravamen);
        } else{
            //SI ES CON PLAZO DE GRACIA TOTAL
            return 0.0;
        }


    }

    public static double calcularNumeroCuotasTotales(int tiempoAnios, String frecuenciaPago) {
        int pagosPorAnio = 0;
        String frecuenciaPagoMayuscula= frecuenciaPago.toUpperCase();

        switch (frecuenciaPagoMayuscula) {
            case "DIARIA":
                pagosPorAnio = 365;
                break;
            case "SEMANAL":
                pagosPorAnio = 52;
                break;
            case "QUINCENAL":
                pagosPorAnio = 26;
                break;
            case "MENSUAL":
                pagosPorAnio = 12;
                break;
            case "BIMESTRAL":
                pagosPorAnio = 6;
                break;
            case "TRIMESTRAL":
                pagosPorAnio = 4;
                break;
            case "CUATRIMESTRAL":
                pagosPorAnio = 3;
                break;
            case "SEMESTRAL":
                pagosPorAnio = 2;
                break;
            case "ANUAL":
                pagosPorAnio = 1;
                break;
            default:
                // Manejar un caso por defecto o lanzar una excepción si la frecuencia no es válida
                break;
        }

        return tiempoAnios * pagosPorAnio;
    }

    public static double calculoInteres(double saldoInicial, double tasaEfectivaSegunFrecuenciaPago) {
        return saldoInicial * (tasaEfectivaSegunFrecuenciaPago / 100);
    }

    public static String determinarEstadoPlazoGracia( DatosEntradaCronograma datosEntradaCronograma){
        String ESTADO_PLAZO_GRACIA;

        if(datosEntradaCronograma.getPlazoDeGracia()==null){
            ESTADO_PLAZO_GRACIA=SIN_PLAZO_GRACIA;
        }else{
            if(datosEntradaCronograma.getPlazoDeGracia().equalsIgnoreCase("TOTAL")){
                ESTADO_PLAZO_GRACIA=PLAZO_GRACIA_TOTAL;
            }else{
                ESTADO_PLAZO_GRACIA=PLAZO_GRACIA_PARCIAL;
            }
        }

        return ESTADO_PLAZO_GRACIA;
    }

    public static double calcularSaldoFinal(ColumnasCronogramaPago columnasCronogramaPago, String ESTADO_PLAZO_GRACIA){

        if(ESTADO_PLAZO_GRACIA.equalsIgnoreCase(PLAZO_GRACIA_TOTAL)){
            return columnasCronogramaPago.saldoInicial+columnasCronogramaPago.interes;
        }else if(ESTADO_PLAZO_GRACIA.equalsIgnoreCase(PLAZO_GRACIA_PARCIAL)){
            return columnasCronogramaPago.saldoInicial;
        }else{
            //SIN PLAZO DE GRACIA
            return columnasCronogramaPago.saldoInicial-columnasCronogramaPago.amortizacion;
        }
    }

    public static List<Cuota> obtenerListaCuotas(DatosEntradaCronograma datosEntradaCronograma) {

        //Instanciar variables que vamos a tener si o si
        VariablesIntermediasCalculoCronograma variablesIntermediasCalculoCronograma = instanciarVariablesIntermedias(datosEntradaCronograma);
        ColumnasCronogramaPago columnasCronogramaPago = instanciarColumnasCronogramaPago(datosEntradaCronograma, variablesIntermediasCalculoCronograma);

        String ESTADO_PLAZO_GRACIA = determinarEstadoPlazoGracia(datosEntradaCronograma);

        //Instanciar variable monto del Prestamo
        double montoPrestamo = variablesIntermediasCalculoCronograma.cuotaFinal+variablesIntermediasCalculoCronograma.montoAFinanciar;

        //A esta variable montoPrestamo es la que debe aparecer en el cronograma, sin embargo el financiamiento
        //se debe ir haciendo con el montoAFinanciar normal

        //Obtenemos el numero de cuotas parciales y totales
        double numeroCuotasPlazoGraciaParcial= variablesIntermediasCalculoCronograma.numeroCuotasPlazoGraciaParcial;
        double numeroCuotasPlazoGraciaTotal= variablesIntermediasCalculoCronograma.numeroCuotasPlazoGraciaTotal;

        double auxNumeroCuotasPlazoGraciaTotal=0;
        double auxNumeroCuotasPlazoGraciaParcial=0;


        //Instanciamos lista de cuotas
        List<Cuota> listaCuotas = new ArrayList<>();

        //Cuotas del prestamo
        for(int cuotaActual=0;cuotaActual<=variablesIntermediasCalculoCronograma.numeroCuotas;cuotaActual++){

            Cuota cuotaNueva = new Cuota();

            if(cuotaActual==CUOTA_CERO){
                cuotaNueva.setSaldoInicial(columnasCronogramaPago.saldoInicial);
                cuotaNueva.setMontoPrestamo(montoPrestamo);
                cuotaNueva.setNumeroDeCuota(0);
                cuotaNueva.setAmortizacion(0);
                cuotaNueva.setInteres(0);
                cuotaNueva.setTipoPlazo("N");
                cuotaNueva.setSeguroDesgravamen(0);
                cuotaNueva.setSeguroVehicular(0);
                cuotaNueva.setPortes(0);
                cuotaNueva.setCostosRegistrales(0);
                cuotaNueva.setCostosNotariales(0);
                cuotaNueva.setCuota(0);
                cuotaNueva.setFechaDePago(datosEntradaCronograma.getFechaInicio());
                cuotaNueva.setSaldoFinal(columnasCronogramaPago.saldoInicial);
                cuotaNueva.setFlujo(montoPrestamo);
            }else{

                if(cuotaActual>numeroCuotasPlazoGraciaParcial+numeroCuotasPlazoGraciaTotal){
                    ESTADO_PLAZO_GRACIA = SIN_PLAZO_GRACIA;
                }

                // SI HAY PLAZO DE GRACIA TOTAL Y ANUAL
                if(datosEntradaCronograma.plazoDeGracia!=null){
                    if(datosEntradaCronograma.plazoDeGracia.equals("AMBOS")){
                        //Primero se calcula el plazo de gracia total
                        if(auxNumeroCuotasPlazoGraciaTotal<numeroCuotasPlazoGraciaTotal) {
                            ESTADO_PLAZO_GRACIA = PLAZO_GRACIA_TOTAL;
                            auxNumeroCuotasPlazoGraciaTotal++;
                        }else if(auxNumeroCuotasPlazoGraciaParcial<numeroCuotasPlazoGraciaParcial){
                            ESTADO_PLAZO_GRACIA = PLAZO_GRACIA_PARCIAL;
                            auxNumeroCuotasPlazoGraciaParcial++;
                        }
                    }
                }

                columnasCronogramaPago.numeroCuota= cuotaActual;
                columnasCronogramaPago.interes= Utilidades.redondear(columnasCronogramaPago.saldoInicial*(variablesIntermediasCalculoCronograma.tasaEfectiva/100),2);
                columnasCronogramaPago.seguroDesgravamen=Utilidades.redondear(CalculadoraSeguroDesgravamen.calcularSeguroDesgravamenConPrestamo(columnasCronogramaPago.saldoInicial,variablesIntermediasCalculoCronograma.tasaDesgravamen),2);
                columnasCronogramaPago.cuota= Utilidades.redondear(
                        realizarCalculoCuotaSegunFrecuenciaPago(
                                columnasCronogramaPago.saldoInicial,
                                variablesIntermediasCalculoCronograma.tasaEfectiva,
                                variablesIntermediasCalculoCronograma.tasaDesgravamen,
                                variablesIntermediasCalculoCronograma.numeroCuotas,
                                cuotaActual,
                                ESTADO_PLAZO_GRACIA)
                        ,2);

                if(ESTADO_PLAZO_GRACIA.equalsIgnoreCase(PLAZO_GRACIA_TOTAL)){
                    columnasCronogramaPago.amortizacion=0;
                }else{
                    columnasCronogramaPago.amortizacion= Utilidades.redondear(columnasCronogramaPago.cuota-columnasCronogramaPago.interes-columnasCronogramaPago.seguroDesgravamen,2);
                }

                //en el saldo Final hay distintos casos, si es con plazo de gracia o sin plazo de gracia
                columnasCronogramaPago.saldoFinal= Utilidades.redondear(
                        calcularSaldoFinal(
                                columnasCronogramaPago,
                                ESTADO_PLAZO_GRACIA),
                        2);


                columnasCronogramaPago.flujo= Utilidades.redondear(columnasCronogramaPago.cuota+columnasCronogramaPago.seguroVehicular+columnasCronogramaPago.portes+columnasCronogramaPago.costosNotariales+columnasCronogramaPago.costosRegistrales,2);
                columnasCronogramaPago.fechaVencimiento= CalculadoraFechas.calcularFechaDePago(datosEntradaCronograma.fechaInicio,cuotaActual, datosEntradaCronograma.frecuenciaPago);
                columnasCronogramaPago.tipoPlazo=ESTADO_PLAZO_GRACIA;

                montoPrestamo-=columnasCronogramaPago.amortizacion;

                //Guardamos el saldo inicial y Actualizamos el valor del saldo inicial para la siguiente cuota
                cuotaNueva.setSaldoInicial(Utilidades.redondear(columnasCronogramaPago.saldoInicial,2));
                cuotaNueva.setMontoPrestamo(Utilidades.redondear(montoPrestamo,2));
                columnasCronogramaPago.saldoInicial=columnasCronogramaPago.saldoFinal;
                cuotaNueva.setNumeroDeCuota(cuotaActual);
                cuotaNueva.setAmortizacion(columnasCronogramaPago.amortizacion);
                cuotaNueva.setInteres(columnasCronogramaPago.interes);
                cuotaNueva.setTipoPlazo(columnasCronogramaPago.tipoPlazo);
                cuotaNueva.setSeguroDesgravamen(columnasCronogramaPago.seguroDesgravamen);
                cuotaNueva.setSeguroVehicular(columnasCronogramaPago.seguroVehicular);
                cuotaNueva.setPortes(datosEntradaCronograma.getPortes());
                cuotaNueva.setCostosRegistrales(datosEntradaCronograma.getCostosRegistrales());
                cuotaNueva.setCostosNotariales(datosEntradaCronograma.getCostosNotariales());
                cuotaNueva.setCuota(columnasCronogramaPago.cuota);
                cuotaNueva.setFechaDePago(columnasCronogramaPago.fechaVencimiento);
                cuotaNueva.setSaldoFinal(columnasCronogramaPago.saldoFinal);
                cuotaNueva.setFlujo(-columnasCronogramaPago.flujo);
            }

            listaCuotas.add(cuotaNueva);
        }


        //Añadimos la ultima cuota
        listaCuotas.add(calculoUltimaCuota(datosEntradaCronograma,variablesIntermediasCalculoCronograma, columnasCronogramaPago));

        return listaCuotas;
    }

    public static Cuota calculoUltimaCuota(DatosEntradaCronograma datosEntradaCronograma,VariablesIntermediasCalculoCronograma variablesIntermediasCalculoCronograma, ColumnasCronogramaPago columnasCronogramaPago) {
        double amortizacion = 0;
        double interes = variablesIntermediasCalculoCronograma.cuotaFinal * (variablesIntermediasCalculoCronograma.tasaEfectiva / 100);
        double valorSeguroDesgravamen = variablesIntermediasCalculoCronograma.cuotaFinal * (variablesIntermediasCalculoCronograma.tasaDesgravamen / 100);

        Cuota ultimaCuota = new Cuota();
        ultimaCuota.setNumeroDeCuota((int) variablesIntermediasCalculoCronograma.numeroCuotas + 1);
        ultimaCuota.setSaldoInicial(Utilidades.redondear(variablesIntermediasCalculoCronograma.cuotaFinal,2));
        ultimaCuota.setAmortizacion(Utilidades.redondear(amortizacion,2));
        ultimaCuota.setInteres(Utilidades.redondear(interes,2));
        ultimaCuota.setSeguroDesgravamen(Utilidades.redondear(valorSeguroDesgravamen,2));
        ultimaCuota.setSeguroVehicular(Utilidades.redondear(columnasCronogramaPago.seguroVehicular,2));
        ultimaCuota.setPortes(columnasCronogramaPago.portes);
        ultimaCuota.setCostosRegistrales(columnasCronogramaPago.costosRegistrales);
        ultimaCuota.setCostosNotariales(columnasCronogramaPago.costosNotariales);
        ultimaCuota.setTipoPlazo(SIN_PLAZO_GRACIA);
        ultimaCuota.setCuota(Utilidades.redondear(variablesIntermediasCalculoCronograma.cuotaFinal + amortizacion + interes + valorSeguroDesgravamen,2));
        ultimaCuota.setFechaDePago(CalculadoraFechas.calcularFechaDePago(variablesIntermediasCalculoCronograma.fechaInicio, (int) variablesIntermediasCalculoCronograma.numeroCuotas + 1, datosEntradaCronograma.frecuenciaPago));
        ultimaCuota.setSaldoFinal(0);
        ultimaCuota.setFlujo(-
                Utilidades.redondear(
                        variablesIntermediasCalculoCronograma.cuotaFinal +
                        columnasCronogramaPago.seguroVehicular +
                        columnasCronogramaPago.portes +
                                interes +
                        valorSeguroDesgravamen +
                        columnasCronogramaPago.costosRegistrales +
                        columnasCronogramaPago.costosNotariales,2)

        );

        return ultimaCuota;
    }

    public static ColumnasCronogramaPago instanciarColumnasCronogramaPago(DatosEntradaCronograma datosEntradaCronograma, VariablesIntermediasCalculoCronograma variablesIntermediasCalculoCronograma){

        //el saldo inicial debe ser cuotón+ saldo prestamo
        //pero el financiamiento se debe hacer en base al monto a financiar

        double saldoInicial= variablesIntermediasCalculoCronograma.montoAFinanciar;
        double amortizacion=0;
        double interes=0;
        double valorSeguroDesgravamen=0;
        double valorSeguroVehicular= Utilidades.redondear(CalculadoraSeguroVehicular.calculoSeguroVehicularDelVehiculo(datosEntradaCronograma.getPrecioVehiculo(), variablesIntermediasCalculoCronograma.tasaSeguroVehicular), 2);
        double saldoFinal= Utilidades.redondear(saldoInicial-amortizacion,2);

        ColumnasCronogramaPago columnasCronogramaPago = ColumnasCronogramaPago.builder()
                .numeroCuota(0)
                .fechaVencimiento(datosEntradaCronograma.getFechaInicio())
                .saldoInicial(Utilidades.redondear(saldoInicial,2))
                .interes(interes)
                .cuota(0)
                .tipoPlazo("N")
                .amortizacion(amortizacion)
                .seguroDesgravamen(valorSeguroDesgravamen)
                .seguroVehicular(valorSeguroVehicular)
                .portes(datosEntradaCronograma.getPortes())
                .costosNotariales(datosEntradaCronograma.getCostosNotariales())
                .costosRegistrales(datosEntradaCronograma.getCostosRegistrales())
                .saldoFinal(Utilidades.redondear(saldoFinal,2))
                .flujo(Utilidades.redondear(saldoFinal,2))
                .build();

        return columnasCronogramaPago;
    }

    public static double calcularTasaInteresEfectivaMensual(DatosEntradaCronograma datosEntradaCronograma){

        if(datosEntradaCronograma.getTipoTasaInteres().equalsIgnoreCase("EFECTIVA")) {
            return CalculadoraTasaInteresEfectiva.convertirEfectivaAEfectivaDeAcuerdoALaFrecuenciaPago(datosEntradaCronograma.getPorcentajeTasaInteres(), datosEntradaCronograma.getPlazoTasaInteres(), datosEntradaCronograma.getFrecuenciaPago());
        }else{
            //Si no es efectiva (osea es nominal), se convierte a efectiva
            //La tasa efectiva nominal debe ser pasada a una tasa efectiva de acuerdo a la frecuencia de pago
            return CalculadoraTasaInteresNominal.convertirATasaEfectivaDeAcuerdoALaFrecuenciaPago(datosEntradaCronograma.getPlazoTasaInteres(), datosEntradaCronograma.getPorcentajeTasaInteres(), datosEntradaCronograma.getCapitalizacion(), datosEntradaCronograma.getFrecuenciaPago());
        }
    }
    public static VariablesIntermediasCalculoCronograma instanciarVariablesIntermedias(DatosEntradaCronograma datosEntradaCronograma){

        double tasaEfectiva= calcularTasaInteresEfectivaMensual(datosEntradaCronograma);

        VariablesIntermediasCalculoCronograma variablesIntermediasCalculoCronograma =
                VariablesIntermediasCalculoCronograma.builder()
                        .porcentajePrestamoAFinanciar(Utilidades.calcularPorcentajePrestamoAFinanciar(datosEntradaCronograma.getPorcentajeCuotaInicial(), datosEntradaCronograma.getPorcentajeCuotaFinal()))
                        .fechaInicio(datosEntradaCronograma.getFechaInicio())
                        .tasaEfectiva(tasaEfectiva)
                        .tasaDesgravamen(CalculadoraSeguroDesgravamen.calcularTasaSeguroConFrecuenciaPago(datosEntradaCronograma.getFrecuenciaPago(), datosEntradaCronograma.getTiempoSeguroDesgravamen(), datosEntradaCronograma.getPorcentajeSeguroDesgravamen()))
                        .tasaSeguroVehicular(CalculadoraSeguroVehicular.calcularTasaSeguroVehicularDadoFrecuenciaPago(datosEntradaCronograma.getFrecuenciaPago(), datosEntradaCronograma.getTiempoSeguroVehicular(), datosEntradaCronograma.getPorcentajeSeguroVehicular()))
                        .montoAFinanciar(Utilidades.calcularMontoAplicandoPorcentaje(datosEntradaCronograma.getPrecioVehiculo(),Utilidades.calcularPorcentajePrestamoAFinanciar(datosEntradaCronograma.getPorcentajeCuotaInicial(), datosEntradaCronograma.getPorcentajeCuotaFinal())))
                        .cuotaInicial(Utilidades.calcularMontoAplicandoPorcentaje(datosEntradaCronograma.getPrecioVehiculo(), datosEntradaCronograma.getPorcentajeCuotaInicial()))
                        .cuotaFinal(Utilidades.calcularMontoAplicandoPorcentaje(datosEntradaCronograma.getPrecioVehiculo(), datosEntradaCronograma.getPorcentajeCuotaFinal()))
                        .numeroCuotas((int) CalculadoraCuota.calcularNumeroCuotasTotales(datosEntradaCronograma.getNumeroAnios(), datosEntradaCronograma.getFrecuenciaPago()))
                        .numeroCuotasPlazoGraciaParcial(datosEntradaCronograma.getTiempoPlazoDeGraciaParcial())
                        .numeroCuotasPlazoGraciaTotal(datosEntradaCronograma.getTiempoPlazoDeGraciaTotal())
                        .build();

        return variablesIntermediasCalculoCronograma;
    }

}
