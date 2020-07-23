package cl.leid.detta.controladores;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProfesionalesController {

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------
    
    @GetMapping(path = "/profesionales")
    public ModelAndView mostrarListado(Locale locale) {
        return new ModelAndView("profesionales");
    }
    
}
