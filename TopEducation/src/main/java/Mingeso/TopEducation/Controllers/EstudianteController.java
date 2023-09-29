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
                                  @RequestParam("Apellidos") String apellidos,
                                  @RequestParam("Nombres") String nombres,
                                  @RequestParam("Fecha de Nacimiento") String fecha_nacimiento,
                                  @RequestParam("Tipo de Colegio") String tipo_colegio,
                                  @RequestParam("Nombre de Colegio") String nombre_colegio,
                                  @RequestParam("Año de Egreso") Integer anio_egreso)
    {
        estudianteService.guardarEstudiante(RUN, apellidos, nombres, fecha_nacimiento, tipo_colegio, nombre_colegio, anio_egreso);
        return "cuotas";
    }
}
