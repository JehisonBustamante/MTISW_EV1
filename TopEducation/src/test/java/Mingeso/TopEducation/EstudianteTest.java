package Mingeso.TopEducation;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Services.EstudianteService;
import org.junit.jupiter.api.Test;

class EstudianteTest {
    EstudianteService estudianteService = new EstudianteService();
    EstudianteEntity estudiante = new EstudianteEntity();

    @Test
    void guardarEstudiante()
    {
        estudiante.setRUN("20.404.140-7");
        estudiante.setApellidos("Bustamante Molina");
        estudiante.setNombres("Jehison Alexis");
        estudiante.setFecha_nacimiento("02-06-2000");
        estudiante.setTipo_colegio("Municipal");
        estudiante.setNombre_colegio("Clara Solovera");
        estudiante.setAnio_egreso(2017);

    }
}
