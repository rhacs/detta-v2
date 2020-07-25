package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
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
@RequestMapping(path = "/clientes")
public class ClientesController {

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
     * Muestra el listado de {@link Cliente}s según la autoridad del usuario
     * 
     * @param auth    objeto {@link Authentication} con la información del usuario
     *                autenticado
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hizo el cliente al {@link HttpServlet}
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping
    public ModelAndView mostrarListado(Authentication auth, HttpServletRequest request, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("clientes");

        // Inicializar repositorio
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Inicializar listado
        List<Cliente> clientes = null;

        // Verificar autoridad del usuario
        if (request.isUserInRole("ROLE_ADMIN")) {
            // Buscar todos los clientes
            clientes = clientesRepositorio.buscarTodos();
        } else if (request.isUserInRole("ROLE_STAFF")) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Buscar clientes del profesional
            clientes = clientesRepositorio.buscarPorProfesionalId(profesional.getId());
        }

        // Agregar listado a la vista
        vista.addObject("clientes", clientes);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.clients", null, locale));

        // Devolver vista
        return vista;
    }

    /**
     * Muestra el detalle de un {@link Profesional}
     * 
     * @param id      identificador numérico del {@link Profesional}
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le envía el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del usuario
     *                autenticado
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = "/{id}")
    public ModelAndView mostrarDetalles(@PathVariable int id, HttpServletRequest request, Authentication auth,
            Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("clientes/detalles");

        // Inicializar repositorios
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Buscar información del cliente
        Cliente cliente = clientesRepositorio.buscarPorId(id);

        // Verificar si no existe
        if (cliente == null) {
            // Redireccionar
            return new ModelAndView("redirect:/clientes", "noid", id);
        }

        // Agregar cliente a la vista
        vista.addObject("cliente", cliente);

        // Verificar autoridad
        if (request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_CLIENT")) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorId(cliente.getProfesionalId());

            // Verificar si existe
            if (profesional != null) {
                // Agregar a la vista
                vista.addObject("profesional", profesional);
            }
        }

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.clients", null, locale));

        // Devolver vista
        return vista;
    }

    /**
     * Muestra el formulario para agregar/editar un {@link Cliente}
     * 
     * @param id     identificador numérico del {@link Cliente}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = { "/{id}/editar", "/agregar" })
    public ModelAndView mostrarFormulario(@PathVariable Optional<Integer> id, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("clientes/formulario");

        // Inicializar acción
        String accion = null;

        // Inicializar cliente
        Cliente cliente = new Cliente();

        // Verificar si el id está presente
        if (id.isPresent()) {
            // Buscar cliente
            cliente = new ClientesRepositorio(jdbcTemplate).buscarPorId(id.get());

            // Verificar si no existe
            if (cliente == null) {
                // Redireccionar
                return new ModelAndView("redirect:/clientes", "noid", id.get());
            }

            accion = "editar";
        } else {
            accion = "agregar";
        }

        // Buscar profesionales
        List<Profesional> profesionales = new ProfesionalesRepositorio(jdbcTemplate).buscarTodos();

        // Agregar profesionales a la vista
        vista.addObject("profesionales", profesionales);

        // Agregar cliente a la vista
        vista.addObject("cliente", cliente);

        // Agregar acción
        vista.addObject("accion", accion);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.clients", null, locale));

        // Devolver vista
        return vista;
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Procesa el formulario al agregar un nuevo {@link Cliente}
     * 
     * @param cliente objeto {@link Cliente} con la información a agregar
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @PostMapping(path = "/agregar")
    public ModelAndView formularioAgregar(@ModelAttribute Cliente cliente, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("clientes/formulario");

        // Inicializar repositorios
        UsuariosRepositorio usuariosRepositorio = new UsuariosRepositorio(jdbcTemplate);
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);

        // Buscar correo electrónico en el repositorio de usuarios
        if (usuariosRepositorio.buscarPorEmail(cliente.getEmail()) != null) {
            // Agregar error
            vista.addObject("error",
                    messageSource.getMessage("form.error.used_email", new Object[] { cliente.getEmail() }, locale));
        } else {
            // Buscar rut en el repositorio de clientes
            if (clientesRepositorio.buscarPorRut(cliente.getRut()) != null) {
                // Agregar error
                vista.addObject("error",
                        messageSource.getMessage("form.error.used_rut", new Object[] { cliente.getRut() }, locale));
            } else {
                // Agregar contraseña
                cliente.setPassword(new BCryptPasswordEncoder().encode(cliente.getEmail()));

                // Agregar al repositorio
                if (clientesRepositorio.agregarRegistro(cliente)) {
                    // Buscar cliente
                    cliente = clientesRepositorio.buscarPorEmail(cliente.getEmail());

                    // Redireccionar
                    return new ModelAndView("redirect:/clientes/" + cliente.getId());
                } else {
                    // Agregar error
                    vista.addObject("error", messageSource.getMessage("error.unexpected_add", null, locale));
                }
            }
        }

        // Agregar cliente a la vista
        vista.addObject("cliente", cliente);

        // Buscar profesionales
        List<Profesional> profesionales = new ProfesionalesRepositorio(jdbcTemplate).buscarTodos();

        // Agregar profesionales a la vista
        vista.addObject("profesionales", profesionales);

        // Agregar acción
        vista.addObject("accion", "agregar");

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.clients", null, locale));

        // Devolver vista
        return vista;
    }

}
