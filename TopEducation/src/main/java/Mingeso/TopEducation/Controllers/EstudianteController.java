package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class EstudianteController {

    @Autowired
    EstudianteService estudianteService;

    @GetMapping("/lista-estudiantes")
    public String listar(Model model)
    {
        ArrayList<EstudianteEntity> estudiantes = estudianteService.obtenerEstudiantes();
        model.addAttribute("estudiantes", estudiantes);
        return "index";
    }
}
