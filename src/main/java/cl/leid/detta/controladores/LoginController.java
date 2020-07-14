package cl.leid.detta.controladores;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = "/")
public class LoginController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link MessageSource} */
    @Autowired
    private MessageSource messageSource;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    @GetMapping(path = "/login")
    public ModelAndView mostrarFormulario(Locale locale) {
        // Obtener objeto de autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Verificar inicio de sesión
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            // Redireccionar
            return new ModelAndView("redirect:/");
        }

        // Crear nuevo modelo-vista
        ModelAndView modeloVista = new ModelAndView("login");

        // Agregar atributos
        modeloVista.addObject("titulo", messageSource.getMessage("title.login", null, locale));

        // Devolver modeloVista
        return modeloVista;
    }
    
    @GetMapping(path = "/denied")
    public ModelAndView permisoDenegado(Locale locale) {
        // Crear nuevo modelo-vista
        ModelAndView modeloVista = new ModelAndView("denied");

        // Agregar atributos
        modeloVista.addObject("titulo", messageSource.getMessage("title.access_denied", null, locale));

        // Devolver modeloVista
        return modeloVista;
    }

}
