package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.CuotaEntity;
import Mingeso.TopEducation.Repositories.CuotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;


    /**
     * Busca y devuelve todas las cuotas registradas en la base de datos.
     */
    public ArrayList<CuotaEntity> obtenerTodo()
    {
        return  cuotaRepository.findAll();
    }

    /**
     * Busca y devuelve una cuota en función de el rut asociado.
     *
     * @param RUN             RUT del estudiante.
     */
    public ArrayList<CuotaEntity> obtenerCuotas(String RUN)
    {
    ArrayList<CuotaEntity> cuotas = cuotaRepository.findAllByRUN(RUN);
        verificarAtrasos(cuotas);
        return cuotas;
    }

    /**
     * Guarda los detalles de una cuota en la base de datos.
     *
     * @param RUN          RUT del estudiante asociado a la cuota.
     * @param montoInicial Monto inicial de la cuota.
     * @param estadoPago   Estado de pago de la cuota (Pagado, No Pagado).
     * @param fechaInicio  Fecha de inicio de la cuota en formato LocalDate.
     * @param fechaPago    Fecha límite para el pago de la cuota en formato LocalDate.
     * @param atrasoMeses  Número de meses de atraso en el pago de la cuota.
     * @param error        Tipo de error asociado a la cuota (si lo hay, 0 si es que no hay).
     */
    public void guardarCuota(String RUN, Integer montoInicial, String estadoPago,
                             LocalDate fechaInicio, LocalDate fechaPago,
                             Integer atrasoMeses, Integer error) {
        // Crea una nueva instancia de CuotaEntity.
        CuotaEntity cuota = new CuotaEntity();

        // Asigna los valores a las propiedades de la cuota.
        cuota.setRUN(RUN);
        cuota.setMontoInicial(montoInicial);
        cuota.setEstadoPago(estadoPago);
        cuota.setFechaInicio(fechaInicio);
        cuota.setFechaPago(fechaPago);
        cuota.setAtrasoMeses(atrasoMeses);
        cuota.setTipoError(error);

        // Guarda la cuota en la base de datos.
        cuotaRepository.save(cuota);
    }

    /**
     * Determina el número máximo de cuotas permitidas según el tipo de colegio del estudiante.
     *
     * @param tipoColegio Tipo de colegio del estudiante (Municipal, Subvencionado, Privado).
     * @return Número máximo de cuotas permitidas para el tipo de colegio especificado.
     */
    public Integer cuotasMaximas(String tipoColegio) {
        switch (tipoColegio) {
            case "Municipal":
                return 10;
            case "Subvencionado":
                return 7;
            case "Privado":
                return 4;
            default:
                return 10; // Valor predeterminado si el tipo de colegio no es reconocido.
        }
    }


    /**
     * Verifica y asigna la cantidad de meses de atraso para cada cuota en función de la fecha actual.
     *
     * @param cuotas Lista de cuotas que se deben verificar.
     */
    public void verificarAtrasos(ArrayList<CuotaEntity> cuotas) {
        LocalDate hoy = LocalDate.now();
        // Itera a través de las cuotas para verificar los atrasos
        for (CuotaEntity cuota : cuotas) {
            int atraso = 0;
            // Comprueba si el mes de pago ya ha pasado o es el mes actual
            if (hoy.getMonthValue() >= cuota.getFechaPago().getMonthValue()) {
                for (int j = cuota.getFechaPago().getMonthValue(); j <= hoy.getMonthValue(); j++) {
                    // Si es el mes de pago y el día actual es posterior al día de pago, se cuenta como atraso
                    if (j == cuota.getFechaPago().getMonthValue() && hoy.getDayOfMonth() > 10) {
                        atraso++;
                    }
                    // Si el mes actual es posterior al mes de pago, se cuenta como atraso
                    else if (j > cuota.getFechaPago().getMonthValue()) {
                        atraso++;
                    }
                }
            }
            // Asigna el número de meses de atraso a la cuota
            cuota.setAtrasoMeses(atraso);
        }
    }


    /**
     * Calcula el monto del arancel para un pago al contado.
     *
     * @param arancel Monto total del arancel.
     * @return Monto del arancel dividido por 2.
     */
    public Integer alContado(Integer arancel) {
        return arancel / 2;
    }

    /**
     * Calcula la fecha del próximo pago basándose en la fecha de la cuota actual.
     *
     * @param cuota1 Fecha de la cuota actual.
     * @return Fecha del próximo pago (mes siguiente, día 5).
     */
    public LocalDate fechaPago(LocalDate cuota1) {
        // Obtiene el año, mes y día de la cuota actual.
        int year = cuota1.getYear();
        int month = cuota1.getMonthValue();
        int day = cuota1.getDayOfMonth();

        // Si la cuota está en diciembre, el próximo pago será en enero del siguiente año.
        // De lo contrario, el próximo pago será en el siguiente mes del mismo año.
        if (month == 12) {
            year++;  // Siguiente año.
            month = 1;  // Enero.
        } else {
            month++;  // Siguiente mes.
        }

        // La fecha del próximo pago será el día 5 del mes calculado.
        LocalDate fechaPago = LocalDate.of(year, month, 5);
        return fechaPago;
    }


    public void generarCuota(String RUN, String tipoColegio, Integer anioEgreso, Integer cantCuotas)
    {
        Integer arancel = 1500000;
        if(cantCuotas==1)
        {
            arancel = alContado(arancel);
        }
        else if(cantCuotas>1)
        {
            arancel = descuentoColegio(arancel, tipoColegio);
            arancel = descuentoEgreso(arancel, anioEgreso);
        }
        String estado = "No Pagado";
        Integer atrasoMeses = 0;
        LocalDate hoy = LocalDate.now();
        ArrayList<LocalDate> pagos = new ArrayList<>();
        for(int i=0; i<cantCuotas; i++)
        {
            LocalDate pago = fechaPago(hoy);
            if(cantCuotas==1)
            {
                pago = hoy;
                pagos.add(pago);
                break;
            }
            hoy = pago;
            pagos.add(pago);
        }
        hoy = LocalDate.now();
        Integer error = 0;
        Integer valorCuota = arancel/cantCuotas;
        for(int i=0; i<cantCuotas; i++)
        {
            LocalDate siguiente = pagos.get(i);
            guardarCuota(RUN, valorCuota, estado, hoy, siguiente, atrasoMeses, error);
        }

    }

    public Integer descuentoColegio(Integer inicial, String tipoColegio)
    {
        switch (tipoColegio) {
            case "Municipal" -> {
                inicial = inicial - (inicial / 5);
                return inicial;
            }
            case "Subvencionado" -> {
                inicial = inicial - (inicial / 10);
                return inicial;
            }
            case "Privado" -> {
                return inicial;
            }
        }
        return inicial;
    }

    public Integer descuentoEgreso(Integer inicial, Integer egreso)
    {
        LocalDate ahora = LocalDate.now();
        Integer esteAnio = ahora.getYear();
        if((esteAnio-egreso) == 0)
        {
            inicial = inicial - (int) Math.round(inicial*0.15);
            return inicial;
        }
        else if((esteAnio-egreso) < 3 && (esteAnio-egreso) > 0)
        {
            inicial = inicial - (int) Math.round(inicial*0.08);
            return inicial;
        }
        else if((esteAnio-egreso) < 5 && (esteAnio-egreso) > 2)
        {
            inicial = inicial - (int) Math.round(inicial*0.04);
            return inicial;
        }
        return inicial;
    }

    public ArrayList<CuotaEntity> buscarCuotas(String RUN)
    {
        ArrayList<CuotaEntity> cuotaEntities = cuotaRepository.findAllByRUN(RUN);
        ArrayList<CuotaEntity> cuotasBuscadas = new ArrayList<>();
        LocalDate hoy = LocalDate.now();
        for (CuotaEntity cuotaEntity : cuotaEntities) {
            if (cuotaEntity.getEstadoPago().equals("No Pagado") && hoy.getYear() >= cuotaEntity.getFechaPago().getYear()) {
                if(hoy.getYear() == cuotaEntity.getFechaPago().getYear()) {
                    if(hoy.getMonthValue() >= cuotaEntity.getFechaPago().getMonthValue()) {
                        if (hoy.getMonthValue() == cuotaEntity.getFechaPago().getMonthValue()) {
                            if (hoy.getDayOfMonth() >= hoy.getDayOfMonth()) {
                                cuotasBuscadas.add(cuotaEntity);
                            }
                        } else {
                            cuotasBuscadas.add(cuotaEntity);
                        }
                    }
                }
                else{
                    cuotasBuscadas.add(cuotaEntity);
                }
            }
        }
        verificarAtrasos(cuotasBuscadas);
        return cuotasBuscadas;
    }
    public void pagarPendientes(ArrayList<CuotaEntity> cuotas)
    {
        for (CuotaEntity cuota : cuotas) {
            cuota.setEstadoPago("Pagado");
            cuotaRepository.save(cuota);
        }
    }

}
