package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.leid.detta.Constantes;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
@RequestMapping(path = "/clientes")
public class ClientesController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(ClientesController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link MessageSource} con lo métodos de parametrización e
     * internacionalización de los mensajes
     */
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Cliente}s
     * 
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le envió el cliente al {@link HttpServlet}
     * @param model   objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping
    public String verListado(Authentication auth, HttpServletRequest request, Model model) {
        // Inicializar listado de clientes
        List<Cliente> clientes = null;

        // Verificar autoridad del usuario
        if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
            // Buscar todos los clientes
            clientes = clientesRepositorio.findAll(Sort.by(Direction.ASC, "usuario.nombre"));
        } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
            // Buscar información del Usuario
            Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

            // Verificar si existe
            if (usuario.isPresent()) {
                // Obtener info del Profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar
                if (profesional.isPresent()) {
                    // Buscar clientes del profesional
                    clientes = clientesRepositorio.findByProfesional(profesional.get(),
                            Sort.by(Direction.ASC, "nombre"));
                }
            }
        }

        // Agregar atributos a la vista
        model.addAttribute("clientes", clientes);

        return "clientes";
    }

    /**
     * Muestra el detalle de un {@link Cliente}
     * 
     * @param id    identificador numérico del {@link Cliente}
     * @param model objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la consulta
     */
    @GetMapping(path = "/{id}")
    public String verDetalles(@PathVariable int id, Model model) {
        // Obtener información del cliente
        Optional<Cliente> cliente = clientesRepositorio.findById(id);

        // Verificar si existe
        if (cliente.isPresent()) {
            // Agregar cliente a la vista
            model.addAttribute("cliente", cliente.get());

            // Mostrar vista
            return "clientes/detalles";
        }

        // Redireccionar
        return "redirect:/clientes?noid=" + id;
    }

    @GetMapping(path = { "/agregar", "/{id}/editar" })
    public String verFormulario(@PathVariable Optional<Integer> id, Model model) {
        // Verificar si el id está presente
        if (id.isPresent()) {
            // Buscar información del cliente
            Optional<Cliente> cliente = clientesRepositorio.findById(id.get());

            // Verificar si existe
            if (!cliente.isPresent()) {
                // Redireccionar
                return "redirect:/clientes?noid=" + id.get();
            }

            // Agregar cliente al modelo
            model.addAttribute("cliente", cliente.get());
            model.addAttribute("accion", "editar");
        } else {
            // Agregar nuevo cliente
            model.addAttribute("cliente", new Cliente());
            model.addAttribute("accion", "agregar");
        }

        // Obtener todos los profesionales
        List<Profesional> profesionales = profesionalesRepositorio.findAll();

        // Agregar listado al modelo
        model.addAttribute("profesionales", profesionales);

        return "clientes/formulario";
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo {@link Cliente} al repositorio
     * 
     * @param cliente       objeto {@link Cliente} enviado por el usuario
     * @param bindingResult objeto {@link BindingResult} con la información de los
     *                      errores de validación
     * @param model         objeto {@link Model} con el modelo de la vista
     * @param locale        objeto {@link Locale} con la información regional del
     *                      cliente
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = "/agregar")
    public String agregarRegistro(@Valid Cliente cliente, BindingResult bindingResult, Model model, Locale locale) {
        model.addAttribute("accion", "agregar");

        // Buscar profesionales
        List<Profesional> profesionales = profesionalesRepositorio.findAll();
        model.addAttribute("profesionales", profesionales);

        // Verificar si hay errores
        if (bindingResult.hasErrors()) {
            // Mostrar formulario
            return "clientes/formulario";
        }

        // Buscar usuario con el correo ingresado
        Optional<Usuario> usuarioExiste = usuariosRepositorio.findByEmail(cliente.getUsuario().getEmail());

        // Verificar si existe
        if (usuarioExiste.isPresent()) {
            // Agregar error
            model.addAttribute("error", messageSource.getMessage("form.error.used_email",
                    new Object[] { cliente.getUsuario().getEmail() }, locale));

            // Mostrar formulario
            return "clientes/formulario";
        }

        // Buscar cliente con el rut ingresado
        Optional<Cliente> clienteExiste = clientesRepositorio.findByRut(cliente.getRut());

        // Verificar si existe
        if (clienteExiste.isPresent()) {
            // Agregar error
            model.addAttribute("error",
                    messageSource.getMessage("form.error.used_rut", new Object[] { cliente.getRut() }, locale));

            // Mostrar formulario
            return "clientes/formulario";
        }

        // Agregar atributos faltantes
        cliente.getUsuario().setPassword(new BCryptPasswordEncoder().encode(cliente.getUsuario().getEmail()));
        cliente.getUsuario().getRol().setRole(Constantes.ROLE_CLIENT);
        cliente.getUsuario().getRol().setUsuario(cliente.getUsuario());

        // Guardar registro
        cliente = clientesRepositorio.save(cliente);

        // Redireccionar
        return "redirect:/clientes/" + cliente.getId();
    }

    /**
     * Edita la información de un {@link Cliente}
     * 
     * @param idnt          identificador numérico del {@link Cliente}
     * @param cliente       objeto {@link Cliente} con la información a actualizar
     * @param bindingResult objeto {@link BindingResult} con los errores de
     *                      validación
     * @param model         objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = "/{idnt}/editar")
    public String editarRegistro(@PathVariable int idnt, @Valid Cliente cliente, BindingResult bindingResult,
            Model model) {
        // Verificar si hay errores
        if (bindingResult.hasErrors()) {
            model.addAttribute("accion", "editar");

            // Buscar profesionales
            List<Profesional> profesionales = profesionalesRepositorio.findAll();
            model.addAttribute("profesionales", profesionales);

            // Mostrar formulario
            return "clientes/formulario";
        }

        // Obtener información del Cliente
        Optional<Cliente> existente = clientesRepositorio.findById(cliente.getId());

        // Verificar si existe
        if (existente.isPresent()) {
            // Agregar datos faltantes
            cliente.getUsuario().setPassword(existente.get().getUsuario().getPassword());
            cliente.getUsuario().setRol(existente.get().getUsuario().getRol());

            // Guardar cambios
            cliente = clientesRepositorio.save(cliente);

            return "redirect:/clientes/" + cliente.getId();
        }

        // Mostrar formulario
        return "redirect:/clientes?noid=" + idnt;
    }

    /**
     * Elimina un {@link Cliente} del sistema
     * 
     * @param id   identificador numérico del {@link Cliente}
     * @param auth objeto {@link Authentication} con la información del
     *             {@link Usuario} autenticado
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = "/{id}/eliminar")
    public String eliminarRegistro(@PathVariable int id, Authentication auth) {
        // Buscar información del registro
        Optional<Cliente> cliente = clientesRepositorio.findById(id);

        // Verificar si existe
        if (cliente.isPresent()) {
            // Eliminar registro
            clientesRepositorio.delete(cliente.get());

            // Depuración
            logger.info("{} eliminó un Cliente: {} ", auth.getName(), cliente.get().getUsuario().getEmail());

            // Obtener información del usuario
            Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

            // Verificar si existe
            if (usuario.isPresent()) {
                // Crear nueva Accion
                Accion accion = new Accion("Eliminó un cliente: " + cliente.get().getUsuario().getEmail(), 3,
                        usuario.get());

                // Agregar registro
                accionesRepositorio.save(accion);
            }

            // Redireccionar
            return "redirect:/clientes?remid=" + id;
        }

        // Redireccionar
        return "redirect:/clientes?noid=" + id;
    }

}
