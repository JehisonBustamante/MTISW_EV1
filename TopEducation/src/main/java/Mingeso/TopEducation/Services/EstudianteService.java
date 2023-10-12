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

    public ArrayList<EstudianteEntity> obtenerEstudiantes()
    {
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }


    public void guardarEstudiante(String RUN, String apellidos, String nombres, LocalDate fechaNacimiento, String tipoColegio, String nombreColegio, Integer anioEgreso) {
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRUN(RUN);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFechaNacimiento(fechaNacimiento);
        estudiante.setTipoColegio(tipoColegio);
        estudiante.setNombreColegio(nombreColegio);
        estudiante.setAnioEgreso(anioEgreso);
        estudianteRepository.save(estudiante);
    }

    public int VerificarEstudiante(String RUN) {
        if (estudianteRepository.findByRUN(RUN) == null) {
            return 0;
        } else {
            return 1;
        }
    }

    public void eliminarEstudiante(EstudianteEntity estudiante)
    {
        estudianteRepository.delete(estudiante);
    }

}
