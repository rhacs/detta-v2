package cl.leid.detta.controladores;

import java.util.ArrayList;
import java.util.Comparator;
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
import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccidentesRepositorio;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
@RequestMapping(path = "/accidentes")
public class AccidentesController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(AccidentesController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @Autowired
    private AccidentesRepositorio accidentesRepositorio;

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de accidentes según los permisos del {@link Usuario}
     * autenticado
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le envía el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param model   objeto {@link Modelo} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping
    public String verListado(HttpServletRequest request, Authentication auth, Model model) {
        // Inicializar listado de accidentes
        List<Accidente> accidentes = null;

        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Verificar autoridad del usuario
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar todos los accidentes
                accidentes = accidentesRepositorio.findAll(Sort.by(Direction.DESC, "fecha", "hora"));
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar información del Profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (profesional.isPresent()) {
                    // Buscar el listado de clientes
                    List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get());

                    // Inicializar listado
                    accidentes = new ArrayList<>();

                    // Por cada cliente
                    for (Cliente c : clientes) {
                        // Buscar accidentes
                        List<Accidente> aux = accidentesRepositorio.findByCliente(c);

                        // Verificar si hay resultados
                        if (aux != null && !aux.isEmpty()) {
                            // Agregar accidentes al listado
                            accidentes.addAll(aux);
                        }
                    }
                }
            } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                // Buscar información del cliente
                Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                // Verificar si hay resultados
                if (cliente.isPresent()) {
                    // Buscar accidentes del cliente
                    accidentes = accidentesRepositorio.findByCliente(cliente.get());
                }
            }
        }

        // Verificar si hay resultados
        if (accidentes != null && !accidentes.isEmpty()) {
            accidentes.sort(Comparator.comparing(Accidente::getFecha, Comparator.reverseOrder()));
        }

        // Agregar listado al modelo
        model.addAttribute("accidentes", accidentes);

        // Mostrar lista
        return "accidentes";
    }

    /**
     * Muestra la información de un {@link Accidente}
     * 
     * @param id    identificador numérico del {@link Accidente}
     * @param model objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id}")
    public String verDetalles(@PathVariable int id, Model model) {
        // Buscar información del Accidente
        Optional<Accidente> accidente = accidentesRepositorio.findById(id);

        // Verificar si existe
        if (accidente.isPresent()) {
            // Agregar accidente al modelo
            model.addAttribute("accidente", accidente.get());

            // Mostrar detalle
            return "accidentes/detalles";
        }

        // Redireccionar
        return "redirect:/accidentes?noid=" + id;
    }

    /**
     * Muestra el formulario para agregar un nuevo registro
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hace el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param model   objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/agregar")
    public String formularioAgregar(HttpServletRequest request, Authentication auth, Model model) {
        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Crear objeto accidente
        Accidente accidente = new Accidente();

        // Verificar si existe
        if (usuario.isPresent()) {
            // Verificar rol del usuario
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar todos los clientes
                List<Cliente> clientes = clientesRepositorio.findAll(Sort.by(Direction.ASC, "nombre"));

                // Agregar clientes al modelo
                model.addAttribute("clientes", clientes);
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar información del profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (profesional.isPresent()) {
                    // Buscar clientes del profesional
                    List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get(),
                            Sort.by(Direction.ASC, "nombre"));

                    // Agregar listado al modelo
                    model.addAttribute("clientes", clientes);
                }
            } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                // Buscar información del cliente
                Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (cliente.isPresent()) {
                    // Agregar cliente al accidente
                    accidente.setCliente(cliente.get());
                }
            }
        }

        // Agregar accidente al modelo
        model.addAttribute("accidente", accidente);
        model.addAttribute("accion", "agregar");

        // Mostrar formulario
        return "accidentes/formulario";
    }

    /**
     * Muestra el formulario de edición de un {@link Accidente}
     * 
     * @param id    identificador numérico del {@link Accidente}
     * @param auth  objeto {@link Authentication} con la información del
     *              {@link Usuario} autenticado
     * @param model objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id}/editar")
    public String formularioEditar(@PathVariable int id, Authentication auth, Model model) {
        // Buscar información del accidente
        Optional<Accidente> accidente = accidentesRepositorio.findById(id);

        // Verificar si existe
        if (accidente.isPresent()) {
            // Buscar todos los clientes
            List<Cliente> clientes = clientesRepositorio.findAll(Sort.by(Direction.ASC, "nombre"));

            // Agregar listado al modelo
            model.addAttribute("clientes", clientes);

            // Agregar accidente
            model.addAttribute("accidente", accidente);

            // Mostrar vista
            return "accidentes/formulario";
        }

        // Redireccionar
        return "redirect:/accidentes?noid=" + id;
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Procesa el formulario para editar/agregar un registro
     * 
     * @param idnt          identificador numérico del {@link Accidente}
     * @param accidente     objeto {@link Accidente} con la información a
     *                      agregar/editar
     * @param bindingResult objeto {@link BindingResult} con los errores de
     *                      validación
     * @param model         objeto {@link Model} con el modelo de la vista
     * @param request       objeto {@link HttpServletRequest} con la información de
     *                      la solicitud que le hace el cliente al
     *                      {@link HttpServlet}
     * @param auth          objeto {@link Authentication} con la información del
     *                      {@link Usuario} autenticado
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = { "/{idnt}/editar", "/agregar" })
    public String procesarFormulario(@PathVariable Optional<Integer> idnt, @Valid Accidente accidente,
            BindingResult bindingResult, Model model, HttpServletRequest request, Authentication auth) {
        // Obtener información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si hay errores
        if (bindingResult.hasErrors()) {
            // Verificar autoridad del usuario
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar clientes
                List<Cliente> clientes = clientesRepositorio.findAll(Sort.by(Direction.ASC, "nombre"));
                model.addAttribute("clientes", clientes);
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar información del Profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (profesional.isPresent()) {
                    // Buscar clientes del profesional
                    List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get(),
                            Sort.by(Direction.ASC, "name"));

                    // Agregar al modelo
                    model.addAttribute("clientes", clientes);
                }
            } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                // Buscar información del cliente
                Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (cliente.isPresent()) {
                    // Agregar al modelo
                    model.addAttribute("cliente", cliente);
                }
            }

            // Verificar acción
            if (idnt.isPresent()) {
                model.addAttribute("accion", "editar");
            } else {
                model.addAttribute("accion", "agregar");
            }

            // Mostrar formulario
            return "accidentes/formulario";
        }

        // Agregar registro al repositorio
        accidente = accidentesRepositorio.save(accidente);

        // Verificar si existe
        if (usuario.isPresent()) {
            // Crear nueva acción
            Accion accion = new Accion("Agregó un nuevo accidente: " + accidente.getId(), 4, usuario.get());

            // Agregar al repositorio
            accionesRepositorio.save(accion);

            // Depuración
            logger.info("{} agregó un nuevo accidente: {} ", usuario.get(), accidente.getId());
        }

        // Redireccionar
        return "redirect:/accidentes/" + accidente.getId();
    }

    @PostMapping(path = "/{id}/eliminar")
    public String eliminarRegistro(@PathVariable int id, Authentication auth) {
        // Obtener información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Obtener información del accidente
            Optional<Accidente> accidente = accidentesRepositorio.findById(id);

            // Verificar si existe
            if (accidente.isPresent()) {
                // Eliminar registro
                accidentesRepositorio.delete(accidente.get());

                // Crear acción
                Accion accion = new Accion("Eliminó un accidente: " + accidente.get().getId(), 4, usuario.get());

                // Guardar registro
                accionesRepositorio.save(accion);

                // Depuración
                logger.info("{} eliminó un registro de Accidente: {}", auth.getName(), accidente.get().getId());

                // Redireccionar
                return "redirect:/accidentes?remid=" + id;
            }

            // Redireccionar
            return "redirect:/accidentes?noid=" + id;
        }

        // Redireccionar
        return "redirect:/login";
    }

}
