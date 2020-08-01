package cl.leid.detta.controladores;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
                if (profesional.isEmpty()) {
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
                if (cliente.isEmpty()) {
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

}
