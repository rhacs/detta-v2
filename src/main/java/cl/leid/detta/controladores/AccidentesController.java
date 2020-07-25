package cl.leid.detta.controladores;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.repositorios.AccidentesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;

@Controller
@RequestMapping(path = "/accidentes")
public class AccidentesController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link MessageSource} con lo métodos de parametrización e
     * internacionalización de los mensajes
     */
    @Autowired
    private MessageSource messageSource;

    /**
     * Objeto {@link JdbcTemplate} con los métodos para la manipulación de la base
     * de datos
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de accidentes según el tipo de usuario
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le envía el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del usuario
     *                autenticado
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping
    public ModelAndView mostrarListado(HttpServletRequest request, Authentication auth, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("accidentes");

        // Inicializar repositorios
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        AccidentesRepositorio accidentesRepositorio = new AccidentesRepositorio(jdbcTemplate);

        // Inicializar listado de accidentes
        List<Accidente> accidentes = null;

        // Verificar rol del usuario
        if (request.isUserInRole("ROLE_ADMIN")) {
            // Buscar todos los accidentes
            accidentes = accidentesRepositorio.buscarTodos();
        } else if (request.isUserInRole("ROLE_STAFF")) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Buscar listado de accidentes
            accidentes = accidentesRepositorio.buscarPorProfesionalId(profesional.getId());
        } else if (request.isUserInRole("ROLE_CLIENT")) {
            // Buscar información del cliente
            Cliente cliente = clientesRepositorio.buscarPorEmail(auth.getName());

            // Buscar los accidentes relacionados con el cliente
            accidentes = accidentesRepositorio.buscarPorClienteId(cliente.getId());
        }

        // Agregar listado a la vista
        vista.addObject("accidentes", accidentes);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.accidents", null, locale));

        // Devolver vista
        return vista;
    }

}
