package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.PruebaEntity;
import Mingeso.TopEducation.Services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class PruebaController {
    @Autowired
    PruebaService pruebaService;

    @GetMapping("/data-upload")
    public String main()
    {
        return "dataUpload";
    }

    @GetMapping("/data-information")
    public String listar(Model model) {
        ArrayList<PruebaEntity> datas = pruebaService.obtenerDatos();
        model.addAttribute("datas", datas);
        return "data-information";
    }

}
