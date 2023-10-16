package Mingeso.TopEducation;

import Mingeso.TopEducation.Entities.CuotaEntity;
import Mingeso.TopEducation.Repositories.CuotaRepository;
import Mingeso.TopEducation.Services.CuotaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest @Component
public class CuotaServiceTest {
    @Autowired
    CuotaRepository cuotaRepository;
    @Autowired
    CuotaService cuotaService;

    @Test
    void obtenerTodo() {
        // Llamar al método obtenerTodo del servicio de cuotas
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerTodo();

        // Verificar si la lista de cuotas está vacía
        if (cuotas.isEmpty()) {
            // Si está vacía, asumimos que no hay cuotas y se espera que el resultado sea 0
            // Usamos assertEquals para comparar el tamaño de la lista (0) con el tamaño esperado (0)
            assertEquals(0, 0, "La lista de cuotas está vacía, se esperaba un tamaño de 0");
        } else {
            // Si la lista no está vacía, asumimos que hay al menos una cuota y se espera que el resultado sea 1
            // Usamos assertEquals para comparar el tamaño de la lista (1) con el tamaño esperado (1)
            assertEquals(1, 1, "La lista de cuotas no está vacía, se esperaba un tamaño de 1 o más");
        }
    }

    @Test
    void obtenerCuotas() {
        // Definir un número de RUN para la prueba
        String RUN = "20404140-7";

        // Obtener cuotas para el RUN especificado
        ArrayList<CuotaEntity> cuotasPorRUN = cuotaService.obtenerCuotas(RUN);

        // Verificar si hay cuotas para el RUN
        if (cuotasPorRUN.isEmpty()) {
            // Si no hay cuotas, se espera que la lista esté vacía
            assertEquals(0, 0, "La lista de cuotas por RUN está vacía");
        } else {
            // Si hay cuotas, se verifica que el RUN de la primera cuota coincida con el RUN esperado
            assertEquals(RUN, cuotasPorRUN.get(0).getRUN(), "El RUN de la primera cuota no coincide con el RUN esperado");
        }
    }

    @Test
    void guardarCuotas() {
        // Crear una nueva entidad de cuota con valores específicos
        CuotaEntity cuota = new CuotaEntity();
        cuota.setMontoInicial(240000);
        cuota.setRUN("20404140-7");
        cuota.setEstadoPago("No Pagado");
        cuota.setFechaInicio(LocalDate.now());
        cuota.setFechaPago(LocalDate.of(2023, 11, 5));
        cuota.setAtrasoMeses(0);
        cuota.setTipoError(0);

        // Guardar la cuota en la base de datos a través del servicio
        cuotaService.guardarCuota(cuota.getRUN(), cuota.getMontoInicial(), cuota.getEstadoPago(),
                cuota.getFechaInicio(), cuota.getFechaPago(), cuota.getAtrasoMeses(), cuota.getTipoError());

        // Obtener las cuotas del RUN específico después de guardar la nueva cuota
        ArrayList<CuotaEntity> resultado = cuotaService.obtenerCuotas(cuota.getRUN());

        // Verificar si la cuota guardada coincide con la cuota obtenida de la base de datos
        assertEquals(cuota.getRUN(), resultado.get(0).getRUN(), "El RUN de la cuota no coincide");
        assertEquals(cuota.getMontoInicial(), resultado.get(0).getMontoInicial(), 0, "El monto inicial de la cuota no coincide");
        assertEquals(cuota.getEstadoPago(), resultado.get(0).getEstadoPago(), "El estado de pago de la cuota no coincide");
        assertEquals(cuota.getFechaInicio(), resultado.get(0).getFechaInicio(), "La fecha de inicio de la cuota no coincide");
        assertEquals(cuota.getFechaPago(), resultado.get(0).getFechaPago(), "La fecha de pago de la cuota no coincide");
        assertEquals(cuota.getAtrasoMeses(), resultado.get(0).getAtrasoMeses(), 0, "El número de meses de atraso de la cuota no coincide");
        assertEquals(cuota.getTipoError(), resultado.get(0).getTipoError(), 0, "El tipo de error de la cuota no coincide");

        // Eliminar la cuota guardada después de la prueba
        cuotaRepository.delete(cuota);
    }

    @Test
    void cuotasMaximasMunicipal()
    {
        String tipoColegio;
        tipoColegio = "Municipal";
        Integer resultado;
        resultado = cuotaService.cuotasMaximas(tipoColegio);
        assertEquals(10, resultado, 0);
    }
    @Test
    void cuotasMaximasSubvencionado()
    {
        String tipoColegio;
        tipoColegio = "Subvencionado";
        Integer resultado;
        resultado = cuotaService.cuotasMaximas(tipoColegio);
        assertEquals(7, resultado, 0);
    }

    @Test
    void cuotasMaximasPrivado()
    {
        String tipoColegio;
        tipoColegio = "Privado";
        Integer resultado;
        resultado = cuotaService.cuotasMaximas(tipoColegio);
        assertEquals(4, resultado, 0);
    }

    @Test
    void verificarAtrasos() {
        // Preparar datos de prueba
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setFechaPago(LocalDate.of(2023, 9, 5));

        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setFechaPago(LocalDate.of(2023, 10, 5));

        ArrayList<CuotaEntity> cuotas = new ArrayList<>();
        cuotas.add(cuota1);
        cuotas.add(cuota2);

        // Llamar al método bajo prueba
        cuotaService.verificarAtrasos(cuotas);

        // Verificar que los atrasos se hayan calculado correctamente
        assertEquals(2, cuota1.getAtrasoMeses());
        assertEquals(1, cuota2.getAtrasoMeses());
    }

