package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class EstudianteService {
    @Autowired
    EstudianteRepository estudianteRepository;

    public ArrayList<EstudianteEntity> obtenerEstudiantes()
    {
        return (ArrayList<EstudianteEntity>) estudianteRepository.findAll();
    }


    public void guardarEstudiante(String RUN, String apellidos, String nombres, String fechaNacimiento, String tipoColegio, String nombreColegio, Integer anioEgreso) {
        EstudianteEntity estudiante = new EstudianteEntity();
        estudiante.setRUN(RUN);
        estudiante.setApellidos(apellidos);
        estudiante.setNombres(nombres);
        estudiante.setFecha_nacimiento(fechaNacimiento);
        estudiante.setTipo_colegio(tipoColegio);
        estudiante.setNombre_colegio(nombreColegio);
        estudiante.setAnio_egreso(anioEgreso);
        estudianteRepository.save(estudiante);
    }

    public void eliminarEstudiante(EstudianteEntity estudiante)
    {
        estudianteRepository.delete(estudiante);
    }

}
