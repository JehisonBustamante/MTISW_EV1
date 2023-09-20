package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.SubirDatosEntity;
import Mingeso.TopEducation.Services.SubirDatosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SubirDatosController {
    @Autowired
    SubirDatosService subirDatosService;

    @GetMapping("/data-upload")
    public String main()
    {
        return "dataUpload";
    }

    @GetMapping("/data-information")
    public String listar(Model model) {
        ArrayList<SubirDatosEntity> datas = subirDatosService.obtenerDatos();
        model.addAttribute("datas", datas);
        return "data-information";
    }

}
