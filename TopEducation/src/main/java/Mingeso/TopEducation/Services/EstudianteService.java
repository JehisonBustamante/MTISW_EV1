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
}
