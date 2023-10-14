package Mingeso.TopEducation.Controllers;

import Mingeso.TopEducation.Entities.CuotaEntity;
import Mingeso.TopEducation.Services.CuotaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@Controller
@RequestMapping
public class CuotaController {
    @Autowired
    CuotaService cuotaService;

    @GetMapping("/ver-cuotas")
    public String verCuotas()
    {
        return "verCuotas";
    }

    @PostMapping("/ver-cuotas")
    public String verCuotas(@RequestParam("RUN") String RUN, HttpSession session)
    {
        session.setAttribute("RunCuotas", RUN);
        return "redirect:/registro-cuotas";
    }

    @GetMapping("/registro-cuotas")
    public String listaCuotas(HttpSession session, Model model)
    {
        String RUN = (String) session.getAttribute("RunCuotas");
        ArrayList<CuotaEntity> cuotas = cuotaService.obtenerCuotas(RUN);
        if(cuotas.isEmpty())
        {
            model.addAttribute("mensaje", "No hay cuotas asociadas al estudiante");
            return "main";
        }
        model.addAttribute("Cuotas", cuotas);
        return "lista-cuotas";
    }
    @GetMapping("/agregar-cuotas")
    public String agregarCuota(HttpSession session,
                               Model model)
    {
        String tipoColegio = (String) session.getAttribute("TipoColegio");
        Integer max = cuotaService.cuotasMaximas(tipoColegio);
        model.addAttribute("max", max);
        return "cuotas";
    }

    @PostMapping("/agregar-cuotas")
    public String nuevaCuota(@RequestParam("CantidadCuotas") Integer Cantidad,
                             HttpSession session,
                             Model model)
    {
        String RUN = (String) session.getAttribute("RUN");
        String tipoColegio = (String) session.getAttribute("TipoColegio");
        String anioEgreso = (String) session.getAttribute("AnioEgreso");
        Integer anio = Integer.parseInt(anioEgreso);
        cuotaService.generarCuota(RUN, tipoColegio, anio, Cantidad);
        model.addAttribute("mensaje", "Cuotas generadas con exito");
        return "main";
    }

    @GetMapping("/ingreso-pago")
    public String pago()
    {
        return "ipago";
    }
    @PostMapping("/ingreso-pago")
    public String pago(@RequestParam("RUN") String RUN,
                       HttpSession session)
    {
        session.setAttribute("RunPago", RUN);
        return "redirect:/pagar";
    }
    @GetMapping("/pagar")
    public String pagar()
    {
        return "pagar";
    }
    @PostMapping("/pagar")
    public String pagar(HttpSession session,
                        Model model)
    {
        String RUN = (String) session.getAttribute("RunPago");
        ArrayList<CuotaEntity> busqueda = cuotaService.buscarCuotas(RUN);
        cuotaService.pagarPendientes(busqueda);
        model.addAttribute("busqueda", busqueda);

        if(busqueda.isEmpty())
        {
            model.addAttribute("mensaje", "No hay cuotas por pagar");
            return "main";
        }
        model.addAttribute("mensaje", "Pago realizado con éxito");
        return "pagar";
    }
}
