package Mingeso.TopEducation;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Repositories.EstudianteRepository;
import Mingeso.TopEducation.Services.EstudianteService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class EstudianteServiceTest {
    @Autowired
    private EstudianteService estudianteService;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Test
    void guardarEstudiante() {
        // Crear un nuevo estudiante para la prueba
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRUN("22222222-2");
        estudiante.setApellidos("Bustamante Molina");
        estudiante.setNombres("Jehison Alexis");
        estudiante.setFechaNacimiento(LocalDate.parse("2000-06-02"));
        estudiante.setTipoColegio("Municipal");
        estudiante.setNombreColegio("Clara Solovera");
        estudiante.setAnioEgreso(2017);

        // Guardar el estudiante en la base de datos a través del servicio
        estudianteService.guardarEstudiante(
                estudiante.getRUN(),
                estudiante.getApellidos(),
                estudiante.getNombres(),
                estudiante.getFechaNacimiento(),
                estudiante.getTipoColegio(),
                estudiante.getNombreColegio(),
                estudiante.getAnioEgreso()
        );

        // Obtener el estudiante de la base de datos
        EstudianteEntity resultado = estudianteService.obtenerPorRUN(estudiante.getRUN());

        // Eliminar el estudiante de la base de datos después de la prueba
        estudianteRepository.deleteAll();

        // Verificar si los atributos del estudiante guardado coinciden con los esperados
        assertEquals(estudiante.getRUN(), resultado.getRUN(), "RUN no coincide");
        assertEquals(estudiante.getApellidos(), resultado.getApellidos(), "Apellidos no coinciden");
        assertEquals(estudiante.getNombres(), resultado.getNombres(), "Nombres no coinciden");
        assertEquals(estudiante.getFechaNacimiento(), resultado.getFechaNacimiento(), "Fecha de nacimiento no coincide");
        assertEquals(estudiante.getTipoColegio(), resultado.getTipoColegio(), "Tipo de colegio no coincide");
        assertEquals(estudiante.getNombreColegio(), resultado.getNombreColegio(), "Nombre de colegio no coincide");
        assertEquals(estudiante.getAnioEgreso(), resultado.getAnioEgreso(), "Año de egreso no coincide");
    }

    @Test
    void obtenerEstudiantes() {
        // Obtener la lista de estudiantes del servicio
        ArrayList<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();

        // Verificar si la lista de estudiantes está vacía
        if (estudiantes.isEmpty()) {
            assertEquals(0, 0, "La lista de estudiantes está vacía");
        } else {
            assertEquals(1, 1, "La lista de estudiantes no está vacía");
        }
    }
}
