package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
@RequestMapping(path = "/profesionales")
public class ProfesionalesController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(ProfesionalesController.class);

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

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

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

        // Buscar listado de profesionales
        List<Profesional> profesionales = profesionalesRepositorio.findAll(Sort.by(Sort.Direction.ASC, "nombre"));

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
        // Buscar el profesional
        Optional<Profesional> profesional = profesionalesRepositorio.findById(id);

        // Verificar si existe
        if (profesional.isPresent()) {
            // Crear vista
            ModelAndView vista = new ModelAndView("profesionales/detalles");

            // Agregar profesional a la vista
            vista.addObject("profesional", profesional.get());

            // Buscar listado de clientes
            List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get());

            // Agregar listado a la vista
            vista.addObject("clientes", clientes);

            // Agregar título
            vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

            return vista;
        }

        // Depuración
        logger.info("{} solicitó la información de un profesional que no existe: {}",
                SecurityContextHolder.getContext().getAuthentication().getName(), id);

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
                // Depuración
                logger.error("{} intentó editar la información de un Profesional que no existe: {}",
                        SecurityContextHolder.getContext().getAuthentication().getName(), id.get());

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

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Procesa el formulario cuando se trata de una solicitud para agregar un nuevo
     * registro
     * 
     * @param profesional objeto {@link Profesional} con la información a agregar
     * @param locale      objeto {@link Locale} con la información regional del
     *                    cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @PostMapping(path = "/agregar")
    public ModelAndView procesarFormulario(@ModelAttribute Profesional profesional, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales/formulario");

        // Inicializar repositorios
        UsuariosRepositorio usuariosRepositorio = new UsuariosRepositorio(jdbcTemplate);
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Verificar si el correo no existe
        if (usuariosRepositorio.buscarPorEmail(profesional.getEmail()) == null) {
            // Agregar contraseña
            profesional.setPassword(new BCryptPasswordEncoder().encode(profesional.getEmail()));

            // Agregar profesional al repositorio
            if (profesionalesRepositorio.agregarRegistro(profesional)) {
                // Buscar registro
                profesional = profesionalesRepositorio.buscarPorEmail(profesional.getEmail());

                logger.info("{} agregó un nuevo profesional: /detta/profesionales/{}",
                        SecurityContextHolder.getContext().getAuthentication().getName(), profesional.getId());

                // Redireccionar
                return new ModelAndView("redirect:/profesionales/" + profesional.getId());
            } else {
                // Agregar error
                vista.addObject("error", messageSource.getMessage("error.unexpected_add", null, locale));
            }
        } else {
            // Agregar error
            vista.addObject("error",
                    messageSource.getMessage("form.error.used_email", new Object[] { profesional.getEmail() }, locale));
        }

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        // Agregar profesional a la vista
        vista.addObject("profesional", profesional);

        // Agregar acción
        vista.addObject("accion", "agregar");

        // Devolver vista
        return vista;
    }

    /**
     * Procesa el formulario cuando se trata de una actualización de información
     * 
     * @param idnt        identificador numérico del {@link Profesional}
     * @param profesional objeto {@link Profesional} con la información a actualizar
     * @param locale      objeto {@link Locale} con la información regional del
     *                    cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @PostMapping(path = "/{idnt}/editar")
    public ModelAndView procesarEdicion(@PathVariable int idnt, @ModelAttribute Profesional profesional,
            Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales/formulario");

        // Inicializar repositorios
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Obtener información del profesional
        Profesional existente = profesionalesRepositorio.buscarPorId(profesional.getId());

        // Verificar si el profesional existe
        if (existente != null) {
            // Recuperar contraseña
            profesional.setPassword(existente.getPassword());

            // Actualizar registro
            if (profesionalesRepositorio.actualizarRegistro(profesional)) {
                logger.info("{} editó la información de un Profesional: /detta/profesionales/{}",
                        SecurityContextHolder.getContext().getAuthentication().getName(), profesional.getId());

                // Redireccionar
                return new ModelAndView("redirect:/profesionales/" + profesional.getId());
            } else {
                // Agregar error
                vista.addObject("error", messageSource.getMessage("error.unexpected_edit", null, locale));
            }
        } else {
            logger.error("{} intentó editar la información de un profesional que no existe",
                    SecurityContextHolder.getContext().getAuthentication().getName());
            // Redireccionar
            return new ModelAndView("redirect:/profesionales", "noid", idnt);
        }

        // Agregar acción
        vista.addObject("accion", "editar");

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        // Agregar profesional
        vista.addObject("profesional", profesional);

        // Devolver vista
        return vista;
    }

    /**
     * Elimina un registro del repositorio
     * 
     * @param id     identificador numérico del {@link Profesional}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @PostMapping(path = "/{id}/eliminar")
    public ModelAndView eliminarRegistro(@PathVariable int id, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales/detalles");

        // Inicializar repositorio
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Buscar profesional
        Profesional profesional = profesionalesRepositorio.buscarPorId(id);

        // Verificar si existe
        if (profesional != null) {
            // Eliminar registro
            if (profesionalesRepositorio.eliminarRegistro(profesional)) {
                logger.info("{} eliminó un Profesional: {}",
                        SecurityContextHolder.getContext().getAuthentication().getName(), profesional.getEmail());

                // Redireccionar
                return new ModelAndView("redirect:/profesionales", "remid", id);
            } else {
                // Agregar error
                vista.addObject("error", messageSource.getMessage("error.unexpected_del", null, locale));
            }
        } else {
            logger.error("{} intentó eliminar un Profesional que no existe",
                    SecurityContextHolder.getContext().getAuthentication().getName());

            // Redireccionar
            return new ModelAndView("redirect:/profesionales", "noid", id);
        }

        // Agregar profesional a la vista
        vista.addObject("profesional", profesional);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        // Devolver vista
        return vista;
    }

}
