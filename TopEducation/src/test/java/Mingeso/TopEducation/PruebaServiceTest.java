package Mingeso.TopEducation;

import Mingeso.TopEducation.Entities.PruebaEntity;
import Mingeso.TopEducation.Repositories.PruebaRepository;
import Mingeso.TopEducation.Services.PruebaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PruebaServiceTest {

    @Autowired
    private PruebaService pruebaService;

    @Autowired
    private PruebaRepository pruebaRepository;

    @Test
    void obtenerDatosTest() {
        // Crear algunas entidades de prueba y guardarlas en la base de datos
        PruebaEntity prueba1 = new PruebaEntity(1, "20404140-7", 980, LocalDate.now());
        PruebaEntity prueba2 = new PruebaEntity(2, "20404140-7", 900, LocalDate.now());
        pruebaRepository.save(prueba1);
        pruebaRepository.save(prueba2);

        // Llamar al método que estamos probando
        ArrayList<PruebaEntity> resultado = pruebaService.obtenerDatos();
        // Limpiar las pruebas después de la ejecución
        pruebaRepository.delete(prueba1);
        pruebaRepository.delete(prueba2);
        // Verificar que la lista contenga las entidades de prueba guardadas en la base de datos
        assertEquals(2, resultado.size());

    }

    @Test
    void guardarDatosTest() {
        // Crear una instancia de PruebaEntity para la prueba
        PruebaEntity prueba = new PruebaEntity(1,"20404140-7", 990, LocalDate.now());

        // Llamar al método que estamos probando
        pruebaService.guardarDatos(prueba);

        // Obtener la entidad desde el repository y verificar que los datos sean los esperados
        ArrayList<PruebaEntity> pruebaGuardada = pruebaRepository.findAllByRUN(prueba.getRUN());
        pruebaRepository.delete(prueba);
        assertEquals(prueba.getRUN(), pruebaGuardada.get(0).getRUN());

    }

    @Test
    void guardarDatosBDIntegrationTest() {
        // Datos de prueba
        String RUN = "123456789";
        String puntaje = "90";
        String fechaPruebaString = "28-09-2023";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fechaPrueba = LocalDate.parse(fechaPruebaString, formatter);

        // Llamar al método que estamos probando
        pruebaService.guardarDatosBD(RUN, puntaje, fechaPruebaString);

        // Obtener la entidad de la base de datos
        PruebaEntity pruebaGuardada = pruebaRepository.findByRUNAndFechaPrueba(RUN, fechaPrueba);

        // Verificar que los datos guardados sean los esperados
        assertEquals(RUN, pruebaGuardada.getRUN());
        assertEquals(Integer.parseInt(puntaje), pruebaGuardada.getPuntaje());
        assertEquals(fechaPrueba, pruebaGuardada.getFechaPrueba());

        //Limpieza de datos
        pruebaRepository.delete(pruebaGuardada);
    }

}
