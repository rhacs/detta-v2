package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;

@Controller
@RequestMapping(path = "/profesionales")
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

    /**
     * Muestra el listado de {@link Profesional}es
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping
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

    /**
     * Muestra los detalles del {@link Profesional} seleccionado
     * 
     * @param id     identificador numérico del {@link Profesional}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = "/{id}")
    public ModelAndView mostrarDetalles(@PathVariable int id, Locale locale) {
        // Inicializar el repositorio de Profesionales
        ProfesionalesRepositorio repositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Buscar el profesional
        Profesional profesional = repositorio.buscarPorId(id);

        // Verificar si existe
        if (profesional != null) {
            // Crear vista
            ModelAndView vista = new ModelAndView("profesionales/detalles");

            // Agregar profesional a la vista
            vista.addObject("profesional", profesional);

            // Agregar título
            vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

            return vista;
        }

        return new ModelAndView("redirect:/profesionales", "noid", id);
    }

    /**
     * Muestra el formulario de edición/inserción de un {@link Profesional}
     * 
     * @param id     identificador numérico del {@link Profesional} (opcional, puede
     *               ser {@code null})
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = { "/{id}/editar", "/agregar" })
    public ModelAndView mostrarFormulario(@PathVariable Optional<Integer> id, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales/formulario");

        // Verificar si es una edición
        if (id.isPresent()) {
            // Buscar registro
            Profesional profesional = new ProfesionalesRepositorio(jdbcTemplate).buscarPorId(id.get());

            // Verificar si existe
            if (profesional != null) {
                // Agregar a la vista
                vista.addObject("profesional", profesional);

                // Agregar acción a la vista
                vista.addObject("accion", "editar");
            } else {
                // Redireccionar al usuario si el registro no es encontrado
                return new ModelAndView("profesionales", "noid", id.get());
            }
        } else {
            // Agregar nuevo profesional a la vista
            vista.addObject("profesional", new Profesional());

            // Agregar acción
            vista.addObject("accion", "agregar");
        }

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        // Devolver vista
        return vista;
    }

}
