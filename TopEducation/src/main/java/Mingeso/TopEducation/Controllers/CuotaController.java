package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.CuotaEntity;
import Mingeso.TopEducation.Entities.EstudianteEntity;
import Mingeso.TopEducation.Services.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@RequestMapping
public class CuotaController {
    @Autowired
    CuotaService cuotaService;

    @GetMapping("/registro-cuotas")
    public String verCuotas(Model model)
    {
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCuotas();
        model.addAttribute("Cuotas", cuotas);
        return "lista-cuotas";
    }
    @GetMapping("/agregar-cuotas")
    public String agregarCuota()
    {
        return "agregar-cuotas";
    }

    @PostMapping("/agregar-cuotas")
    public String nuevaCuota(@RequestParam("Rut del Estudiante")String RUN,
                             @RequestParam("Cantidad de Cuotas")Integer Cantidad,
                             Model model)
    {

        model.addAttribute("mensaje", "Cuota generada con éxito");
        return "main";
    }
}
