package com.upc.crediApp.service.impl;

import com.upc.crediApp.dto.DatosEntradaCronograma;
import com.upc.crediApp.exception.ValidationException;
import com.upc.crediApp.helpers.Calculadora.CalculadoraCuota;
import com.upc.crediApp.helpers.Calculadora.CalculadoraTIR;
import com.upc.crediApp.helpers.Calculadora.CalculadoraVAN;
import com.upc.crediApp.helpers.Utilidades.Utilidades;
import com.upc.crediApp.model.*;
import com.upc.crediApp.repository.*;
import com.upc.crediApp.service.inter.CronogramaService;
import com.upc.crediApp.service.inter.PlanPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CronogramaServiceImpl implements CronogramaService {


    @Autowired
    private CronogramaRepository cronogramaRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TasaInteresRepository tasaInteresRepository;

    @Autowired
    private CuotaRepository cuotaRepository;

    @Autowired
    private MonedaRepository monedaRepository;

    @Autowired
    private PlanPagoRepository planPagoRepository;

    @Override
    public List<Cronograma> getAllCronogramas() {
        return null;
    }

    @Override
    public List<Cuota> getAllCuotasByCronograma(Long id) {
        return null;
    }
    @Override
    public Informacion getInformacionByCronograma(Long id) {
        return null;
    }
    @Override
    public void deleteCronograma(Long id) {
    }

    //TODO: REVISAR ESTO, AHORA YA NO SE GUARDA EL CRONOGRAMA EN EL CUSTOMER, ES EN EL PLAN DE PAGOS
/*
    @Override
    public Cronograma saveCronograma(Long customerId, DatosEntradaCronograma datosEntradaCronograma) {

        validacionCustomer(customerId);
        validacionMoneda(datosEntradaCronograma);

        validarDatosEntrada(datosEntradaCronograma);

        convertirTodosLosCamposAMayuscula(datosEntradaCronograma);

        Cronograma nuevoCronograma= Cronograma.builder().build();

        List<Cuota> listaCuotas= calcularListaCuotasSegunPlazo(datosEntradaCronograma);

        rellenarCuotas(listaCuotas,nuevoCronograma);
        rellenarInformacion(datosEntradaCronograma,nuevoCronograma);
        rellenarVehiculo(datosEntradaCronograma,nuevoCronograma);
        //rellenarCustomer(customerId,nuevoCronograma);
        rellenarIndicadores(listaCuotas,nuevoCronograma);

        //Primero se guarda el cronograma y luego se guardan las cuotas del cronograma
        cronogramaRepository.save(nuevoCronograma)
                .getCuotas()
                .forEach(cuota -> cuotaRepository.save(cuota));

        return nuevoCronograma;
    }
*/

    @Override
    public Cronograma saveCronograma(Long planPagoId, DatosEntradaCronograma datosEntradaCronograma) {

        validacionPlanPago(planPagoId);
        validacionMoneda(datosEntradaCronograma);
        validacionAutoTasado(datosEntradaCronograma);
        validacionTipoCronograma(datosEntradaCronograma);

        validarDatosEntrada(datosEntradaCronograma);
        convertirTodosLosCamposAMayuscula(datosEntradaCronograma);

        Cronograma nuevoCronograma= Cronograma.builder().build();

        List<Cuota> listaCuotas= calcularListaCuotasSegunPlazo(datosEntradaCronograma);

        rellenarCuotas(listaCuotas,nuevoCronograma);
        rellenarInformacion(datosEntradaCronograma,nuevoCronograma);
        rellenarVehiculo(datosEntradaCronograma,nuevoCronograma);
        rellenarIndicadores(listaCuotas,nuevoCronograma);

        rellenarPlanPago(planPagoId,nuevoCronograma);

        //Primero se guarda el cronograma y luego se guardan las cuotas del cronograma
        cronogramaRepository.save(nuevoCronograma)
                .getCuotas()
                .forEach(cuota -> cuotaRepository.save(cuota));

        return nuevoCronograma;

    }

    private void validacionPlanPago(Long planPagoId){
        if(!planPagoRepository.existsById(planPagoId)){
            throw new ValidationException("No existe el plan de pago con el id: "+planPagoId);
        }
    }
    private void rellenarPlanPago(Long idPlanPago,Cronograma cronograma){

        planPagoRepository.findById(idPlanPago).ifPresent(planPago -> {

            if(planPago.getCronograma()==null){
                planPago.setCronograma(new ArrayList<>());
            }
            List<Cronograma> cronogramas = planPago.getCronograma();
            cronogramas.add(cronograma);
            planPago.setCronograma(cronogramas);


            planPagoRepository.save(planPago);
            cronograma.setPlanPago(planPago);

        });
    }


    //TODO: REVISAR ESTE MÉTODO
    @Override
    public List<Cronograma> getAllCronogramasByPlanPagoId(Long planPagoId) {
        if(!planPagoRepository.existsById(planPagoId)){
            throw new ValidationException("No existe el plan de pago con el id: "+planPagoId);
        }
        return cronogramaRepository.findAllByPlanPagoId(planPagoId);
    }

    private String determinarAbreviaturaTasaInteres(DatosEntradaCronograma datosEntradaCronograma){

        //obtengo los datos del tipo de tasa y su plazo
        String tipoTasa=datosEntradaCronograma.getTipoTasaInteres();
        String plazoTasa=datosEntradaCronograma.getPlazoTasaInteres();

        //llamo al repositorio de tasa de interes para obtener la abreviatura
        TasaInteres tasaInteres=tasaInteresRepository.findByTipoAndPlazo(tipoTasa,plazoTasa);

        return tasaInteres.abreviatura;
    }
    private List<Cuota> calcularListaCuotasSegunPlazo(DatosEntradaCronograma datosEntradaCronograma){

        return CalculadoraCuota.obtenerListaCuotas(datosEntradaCronograma);
    }
    private void convertirTodosLosCamposAMayuscula(DatosEntradaCronograma datosEntradaCronograma){

        if(datosEntradaCronograma.getTipoTasaInteres()!=null){
            datosEntradaCronograma.setTipoTasaInteres(datosEntradaCronograma.getTipoTasaInteres().toUpperCase());
        }else {
            throw new ValidationException("No se ingreso el tipo de tasa de interes");
        }

        if(datosEntradaCronograma.getPlazoTasaInteres()!=null){
            datosEntradaCronograma.setPlazoTasaInteres(datosEntradaCronograma.getPlazoTasaInteres().toUpperCase());
        }else {
            throw new ValidationException("No se ingreso el plazo de la tasa de interes");
        }

        if(datosEntradaCronograma.getTiempoSeguroDesgravamen()!=null){
            datosEntradaCronograma.setTiempoSeguroDesgravamen(datosEntradaCronograma.getTiempoSeguroDesgravamen().toUpperCase());
        }else {
            throw new ValidationException("No se ingreso el tiempo del seguro de desgravamen");
        }

        if(datosEntradaCronograma.getTiempoSeguroVehicular()!=null){
            datosEntradaCronograma.setTiempoSeguroVehicular(datosEntradaCronograma.getTiempoSeguroVehicular().toUpperCase());
        }else{
            throw  new ValidationException("No se ingreso el tiempo del seguro vehicular");
        }

        //la capitalizacion si puede ser nula
        if(datosEntradaCronograma.getCapitalizacion()!=null){
            datosEntradaCronograma.setCapitalizacion(datosEntradaCronograma.getCapitalizacion().toUpperCase());
        }else {
            datosEntradaCronograma.setCapitalizacion(null);
        }

        //el plazo de gracia tambien puede ser nulo
        if(datosEntradaCronograma.getPlazoDeGracia()!=null) {
            datosEntradaCronograma.setPlazoDeGracia(datosEntradaCronograma.getPlazoDeGracia().toUpperCase());
        }else {
            datosEntradaCronograma.setPlazoDeGracia(null);
        }

        //la frecuencia de pago debe estar si o si
        if(datosEntradaCronograma.getFrecuenciaPago()!=null) {
            datosEntradaCronograma.setFrecuenciaPago(datosEntradaCronograma.getFrecuenciaPago().toUpperCase());
        }else {
            throw new ValidationException("No se ingreso la frecuencia de pago");
        }

        if(datosEntradaCronograma.getTipoMoneda()!=null){
            datosEntradaCronograma.setTipoMoneda(datosEntradaCronograma.getTipoMoneda().toUpperCase());
        }else{
            throw new ValidationException("No se ingreso el tipo de moneda");
        }

        if(datosEntradaCronograma.getTipoCronograma()!=null){
            datosEntradaCronograma.setTipoCronograma(datosEntradaCronograma.getTipoCronograma().toUpperCase());
        }else{
            throw new ValidationException("No se ingreso el tipo de cronograma");
        }

    }
    private void validarDatosEntrada(DatosEntradaCronograma datosEntradaCronograma){

        if(datosEntradaCronograma.getPlazoDeGracia()!=null){
            validacionesConPlazoGracia(datosEntradaCronograma);
        }
        validacionesCasoTasaNominal(datosEntradaCronograma);
        validacionesCuotaInicial(datosEntradaCronograma);
        validacionesCuotaFinal(datosEntradaCronograma);
    }
    private void rellenarCuotas(List<Cuota> listaCuotas, Cronograma cronograma){
        //Lista externa de cuotas
        List<Cuota> cuotas= new ArrayList<>();
        //Asociar cada cuota al cronograma
        for(Cuota cuota:listaCuotas){
            cuota.setCronograma(cronograma);
            cuotas.add(cuota);
        }
        cronograma.setCuotas(cuotas);
    }

    private double calcularSaldoRestanteMedianteTipoCronograma(DatosEntradaCronograma datosEntradaCronograma, Cronograma cronograma){

        //Primero buscamos la ultima cuota del listado de cuotas del cronograma
        Cuota ultimaCuota = cronograma.getCuotas().get(cronograma.getCuotas().size()-1);
        //DEBE HABER UN TASADO DEL AUTO -> se debe poner manualments


        //Supongamos que mi auto vale 20000 y mi tasado es 8000
        //Si el tasado es menor al cuoton final, entonces la persona debe pagar la diferencia
        // y el cuoton final es de ... 9000
        // Entonces 8000-9000 = -1000 ( La persona debe pagar 1000)


        // Si el tasado es mayor al cuoton final, entonces la persona tiene dinero extra
        // Supongamos que mi auto vale 20000 y mi tasado es 30000
        // y el cuoton final es de ... 9000
        // Entonces 30000-9000 = 21000 ( La persona tiene 21000 extra que le serán devueltos)

        if(datosEntradaCronograma.getTipoCronograma().equalsIgnoreCase("RENOVACION")){
            return datosEntradaCronograma.getValorAutoTasado()-ultimaCuota.getCuota();
            //Posibilidad de hacer el cambio en la ultima cuota ->ultimaCuota.setSaldoFinal(saldoRestante);
        }else if(datosEntradaCronograma.getTipoCronograma().equalsIgnoreCase("DEVOLUCIÓN")) {
            //El valor del auto tasado debería ser 0 en este caso , puesto que el cliente se lleva el auto
            return 0;
        }else{
            return datosEntradaCronograma.getValorAutoTasado()-ultimaCuota.getCuota();
            //Posibilidad de hacer el cambio en la ultima cuota ->ultimaCuota.setSaldoFinal(saldoRestante);
        }
    }
    private void rellenarInformacion(DatosEntradaCronograma datosEntradaCronograma, Cronograma cronograma){


        double porcentajePrestamoAFinanciar= Utilidades.calcularPorcentajePrestamoAFinanciar(datosEntradaCronograma.getPorcentajeCuotaInicial(), datosEntradaCronograma.getPorcentajeCuotaFinal());
        //sacar monto a financiar
        double montoAFinanciar= Utilidades.calcularMontoAplicandoPorcentaje(datosEntradaCronograma.getPrecioVehiculo(),porcentajePrestamoAFinanciar);

        Moneda moneda = monedaRepository.findByNombre(datosEntradaCronograma.getTipoMoneda());
        List<Moneda> monedas = new ArrayList<>();
        monedas.add(moneda);

        double tasaInteresMensual= CalculadoraCuota.calcularTasaInteresEfectivaMensual(datosEntradaCronograma);

        cronograma.setTipoCronograma(datosEntradaCronograma.getTipoCronograma());
        //Creamos un objeto Informacion y lo rellenamos con lo que obtenemos mediante el builder
        Informacion information = Informacion.builder()
                .numeroAnios(datosEntradaCronograma.numeroAnios)
                .porcentajeCuotaInicial(datosEntradaCronograma.porcentajeCuotaInicial)
                .plazoTasaInteres("MENSUAL") //datosEntradaCronograma.plazoTasaInteres
                .porcentajeTasaInteres(tasaInteresMensual)
                .capitalizacion(datosEntradaCronograma.capitalizacion)
                .tiempoPlazoDeGraciaParcial(datosEntradaCronograma.tiempoPlazoDeGraciaParcial)
                .tiempoPlazoDeGraciaTotal(datosEntradaCronograma.tiempoPlazoDeGraciaTotal)
                .porcentajeSeguroDesgravamen(datosEntradaCronograma.porcentajeSeguroDesgravamen)
                .tiempoSeguroDesgravamen(datosEntradaCronograma.tiempoSeguroDesgravamen)
                .porcentajeSeguroVehicular(datosEntradaCronograma.porcentajeSeguroVehicular)
                .tiempoSeguroVehicular(datosEntradaCronograma.tiempoSeguroVehicular)
                .portes(datosEntradaCronograma.portes)
                .costosRegistrales(datosEntradaCronograma.costosRegistrales)
                .costosNotariales(datosEntradaCronograma.costosNotariales)
                .fechaInicio(datosEntradaCronograma.fechaInicio)
                .porcentajePrestamoFinanciar(porcentajePrestamoAFinanciar)
                .porcentajeCuotaFinal(datosEntradaCronograma.porcentajeCuotaFinal)
                .montoCuotaFinal(Utilidades.calcularMontoAplicandoPorcentaje(datosEntradaCronograma.getPrecioVehiculo(),datosEntradaCronograma.getPorcentajeCuotaFinal()))
                .frecuenciaPago(datosEntradaCronograma.frecuenciaPago)
                .montoPrestamoFinanciar(montoAFinanciar)
                .tipoTasaInteres("EFECTIVA") //datosEntradaCronograma.tipoTasaInteres
                .plazoDeGracia(datosEntradaCronograma.plazoDeGracia)
                .abreviaturaTasaInteres(determinarAbreviaturaTasaInteres(datosEntradaCronograma))
                .cokAnual(datosEntradaCronograma.cokAnual)
                .monedas(monedas)
                .saldoRestante(calcularSaldoRestanteMedianteTipoCronograma(datosEntradaCronograma,cronograma))
                .build();
        //Se actualiza el cronograma
        cronograma.setInformacion(information);

    }
    private void rellenarVehiculo(DatosEntradaCronograma datosEntradaCronograma, Cronograma cronograma){


        Vehiculo vehiculo = Vehiculo.builder()
                        .precio(datosEntradaCronograma.getPrecioVehiculo())
                        .marca(datosEntradaCronograma.getMarcaVehiculo())
                        .modelo(datosEntradaCronograma.getModeloVehiculo())
                        .build();
        //Se actualiza el cronograma
        cronograma.setVehiculo(vehiculo);
    }

    //TODO: YA NO RELLENAMOS CUSTOMER, AHORA SE RELLENA EL PLAN DE PAGOS
/*
    public void rellenarCustomer(Long idCustomer, Cronograma cronograma){
        //Se actualiza el customer
        //Encontramos al customer por su id
        customerRepository.findById(idCustomer).ifPresent(customer -> {
            //Se agrega el cronograma al customer
            cronograma.setCustomer(customer);
            //customer.getCronograma().add(cronograma);
            //Se actualiza el customer
            //customerRepository.save(customer);
        });
    }
*/
    public void rellenarIndicadores(List<Cuota> listaCuotas,Cronograma cronograma){

        double VAN=0;
        double TIR=0;
        double prestamo=0;
        List<Double> flujoCaja = new ArrayList<>();
        //Calculo de la VAN
        // Si el numero de cuota es 0 entonces se toma el valor del primer flujo como prestamo/inversion
        for(int i=0;i<listaCuotas.size();i++){
            if(i==0){
                prestamo=listaCuotas.get(i).getFlujo();
            }else{
                flujoCaja.add(listaCuotas.get(i).getFlujo());
            }
        }

        VAN= CalculadoraVAN.calcularVAN(
                cronograma.getInformacion().getCokAnual(),
                cronograma.getInformacion().getFrecuenciaPago(),
                prestamo,
                flujoCaja
        );

        //Calculo de la TIR, para ello incluimos el prestamo en el flujo de caja
        flujoCaja.add(0,prestamo);

        // Convertir List<Double> a double[]
        double[] arrayFlujoCaja = flujoCaja.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
        TIR= CalculadoraTIR.calcularTIR(arrayFlujoCaja);

        //Creamos un objeto Indicadores y lo rellenamos con lo que obtenemos mediante el builder
        Indicadores indicadores = Indicadores.builder()
                .VAN(Utilidades.redondear(VAN,2))
                .TIR(Utilidades.redondear(TIR,2))
                .build();

        //Se actualiza el cronograma
        cronograma.setIndicadores(indicadores);

    }
    private void validacionesConPlazoGracia(DatosEntradaCronograma datosEntradaCronograma){

        double cuotasTotales=CalculadoraCuota.calcularNumeroCuotasTotales(datosEntradaCronograma.getNumeroAnios(), datosEntradaCronograma.getFrecuenciaPago());

        double mitadCuotasTotales= cuotasTotales/2;

        if(datosEntradaCronograma.getPlazoDeGracia().isEmpty()){
            //Si no se ingresa el plazo de gracia, se devuelve un error
            throw new ValidationException("No se ingreso el plazo de gracia");
        }
        if(datosEntradaCronograma.getPlazoDeGracia().equalsIgnoreCase("PARCIAL")){

            if(datosEntradaCronograma.getTiempoPlazoDeGraciaParcial()==null || datosEntradaCronograma.tiempoPlazoDeGraciaParcial==0){
                //Si no se ingresa el tiempo del plazo de gracia, se devuelve un error
                throw new ValidationException("No se ingreso el tiempo del plazo de gracia parcial o es 0");
            }
        }

        if(datosEntradaCronograma.getPlazoDeGracia().equalsIgnoreCase("TOTAL")){
            if(datosEntradaCronograma.getTiempoPlazoDeGraciaTotal()==null || datosEntradaCronograma.tiempoPlazoDeGraciaTotal==0){
                //Si no se ingresa el tiempo del plazo de gracia, se devuelve un error
                throw new ValidationException("No se ingreso el tiempo del plazo de gracia total o es 0");
            }
        }

        if(datosEntradaCronograma.getTiempoPlazoDeGraciaParcial()+ datosEntradaCronograma.getTiempoPlazoDeGraciaTotal()>=cuotasTotales){
            //Si el plazo de gracia es mayor a las cuotas , se devuelve un error
            throw new ValidationException("" +
                    "El plazo de gracia entre parcial y total no puede ser mayor o igual al total de cuotas financiadas" +
                    ". Cuotas totales: "+(int)cuotasTotales+" , frecuencia de pago elegida: "+ datosEntradaCronograma.getFrecuenciaPago().toUpperCase()+
            ", Suma del plazo de gracia parcial y total: "+(datosEntradaCronograma.getTiempoPlazoDeGraciaParcial()+ datosEntradaCronograma.getTiempoPlazoDeGraciaTotal()));
        }

    }
    private void validacionesCasoTasaNominal(DatosEntradaCronograma datosEntradaCronograma){

        if(datosEntradaCronograma.getTipoTasaInteres().equalsIgnoreCase("NOMINAL")){
            if(datosEntradaCronograma.getPlazoTasaInteres()==null || datosEntradaCronograma.getPlazoTasaInteres().isEmpty()){
                //Si no se ingresa el plazo de la tasa nominal, se devuelve un error
                throw new ValidationException("No se ingreso el plazo de la tasa nominal");
            }

            if(datosEntradaCronograma.getCapitalizacion()==null || datosEntradaCronograma.getCapitalizacion().isEmpty()) {
                //Si no se ingresa la capitalizacion de la tasa nominal, se devuelve un error
                throw new ValidationException("No se ingreso la capitalizacion de la tasa nominal");
            }

        }
    }

    private void validacionesCuotaInicial(DatosEntradaCronograma datosEntradaCronograma){

        int porcentajeMinimoCuotaInicial = 20;
        int porcentajeMaximoCuotaInicial = 30;

        if(datosEntradaCronograma.getPorcentajeCuotaInicial()<porcentajeMinimoCuotaInicial || datosEntradaCronograma.getPorcentajeCuotaInicial()>porcentajeMaximoCuotaInicial){
            //Si no se ingresa la cuota inicial, se devuelve un error
            throw new ValidationException("El porcentaje de la cuota inicial debe ser como mínimo "+porcentajeMinimoCuotaInicial+"% y como máximo "+porcentajeMaximoCuotaInicial+"%");
        }
    }

    private void validacionesCuotaFinal(DatosEntradaCronograma datosEntradaCronograma){

        int porcentajeMinimoCuotaFinal = 40;
        int porcentajeMaximoCuotaFinal = 50;

        if(datosEntradaCronograma.getPorcentajeCuotaFinal()<porcentajeMinimoCuotaFinal || datosEntradaCronograma.getPorcentajeCuotaFinal()>porcentajeMaximoCuotaFinal){
            //Si no se ingresa la cuota inicial, se devuelve un error
            throw new ValidationException("El porcentaje de la cuota final debe ser como mínimo "+porcentajeMinimoCuotaFinal+"% y como máximo "+porcentajeMaximoCuotaFinal+"%");
        }

    }

    private void validacionCustomer(Long idCustomer){
        if(!customerRepository.existsById(idCustomer)){
            throw new ValidationException("No existe el customer con el id: "+idCustomer);
        }
    }

    private void validacionTipoCronograma(DatosEntradaCronograma datosEntradaCronograma){

        List<String> tiposCronograma = new ArrayList<>();
        tiposCronograma.add("RENOVACION");
        tiposCronograma.add("DEVOLUCION");
        tiposCronograma.add("COMPRARLO");

        if(datosEntradaCronograma.getTipoCronograma().isEmpty()){
            throw new ValidationException("No se ingreso el tipo de cronograma");
        }

        if(!tiposCronograma.contains(datosEntradaCronograma.getTipoCronograma().toUpperCase())){
            throw new ValidationException("No se permite otro tipo que no sean RENOVACION, DEVOLUCION O COMPRARLO, No existe el tipo: "+datosEntradaCronograma.getTipoCronograma());
        }


    }

    private void validacionAutoTasado(DatosEntradaCronograma datosEntradaCronograma){
            if(datosEntradaCronograma.getValorAutoTasado()>datosEntradaCronograma.getPrecioVehiculo()){
                throw new ValidationException("El valor del auto tasado no puede ser mayor al precio del vehiculo");
            }
    }

    private void validacionMoneda(DatosEntradaCronograma datosEntradaCronograma){

        String tipoMoneda=datosEntradaCronograma.getTipoMoneda();
        if(!tipoMoneda.equalsIgnoreCase("SOLES") && !tipoMoneda.equalsIgnoreCase("DOLARES")){
            throw new ValidationException("No se permite otro tipo que no sean SOLES O DOLARES, No existe la moneda: "+tipoMoneda);
        }
    }


}
