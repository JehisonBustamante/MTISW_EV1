package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Services.EstudianteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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
                                  @RequestParam("FechaNacimiento") LocalDate fechaNacimiento,
                                  @RequestParam("TipoColegio") String tipoColegio,
                                  @RequestParam("NombreColegio") String nombreColegio,
                                  @RequestParam("AnioEgreso") String anioEgreso,
                                  HttpSession session)
    {
        Integer egreso = Integer.parseInt(anioEgreso);
        estudianteService.guardarEstudiante(RUN, apellidos, nombres, fechaNacimiento, tipoColegio, nombreColegio, egreso);
        session.setAttribute("RUN", RUN);
        session.setAttribute("TipoColegio", tipoColegio);
        session.setAttribute("AnioEgreso", anioEgreso);
        return "redirect:/agregar-cuotas";
    }
}
