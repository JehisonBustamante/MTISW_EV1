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


    public ArrayList<CuotaEntity> obtenerCuotas(String RUN)
    {
    ArrayList<CuotaEntity> cuotas = cuotaRepository.findAllByRUN(RUN);
        verificarAtrasos(cuotas);
        return cuotas;
    }

    public void guardarCuota(String RUN, Integer montoInicial, String estadoPago, LocalDate fechaInicio, LocalDate fechaPago, Integer atrasoMeses, Integer error) {
        CuotaEntity cuota = new CuotaEntity();
        cuota.setRUN(RUN);
        cuota.setMontoInicial(montoInicial);
        cuota.setEstadoPago(estadoPago);
        cuota.setFechaInicio(fechaInicio);
        cuota.setFechaPago(fechaPago);
        cuota.setAtrasoMeses(atrasoMeses);
        cuota.setTipoError(error);
        cuotaRepository.save(cuota);
    }

    public Integer cuotasMaximas(String tipoColegio) {
        switch (tipoColegio) {
            case "Municipal" -> {
                return 10;
            }
            case "Subvencionado" -> {
                return 7;
            }
            case "Privado" -> {
                return 4;
            }
        }
        return 10;
    }

    public void verificarAtrasos(ArrayList<CuotaEntity> cuotas)
    {
        for (CuotaEntity cuota : cuotas) {
            LocalDate hoy = LocalDate.now();
            int atraso = 0;
            if(hoy.getMonthValue() >= cuota.getFechaPago().getMonthValue())
            {
                for(int j= hoy.getMonthValue(); j>=cuota.getFechaPago().getMonthValue(); j--)
                {
                    if (j == cuota.getFechaPago().getMonthValue() && hoy.getDayOfMonth() > 10) {
                        atraso = atraso + 1;
                    }
                    else if(j>cuota.getFechaPago().getMonthValue())
                    {
                        atraso = atraso + 1;
                    }
                }
            }
            Integer atrasadoFinal = atraso;
            cuota.setAtrasoMeses(atrasadoFinal);
        }

    }


    public LocalDate fechaPago(LocalDate cuota1)
    {
        LocalDate fechaPago = LocalDate.of(cuota1.getYear(), cuota1.getMonth(), cuota1.getDayOfMonth());
        if(fechaPago.getMonthValue() == 12)
        {
            fechaPago = LocalDate.of(fechaPago.getYear() + 1, 1, 5);
        }
        else {
            fechaPago = LocalDate.of(fechaPago.getYear(), fechaPago.getMonthValue() + 1, 5);
        }
        return fechaPago;
    }

    public void generarCuota(String RUN, String tipoColegio, Integer anioEgreso, Integer cantCuotas)
    {
        Integer arancel = 1500000;
        arancel = descuentoColegio(arancel, tipoColegio);
        arancel = descuentoEgreso(arancel, anioEgreso);
        String estado = "No Pagado";
        Integer atrasoMeses = 0;
        LocalDate hoy = LocalDate.now();
        ArrayList<LocalDate> pagos = new ArrayList<>();
        for(int i=0; i<cantCuotas; i++)
        {
            LocalDate pago = fechaPago(hoy);
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
