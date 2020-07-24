package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;

@Controller
public class ProfesionalesController {

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

    @GetMapping(path = "/profesionales")
    public ModelAndView mostrarListado(Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales");

        // Buscar todos los profesionales
        List<Profesional> profesionales = new ProfesionalesRepositorio(jdbcTemplate).buscarTodos();

        // Agregar listado a la vista
        vista.addObject("profesionales", profesionales);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        return vista;
    }

}