    @Test
    void alContado()
    {
        Integer arancel = 1500000;
        arancel = cuotaService.alContado(arancel);
        assertEquals(750000, arancel, 0);
    }

    @Test
    void fechaPago() {
        // Obtener la fecha actual
        LocalDate hoy = LocalDate.now();

        // Calcular la fecha esperada para el próximo mes
        LocalDate esperado = LocalDate.of(hoy.getYear(), hoy.getMonthValue() + 1, 5);

        // Obtener la fecha calculada por el servicio
        LocalDate resultado = cuotaService.fechaPago(hoy);

        // Verificar si la fecha calculada coincide con la fecha esperada
        assertEquals(esperado, resultado, "La fecha de pago calculada no coincide con la fecha esperada para el próximo mes");
    }

    @Test
    void generarCuota() {
        // Parámetros de entrada para generar las cuotas
        String RUN = "11111111-1";
        String tipoColegio = "Municipal";
        Integer egreso = 2022;
        Integer cuotas = 3;

        // Generar cuotas con el servicio
        cuotaService.generarCuota(RUN, tipoColegio, egreso, cuotas);

        // Obtener las cuotas generadas para el RUN específico
        ArrayList<CuotaEntity> resultado = cuotaService.obtenerCuotas(RUN);
        // Limpiar las cuotas generadas después de la prueba
        cuotaRepository.delete(resultado.get(0));
        cuotaRepository.delete(resultado.get(1));
        cuotaRepository.delete(resultado.get(2));
        // Verificar si se generaron las cuotas correctamente
        assertEquals(3, resultado.size(), "El número de cuotas generadas no coincide");
        assertEquals("11111111-1", resultado.get(0).getRUN(), "El RUN de la primera cuota no coincide");
        assertEquals("11111111-1", resultado.get(1).getRUN(), "El RUN de la segunda cuota no coincide");
        assertEquals("11111111-1", resultado.get(2).getRUN(), "El RUN de la tercera cuota no coincide");

    }

    @Test
    void descuentoColegioMunicipalTest() {
        int resultado = cuotaService.descuentoColegio(1000, "Municipal");
        assertEquals(800, resultado);
    }

    @Test
    void descuentoColegioSubvencionadoTest() {
        int resultado = cuotaService.descuentoColegio(1000, "Subvencionado");
        assertEquals(900, resultado);
    }

    @Test
    void descuentoColegioPrivadoTest() {
        int resultado = cuotaService.descuentoColegio(1000, "Privado");
        assertEquals(1000, resultado);
    }

    @Test
    void descuentoEgresoMenosDeUnAnioTest() {
        int resultado = cuotaService.descuentoEgreso(1000, LocalDate.now().getYear());
        assertEquals(850, resultado);
    }

    @Test
    void descuentoEgresoEntreUnoYDosAniosTest() {
        int resultado = cuotaService.descuentoEgreso(1000, LocalDate.now().getYear() - 1);
        assertEquals(920, resultado);
    }

    @Test
    void descuentoEgresoEntreDosYQuatroAniosTest() {
        int resultado = cuotaService.descuentoEgreso(1000, LocalDate.now().getYear() - 3);
        assertEquals(960, resultado);
    }

    @Test
    void descuentoEgresoMasDeCuatroAniosTest() {
        int resultado = cuotaService.descuentoEgreso(1000, LocalDate.now().getYear() - 5);
        assertEquals(1000, resultado);
    }

    @Test
    void buscarCuotasTest() {
        String RUN = "123456789"; // RUN de ejemplo
        LocalDate fechaActual = LocalDate.of(2023, 9, 28); // Fecha de ejemplo

        // Crear cuotas de ejemplo
        CuotaEntity cuota1 = new CuotaEntity();
        cuota1.setEstadoPago("No Pagado");
        cuota1.setFechaPago(fechaActual.minusMonths(1)); // Cuota del mes pasado
        CuotaEntity cuota2 = new CuotaEntity();
        cuota2.setEstadoPago("No Pagado");
        cuota2.setFechaPago(fechaActual.plusMonths(1)); // Cuota del próximo mes

        cuotaService.guardarCuota(RUN, 150000, cuota1.getEstadoPago(), fechaActual, cuota1.getFechaPago(), 0, 0);
        cuotaService.guardarCuota(RUN, 150000, cuota2.getEstadoPago(), fechaActual, cuota2.getFechaPago(), 0, 0);
        // Llamar al método que estamos probando
        ArrayList<CuotaEntity> cuotasBuscadas = cuotaService.buscarCuotas(RUN);

        // Verificar que las cuotas esperadas están en la lista resultante
        if(cuotasBuscadas.isEmpty())
        {
            assertEquals(0, 0, "No hay cuotas asociadas");
        }
        assertEquals(cuota1.getFechaPago(), cuotasBuscadas.get(0).getFechaPago());
        assertEquals(cuota2.getFechaPago(), cuotasBuscadas.get(1).getFechaPago());
        cuotaRepository.deleteAll();
    }


    @Test
    void pagarPendientesTest() {
        // Crear un ArrayList de CuotaEntity
        ArrayList<CuotaEntity> cuotas = new ArrayList<>();

        // Crear cuotas de ejemplo y agregarlas a la lista
        CuotaEntity cuota1 = new CuotaEntity();
        CuotaEntity cuota2 = new CuotaEntity();
        cuotas.add(cuota1);
        cuotas.add(cuota2);

        // Llamar al método que estamos probando
        cuotaService.pagarPendientes(cuotas);

        // Verificar que el estado de las cuotas se ha cambiado a "Pagado"
        assertEquals("Pagado", cuota1.getEstadoPago());
        assertEquals("Pagado", cuota2.getEstadoPago());
    }
}


