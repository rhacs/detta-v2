package cl.leid.detta.controladores;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/profesionales")
public class ProfesionalesController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    @GetMapping
    public ModelAndView mostrarGeneral(Locale locale) {
        // Crear modelo
        ModelAndView modelo = new ModelAndView("profesionales");

        // Agregar título
        modelo.addObject("titulo", messageSource.getMessage("title.professionals", null, locale));

        // Devolver modelo
        return modelo;
    }

}
