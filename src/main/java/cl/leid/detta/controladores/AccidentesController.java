package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Rol del Administrador en el sistema */
    private static final String ROLE_ADMIN = "ROLE_ADMIN";

    /** Rol del {@link Profesional} en el sistema */
    private static final String ROLE_STAFF = "ROLE_STAFF";

    /** Rol del {@link Cliente} en el sistema */
    private static final String ROLE_CLIENT = "ROLE_CLIENT";

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
     * Muestra el listado de {@link Accidente}s según el rol del {@link Usuario}
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
        if (request.isUserInRole(ROLE_ADMIN)) {
            // Buscar todos los accidentes
            accidentes = accidentesRepositorio.buscarTodos();
        } else if (request.isUserInRole(ROLE_STAFF)) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Buscar listado de accidentes
            accidentes = accidentesRepositorio.buscarPorProfesionalId(profesional.getId());
        } else if (request.isUserInRole(ROLE_CLIENT)) {
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

    /**
     * Muestra los detalles de un {@link Accidente}
     * 
     * @param id      identificador numérico del {@link Accidente}
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le envía el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = "/{id}")
    public ModelAndView verDetalles(@PathVariable int id, HttpServletRequest request, Authentication auth,
            Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("accidentes/detalles");

        // Inicializar repositorios
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        AccidentesRepositorio accidentesRepositorio = new AccidentesRepositorio(jdbcTemplate);

        // Buscar información del accidente
        Accidente accidente = accidentesRepositorio.buscarPorId(id);

        // Verificar si no existe
        if (accidente == null) {
            // Redireccionar
            return new ModelAndView("redirect:/accidentes", "noid", id);
        }

        // Verificar rol del usuario
        if (request.isUserInRole(ROLE_STAFF)) {
            // Obtener información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Verificar que el accidente le pertenezca al profesional
            if (!accidente.getProfesionalId().equals(profesional.getId())) {
                // Redireccionar
                return new ModelAndView("redirect:/accidentes", "perm", true);
            }
        } else if (request.isUserInRole(ROLE_CLIENT)) {
            // Obtener información del cliente
            Cliente cliente = clientesRepositorio.buscarPorEmail(auth.getName());

            // Verificar que el accidente le pertenezca al cliente
            if (accidente.getClienteId() != cliente.getId()) {
                // Redireccionar
                return new ModelAndView("redirect:/accidentes", "perm", true);
            }
        }

        // Agregar accidente a la vista
        vista.addObject("accidente", accidente);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.accidents", null, locale));

        // Devolver vista
        return vista;
    }

    /**
     * Muestra el fomulario para agregar/editar un {@link Accidente}
     * 
     * @param id      identificador numérico del {@link Accidente}
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hace el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta a la solicitud
     */
    @GetMapping(path = { "/{id}/editar", "/agregar" })
    public ModelAndView mostrarFormulario(@PathVariable Optional<Integer> id, HttpServletRequest request,
            Authentication auth, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("accidentes/formulario");

        // Inicializar repositorios
        AccidentesRepositorio accidentesRepositorio = new AccidentesRepositorio(jdbcTemplate);
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Inicializar accidente
        Accidente accidente = null;

        // Inicializar acción
        String accion = "agregar";

        // Verificar si el id está presente
        if (id.isPresent()) {
            // Obtener información del accidente
            accidente = accidentesRepositorio.buscarPorId(id.get());

            // Verificar si no existe
            if (accidente == null) {
                // Redireccionar
                return new ModelAndView("redirect:/accidentes", "noid", id.get());
            }

            accion = "editar";
        }

        // Verificar rol del usuario
        if (request.isUserInRole(ROLE_ADMIN)) {
            // Buscar todos los clientes
            List<Cliente> clientes = clientesRepositorio.buscarTodos();

            // Agregar listado a la vista
            vista.addObject("clientes", clientes);
        } else if (request.isUserInRole(ROLE_STAFF)) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Buscar clientes del profesional
            List<Cliente> clientes = clientesRepositorio.buscarPorProfesionalId(profesional.getId());

            // Agregar listado a la vista
            vista.addObject("clientes", clientes);
        } else if (request.isUserInRole(ROLE_CLIENT)) {
            // Buscar información del cliente
            Cliente cliente = clientesRepositorio.buscarPorEmail(auth.getName());

            // Agregar cliente a la vista
            vista.addObject("cliente", cliente);
        }

        // Agregar acción a la vista
        vista.addObject("accion", accion);

        // Agregar accidente a la vista
        vista.addObject("accidente", accidente == null ? new Accidente() : accidente);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.accidents", null, locale));

        // Devolver vista
        return vista;
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Procesa el formulario cuando el {@link Usuario} agrega un nuevo
     * {@link Accidente}
     * 
     * @param request   objeto {@link HttpServletRequest} con la información de la
     *                  solicitud que le hace el cliente al {@link HttpServlet}
     * @param auth      objeto {@link Authentication} con la información del
     *                  {@link Usuario} autenticado
     * @param accidente objeto {@link Accidente} con la información a agregar
     * @param locale    objeto {@link Locale} con la información regional del
     *                  cliente
     * @return un objeto {@link ModelAndView} con la respuesta de la solicitud
     */
    @PostMapping(path = "/agregar")
    public ModelAndView procesarAgregar(HttpServletRequest request, Authentication auth,
            @ModelAttribute Accidente accidente, Locale locale) {
        // Inicializar repositorios
        AccidentesRepositorio accidentesRepositorio = new AccidentesRepositorio(jdbcTemplate);
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);

        // Agregar registro
        if (accidentesRepositorio.agregarRegistro(accidente)) {
            // Buscar registro
            accidente = accidentesRepositorio.buscarUltimo();

            // Redireccionar
            return new ModelAndView("redirect:/accidentes/" + accidente.getId());
        }

        // Crear vista
        ModelAndView vista = new ModelAndView("accidentes/formulario");

        // Agregar accidente a la vista
        vista.addObject("accidente", accidente);

        // Verificar rol del usuario
        if (request.isUserInRole(ROLE_ADMIN)) {
            // Buscar todos los clientes
            List<Cliente> clientes = clientesRepositorio.buscarTodos();

            // Agregar listado a la vista
            vista.addObject("clientes", clientes);
        } else if (request.isUserInRole(ROLE_STAFF)) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Buscar clientes del profesional
            List<Cliente> clientes = clientesRepositorio.buscarPorProfesionalId(profesional.getId());

            // Agregar listado a la vista
            vista.addObject("clientes", clientes);
        } else if (request.isUserInRole(ROLE_CLIENT)) {
            // Buscar información del cliente
            Cliente cliente = clientesRepositorio.buscarPorEmail(auth.getName());

            // Agregar cliente a la vista
            vista.addObject("cliente", cliente);
        }

        // Agregar acción a la vista
        vista.addObject("accion", "agregar");

        // Agregar accidente a la vista
        vista.addObject("accidente", accidente == null ? new Accidente() : accidente);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.accidents", null, locale));

        // Devolver vista
        return vista;
    }

    /**
     * Procesa el formulario cuando el usuario edita un {@link Accidente}
     * 
     * @param idnt      identificador numérico del {@link Accidente}
     * @param accidente objeto {@link Accidente} con la información a actualizar
     * @param locale    objeto {@link Locale} con la información regional del
     *                  cliente
     * @return un objeto {@link ModelAndView} con la respuesta a la solicitud
     */
    @PostMapping(path = "/{idnt}/editar")
    public ModelAndView procesarEdicion(@PathVariable int idnt, @ModelAttribute Accidente accidente, Locale locale) {
        // Inicializar repositorios
        AccidentesRepositorio accidentesRepositorio = new AccidentesRepositorio(jdbcTemplate);

        // Actualizar registro
        accidentesRepositorio.actualizarRegistro(accidente);

        // Devolver vista
        return new ModelAndView("redirect:/accidentes/" + idnt);
    }

}
