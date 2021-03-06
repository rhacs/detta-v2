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
import cl.leid.detta.modelos.Asesoria;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AsesoriasRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
@RequestMapping(path = "/asesorias")
public class AsesoriasController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(AsesoriasController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private AsesoriasRepositorio asesoriasRepositorio;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    // Solicituded GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Asesoria}s según la autoridad del
     * {@link Usuario}
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hace el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param model   objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta
     */
    @GetMapping
    public String verListado(HttpServletRequest request, Authentication auth, Model model) {
        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Inicializar listado
            List<Asesoria> asesorias = null;

            // Verificar autoridad
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar todas las asesorías
                asesorias = asesoriasRepositorio.findAll(Sort.by(Direction.DESC, "fecha", "hora"));
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar información del Profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si no existe
                if (!profesional.isPresent()){
                    // Depuración
                    logger.error("Profesional no encontrado para: {}", usuario.get());

                    // Redireccionar
                    return "redirect:/login";
                }

                // Buscar asesorías del profesional
                asesorias = asesoriasRepositorio.findByProfesional(profesional.get(),
                        Sort.by(Direction.DESC, "fecha", "hora"));
            } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                // Buscar información del Cliente
                Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                // Verificar si no existe
                if (!cliente.isPresent()) {
                    // Depuración
                    logger.error("Cliente no encontrado para: {}", usuario.get());

                    // Redireccionar
                    return "redirect:/login";
                }

                // Buscar asesorías del cliente
                asesorias = asesoriasRepositorio.findByCliente(cliente.get());
            }

            // Agregar listado al modelo
            model.addAttribute("asesorias", asesorias);

            // Mostrar vista
            return "asesorias";
        }

        // Depuración
        logger.error("Usuario no encontrado: {}", auth.getName());

        // Redireccionar
        return "redirect:/login";
    }

    /**
     * Muestra los detalles de la {@link Asesoria} seleccionada
     * 
     * @param id      identificador numérico de la {@link Asesoria}
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                solicitud que le hace el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del
     *                {@link Usuario} autenticado
     * @param model   objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id}")
    public String verDetalles(@PathVariable int id, HttpServletRequest request, Authentication auth, Model model) {
        // Buscar información de la Asesoría
        Optional<Asesoria> asesoria = asesoriasRepositorio.findById(id);

        // Verificar si existe
        if (asesoria.isPresent()) {
            // Obtener información del Usuario
            Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

            // Verificar si existe
            if (usuario.isPresent()) {
                // Verificar autoridad del usuario
                if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                    // Buscar información del Profesional
                    Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                    // Verificar si no existe
                    if (!profesional.isPresent()) {
                        // Depuración
                        logger.error("Información del Profesional no encontrada para: {}", usuario.get());

                        // Redireccionar
                        return "redirect:/login";
                    }

                    // Verificar si la asesoria no le pertenece al Profesional
                    if (!asesoria.get().getProfesional().equals(profesional.get())) {
                        // Depuración
                        logger.info("[SEC] {} intentó ver el detalle de una Asesoría que no le corresponde: {}",
                                profesional.get(), asesoria.get());

                        // Redireccionar
                        return "redirect:/asesorias?perm=false";
                    }
                } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                    // Buscar información del cliente
                    Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                    // Verificar si no existe
                    if (!cliente.isPresent()) {
                        // Depuración
                        logger.error("Información del Cliente no encontrada para: {}", usuario.get());

                        // Redireccionar
                        return "redirect:/login";
                    }

                    // Verificar si la asesoría no le pertenece al Cliente
                    if (!asesoria.get().getCliente().equals(cliente.get())) {
                        // Depuración
                        logger.info("[SEC] {} intentó ver el detalle de una Asesoría que no le corresponde: {}",
                                cliente.get(), asesoria.get());

                        // Redireccionar
                        return "redirect:/asesorias?perm=false";
                    }
                }

                // Agregar la asesoría al modelo
                model.addAttribute("asesoria", asesoria.get());

                // Mostrar detalles
                return "asesorias/detalles";
            }

            // Depuración
            logger.error("Información para el Usuario {} no encontrada", auth.getName());

            // Redireccionar
            return "redirect:/login";
        }

        // Depuración
        logger.error("Información para la Asesoría {} no encontrada", id);

        // Redireccionar
        return "redirect:/asesorias?noid=" + id;
    }

    /**
     * Muestra el formulario para agregar/editar una {@link Asesoria}
     * 
     * @param id    identificador numérico de la {@link Asesoria}
     * @param model objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = { "/{id}/editar", "/agregar" })
    public String formulario(@PathVariable Optional<Integer> id, Model model) {
        // Inicializar objeto asesoría
        Asesoria asesoria = new Asesoria();

        // Verificar si el id está presente en la URL
        if (id.isPresent()) {
            // Buscar información de la asesoria
            Optional<Asesoria> aux = asesoriasRepositorio.findById(id.get());

            // Verificar si existe
            if (aux.isPresent()) {
                // Reemplazar asesoria
                asesoria = aux.get();
            }
        }

        // Buscar todos los profesionales
        List<Profesional> profesionales = profesionalesRepositorio.findAll(Sort.by("usuario.nombre"));

        // Buscar todos los clientes
        List<Cliente> clientes = clientesRepositorio.findAll(Sort.by("usuario.nombre", "rut"));

        // Agregar objetos al modelo
        model.addAttribute("asesoria", asesoria);
        model.addAttribute("profesionales", profesionales);
        model.addAttribute("clientes", clientes);

        // Mostrar formulario
        return "asesorias/formulario";
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Procesa el formulario cuando se edita/agrega una {@link Asesoria}
     * 
     * @param id            identificador numérico de la {@link Asesoria}
     * @param asesoria      objeto {@link Asesoria} con la información a
     *                      agregar/editar
     * @param bindingResult objeto {@link BindingResult} con los errores de
     *                      validación
     * @param model         objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = { "/{id:\\d+}/editar", "/agregar" })
    public String procesarFormulario(@PathVariable Optional<Integer> id, @Valid Asesoria asesoria,
            BindingResult bindingResult, Model model) {
        // Verificar si hay errores
        if (bindingResult.hasErrors()) {
            // Buscar todos los profesionales
            List<Profesional> profesionales = profesionalesRepositorio.findAll(Sort.by("usuario.nombre"));

            // Buscar todos los clientes
            List<Cliente> clientes = clientesRepositorio.findAll(Sort.by("usuario.nombre", "rut"));

            // Agregar objetos al modelo
            model.addAttribute("profesionales", profesionales);
            model.addAttribute("clientes", clientes);

            // Mostrar formulario
            return "asesorias/formulario";
        }

        // Guardar asesoría
        asesoria = asesoriasRepositorio.save(asesoria);

        // Depuración
        if (id.isPresent())
            logger.info("[WEB] Se editó una Asesoría: /asesorias/{}", asesoria.getId());
        else
            logger.info("[WEB] Se agregó una nueva Asesoría: /asesorias/{}", asesoria.getId());

        // Redireccionar
        return "redirect:/asesorias/" + asesoria.getId();
    }

    /**
     * Elimina una {@link Asesoria} del repositorio
     * 
     * @param id identificador numérico de la {@link Asesoria}
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = "/{id:\\d+}/eliminar")
    public String eliminarAsesoria(@PathVariable int id) {
        // Obtener información de la asesoría
        Optional<Asesoria> asesoria = asesoriasRepositorio.findById(id);

        // Verificar si existe
        if (asesoria.isPresent()) {
            // Depuración
            logger.info("[WEB] Se ha eliminado una asesoría: {}", asesoria.get());

            // Eliminar la asesoría
            asesoriasRepositorio.delete(asesoria.get());

            // Redireccionar
            return "redirect:/asesorias?remid=" + id;
        }

        // Redireccionar
        return "redirect:/asesorias?noid=" + id;
    }

}
