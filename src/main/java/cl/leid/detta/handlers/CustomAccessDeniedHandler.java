package cl.leid.detta.handlers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(CustomAccessDeniedHandler.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    // Herencias (AccessDeniedHandler)
    // -----------------------------------------------------------------------------------------

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Depuración
        logger.log(Level.ERROR, "[SEC] {} intentó acceder a {} sin tener los permisos necesarios",
                request.getRemoteUser(), request.getRequestURI());

        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(request.getRemoteUser());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Registrar acción
            accionesRepositorio.save(new Accion("Intentó ingresar a: " + request.getRequestURI(), 1, usuario.get()));
        }

        // Redireccionar a la página correspondiente
        response.sendRedirect("/detta/youShallNotPass");
    }

}
