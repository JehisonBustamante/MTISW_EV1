package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.PruebaEntity;
import Mingeso.TopEducation.Services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;

@Controller
@RequestMapping
public class PruebaController {
    @Autowired
    PruebaService pruebaService;

    @GetMapping("/data-upload")
    public String upload()
    {
        return "data-upload";
    }
    @PostMapping("/data-upload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        pruebaService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        pruebaService.leerCsv("Examenes.csv");
        return "redirect:/data-upload";
    }

    @GetMapping("/data-information")
    public String listar(Model model) {
        ArrayList<PruebaEntity> datos = pruebaService.obtenerDatos();
        model.addAttribute("datos", datos);
        return "data-information";
    }

}
