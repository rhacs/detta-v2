package cl.leid.detta.controladores;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccionesRepositorio;

@Controller
public class LoginController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link JdbcTemplate} con los métodos de manipulación de la base de
     * datos
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el formulario de inicio de sesión
     * 
     * @param auth   objeto {@link Authentication} con la información del usuario
     *               autenticado
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta que se le envía al
     *         cliente
     */
    @GetMapping(path = "/login")
    public ModelAndView mostrarFormulario(Authentication auth, Locale locale) {
        // Verificar inicio de sesión
        if (auth != null && !(auth instanceof AnonymousAuthenticationToken) && auth.isAuthenticated()) {
            // Redireccionar al panel
            return new ModelAndView("redirect:/");
        }

        // Crear vista y devolver
        return new ModelAndView("auth/login", "usuario", new Usuario());
    }

    /**
     * Muestra la página de acceso denegado
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hizo el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del usuario
     *                autenticado
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = "/youShallNotPass")
    public ModelAndView permisoDenegado(HttpServletRequest request, Authentication auth) {
        // Obtener detalles del usuario
        UserDetails detalles = (UserDetails) auth.getPrincipal();

        // Inicializar repositorio de acciones
        AccionesRepositorio accionesRepositorio = new AccionesRepositorio(jdbcTemplate);

        // Crear nueva acción
        Accion accion = new Accion(detalles.getUsername(), "Intentó acceder a la url: " + request.getContextPath(), 1);

        // Agregar acción al repositorio
        accionesRepositorio.agregarRegistro(accion);

        return new ModelAndView("auth/denied");
    }

}
