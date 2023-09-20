package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/nuevo-estudiante")
    public String agregarEstudiante()
    {
        return "nuevo-estudiante";
    }

    @PostMapping("/nuevo-estudiante")
    public String nuevoEstudiante(@RequestParam("RUN") String RUN,
                                  @RequestParam("apellidos") String apellidos,
                                  @RequestParam("nombres") String nombres,
                                  @RequestParam("fecha_nacimiento") String fecha_nacimiento,
                                  @RequestParam("tipo_colegio") String tipo_colegio,
                                  @RequestParam("nombre_colegio") String nombre_colegio,
                                  @RequestParam("anio_egreso") Integer anio_egreso)
    {
        estudianteService.guardarEstudiante(RUN, apellidos, nombres, fecha_nacimiento, tipo_colegio, nombre_colegio, anio_egreso);
        return "redirect:/nuevo-estudiante";
    }
}
