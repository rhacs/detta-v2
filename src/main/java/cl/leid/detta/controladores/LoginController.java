package cl.leid.detta.controladores;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos para hacer depuración */
    private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link MessageSource} que contiene los métodos de obtención de textos
     * de la aplicación
     */
    @Autowired
    private MessageSource messageSource;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el formulario de inicio de sesión, siempre y cuando el usuario no
     * esté autenticado
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con el modelo y la vista de la
     *         respuesta
     */
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

    /**
     * Muestra al usuario la página de acceso denegado cuando no tiene las
     * credenciales necesarias para ejecutar una acción en particular
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hace el cliente al {@link HttpServlet}
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con el modelo y la vista de la
     *         respuesta
     */
    @GetMapping(path = "/denied")
    public ModelAndView permisoDenegado(HttpServletRequest request, Locale locale) {
        // Obtener autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Verificar autenticación
        if (auth != null) {
            // Mostrar acción por consola
            LOG.warn("Usuario: '" + auth.getName() + "' intentó acceder a la sección protegida: "
                    + request.getContextPath());
        }

        // Crear nuevo modelo-vista
        ModelAndView modeloVista = new ModelAndView("denied");

        // Agregar atributos
        modeloVista.addObject("titulo", messageSource.getMessage("title.access_denied", null, locale));

        // Devolver modeloVista
        return modeloVista;
    }

}
