package Mingeso.TopEducation.Services;

import Mingeso.TopEducation.Entities.SubirDatosEntity;
import Mingeso.TopEducation.Repositories.SubirDatosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubirDatosService {
    @Autowired
    SubirDatosRepository subirDatosRepository;

    public ArrayList<SubirDatosEntity> obtenerDatos()
    {
        return (ArrayList<SubirDatosEntity>) subirDatosRepository.findAll();
    }
}
