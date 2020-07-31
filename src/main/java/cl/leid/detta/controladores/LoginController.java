package cl.leid.detta.controladores;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cl.leid.detta.modelos.Usuario;

@Controller
public class LoginController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(LoginController.class);

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el formulario de inicio de sesión
     * 
     * @param auth   objeto {@link Authentication} con la información del usuario
     *               autenticado
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/login")
    public String mostrarFormulario(Authentication auth, Locale locale, Model model) {
        // Verificar inicio de sesión
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            logger.log(Level.INFO, "{} intentó acceder a /login ya estando autenticado", auth.getName());

            // Redireccionar al panel
            return "redirect:/";
        }

        logger.info("Nuevo inicio de sesión");

        // Agregar nuevo usuario al modelo
        model.addAttribute("usuario", new Usuario());

        // Crear vista y devolver
        return "auth/login";
    }

    /**
     * Muestra la página de acceso denegado
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hizo el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del usuario
     *                autenticado
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/youShallNotPass")
    public String permisoDenegado(HttpServletRequest request, Authentication auth) {
        logger.warn("Violación de seguridad");

        // Mostrar página de acceso denegado
        return "auth/denied";
    }

}
