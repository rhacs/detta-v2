package cl.leid.detta.controladores;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class PanelController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    @GetMapping
    public ModelAndView mostrarPanel(Locale locale) {
        // Crear modelo-vista
        ModelAndView modeloVista = new ModelAndView("panel");

        // Agregar atributos al modelo
        modeloVista.addObject("titulo", messageSource.getMessage("title.dashboard", null, locale));

        // Devolver modelo
        return modeloVista;
    }

}
