package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.CuotaEntity;
import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Repositories.CuotaRepository;
import Mingeso.TopEducation.Repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class CuotaService {
    @Autowired
    CuotaRepository cuotaRepository;

    EstudianteRepository estudianteRepository;

    public ArrayList<CuotaEntity> obtenerCuotas()
    {
        return (ArrayList<CuotaEntity>) cuotaRepository.findAll();
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
        if (tipoColegio.equals("Municipal")) {
            return 10;
        } else if (tipoColegio.equals("Subvencionado")) {
            return 7;
        }
        return 4;
    }

}
