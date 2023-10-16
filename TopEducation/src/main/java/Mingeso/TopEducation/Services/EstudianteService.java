package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class EstudianteService {

    @Autowired
    EstudianteRepository estudianteRepository;

    /**
     * Busca y devuelve a todos los estudiantes guardados en la base de datos.
     */
    public ArrayList<EstudianteEntity> obtenerEstudiantes() {
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }

    /**
     * Busca y devuelve un estudiante en función de su rut.
     *
     * @param RUN             RUT del estudiante.
     */
    public EstudianteEntity obtenerPorRUN(String RUN)
    {
        return estudianteRepository.findByRUN(RUN);
    }

    /**
     * Guarda un nuevo estudiante en la base de datos.
     *
     * @param RUN             RUT del estudiante.
     * @param apellidos       Apellidos del estudiante.
     * @param nombres         Nombres del estudiante.
     * @param fechaNacimiento Fecha de nacimiento del estudiante en formato LocalDate.
     * @param tipoColegio     Tipo de colegio al que asiste el estudiante (Municipal, Subvencionado o Privado).
     * @param nombreColegio   Nombre del colegio al que asiste el estudiante.
     * @param anioEgreso      Año en el que el estudiante egresó de la escuela.
     */
    public void guardarEstudiante(String RUN, String apellidos, String nombres,
                                  LocalDate fechaNacimiento, String tipoColegio,
                                  String nombreColegio, Integer anioEgreso) {
        // Crea una nueva instancia de EstudianteEntity.
        EstudianteEntity estudiante = new EstudianteEntity();

        // Asigna los valores a las propiedades del estudiante.
        estudiante.setRUN(RUN);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFechaNacimiento(fechaNacimiento);
        estudiante.setTipoColegio(tipoColegio);
        estudiante.setNombreColegio(nombreColegio);
        estudiante.setAnioEgreso(anioEgreso);

        // Guarda el estudiante en la base de datos.
        estudianteRepository.save(estudiante);
    }
}
