package cl.leid.detta.controladores;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.leid.detta.Constantes;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Capacitacion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.CapacitacionesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
@RequestMapping(path = "/capacitaciones")
public class CapacitacionesController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(CapacitacionesController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @Autowired
    private CapacitacionesRepositorio capacitacionesRepositorio;

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de capacitaciones disponibles dependiendo de la autoridad
     * del {@link Usuario} autenticado
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hace el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param model   objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping
    public String verListado(HttpServletRequest request, Authentication auth, Model model) {
        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Inicializar listado
            List<Capacitacion> capacitaciones = null;

            // Verificar rol del usuario
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar todas las capacitaciones
                capacitaciones = capacitacionesRepositorio.findAll(Sort.by(Direction.DESC, "fecha", "hora"));
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar la información del profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (profesional.isPresent()) {
                    // Buscar las capacitaciones ligadas al profesional
                    capacitaciones = capacitacionesRepositorio.findByProfesional(profesional.get(),
                            Sort.by(Direction.DESC, "fecha", "hora"));
                }
            } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                // Buscar información del cliente
                Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (cliente.isPresent()) {
                    // Buscar las capacitaciones relacionadas con el cliente
                    capacitaciones = capacitacionesRepositorio.findByCliente(cliente.get(),
                            Sort.by(Direction.DESC, "fecha", "hora"));
                }
            }

            // Agregar listado al modelo
            model.addAttribute("capacitaciones", capacitaciones);

            logger.info("Capacitaciones: {} ", capacitaciones);

            // Mostrar vista
            return "capacitaciones";
        }

        logger.error("Usuario no econtrado");

        // Redireccionar
        return "redirect:/login";
    }

    /**
     * Muestra el formulario para agregar una nueva {@link Capacitacion}
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le envía el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param model   objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = { "/agregar", "/{id}/editar" })
    public String verFormulario(@PathVariable Optional<Integer> id, HttpServletRequest request, Authentication auth,
            Model model) {
        // Obtener información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Verificar autoridad del usuario
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar todos los clientes
                List<Cliente> clientes = clientesRepositorio.findAll(Sort.by("usuario.nombre"));

                // Buscar todos los profesionales
                List<Profesional> profesionales = profesionalesRepositorio.findAll(Sort.by("usuario.nombre"));

                // Agregar listados al modelo
                model.addAttribute("clientes", clientes);
                model.addAttribute("profesionales", profesionales);
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar información del profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (profesional.isPresent()) {
                    // Buscar clientes del profesional
                    List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get(),
                            Sort.by("usuario.nombre"));

                    // Agregar al modelo
                    model.addAttribute("profesional", profesional.get());
                    model.addAttribute("clientes", clientes);
                }
            }
        }

        // Crear capacitación
        Capacitacion capacitacion = new Capacitacion();

        // Verificar si el id está presente
        if (id.isPresent()) {
            // Buscar información de la capacitación
            Optional<Capacitacion> cap = capacitacionesRepositorio.findById(id.get());

            // Verificar si existe
            if (cap.isPresent()) {
                // Reemplazar capacitación
                capacitacion = cap.get();
            } else {
                // Redireccionar
                return "redirect:/capacitaciones?noid=" + id.get();
            }
        }

        // Agregar capacitación al modelo
        model.addAttribute("capacitacion", capacitacion);

        logger.info("Capacitación: {}", capacitacion);

        // Mostrar formulario
        return "capacitaciones/formulario";
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Procesa el formulario enviado por el cliente
     * 
     * @param idnt          identificador numérico de la {@link Capacitacion}
     * @param capacitacion  objeto {@link Capacitacion} con la información a
     *                      agregar/editar
     * @param bindingResult objeto {@link BindingResult} con los errores de
     *                      validación
     * @param model         objeto {@link Model} con el modelo de la vista
     * @param request       objeto {@link HttpServletRequest} con la información de
     *                      la solicitud que le envía el cliente al
     *                      {@link HttpServlet}
     * @param auth          objeto {@link Authentication} con la información del
     *                      {@link Usuario} autenticado
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = { "/agregar", "/{idnt}/editar" })
    public String procesarFormulario(@PathVariable Optional<Integer> idnt, @Valid Capacitacion capacitacion,
            BindingResult bindingResult, Model model, HttpServletRequest request, Authentication auth) {
        // Verificar si hay errores
        if (bindingResult.hasErrors()) {
            // Obtener información del usuario
            Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

            // Verificar si existe
            if (usuario.isPresent()) {
                // Verificar autoridad del usuario
                if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                    // Buscar todos los clientes
                    List<Cliente> clientes = clientesRepositorio.findAll(Sort.by("usuario.nombre"));

                    // Buscar todos los profesionales
                    List<Profesional> profesionales = profesionalesRepositorio.findAll(Sort.by("usuario.nombre"));

                    // Agregar listados al modelo
                    model.addAttribute("clientes", clientes);
                    model.addAttribute("profesionales", profesionales);
                } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                    // Buscar información del profesional
                    Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                    // Verificar si existe
                    if (profesional.isPresent()) {
                        // Buscar clientes del profesional
                        List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get(),
                                Sort.by("usuario.nombre"));

                        // Agregar al modelo
                        model.addAttribute("profesional", profesional.get());
                        model.addAttribute("clientes", clientes);
                    }
                }
            }

            // Mostrar formulario
            return "capacitaciones/formulario";
        }

        // Agregar registro
        capacitacion = capacitacionesRepositorio.save(capacitacion);

        logger.info("Capacitación Agregada/Editada: {}", capacitacion);

        // Redireccionar
        return "redirect:/capacitaciones/" + capacitacion.getId();
    }

    @PostMapping(path = "/{id}/eliminar")
    public String eliminarRegistro(@PathVariable int id, Authentication auth) {
        // Buscar información de la capacitación
        Optional<Capacitacion> capacitacion = capacitacionesRepositorio.findById(id);

        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe el usuario
        if (usuario.isPresent()) {
            // Verificar si existe
            if (capacitacion.isPresent()) {
                // Crear acción
                Accion accion = new Accion("Eliminó una capacitación: " + capacitacion.get().getId(), 5, usuario.get());

                // Dejar registro
                accionesRepositorio.save(accion);

                // Eliminar registro
                capacitacionesRepositorio.delete(capacitacion.get());

                logger.info("Capacitación eliminada: {}", capacitacion.get());

                // Redireccionar
                return "redirect:/capacitaciones?remid=" + id;
            }

            // Redireccionar
            return "redirect:/capacitaciones?noid=" + id;
        }

        // Redireccionar
        return "redirect:/login";
    }

}
