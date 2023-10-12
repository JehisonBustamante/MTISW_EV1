package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.PruebaEntity;
import Mingeso.TopEducation.Repositories.PruebaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PruebaService {
    @Autowired
    PruebaRepository pruebaRepository;

    public ArrayList<PruebaEntity> obtenerDatos()
    {
        return (ArrayList<PruebaEntity>) pruebaRepository.findAll();
    }
}
