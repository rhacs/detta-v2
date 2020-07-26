package cl.leid.detta.handlers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.repositorios.AccionesRepositorio;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(CustomAccessDeniedHandler.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link JdbcTemplate} con los métodos para la manipulación de la base
     * de datos
     */
    private JdbcTemplate jdbcTemplate;

    // Herencias (AccessDeniedHandler)
    // -----------------------------------------------------------------------------------------

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Obtener información del usuario
        String usuario = request.getRemoteUser();
        String urlDenegada = request.getRequestURI();

        // Depuración
        logger.log(Level.ERROR, "[SEC] {} intentó acceder a {} sin tener los permisos necesarios", usuario,
                urlDenegada);

        // Inicializar repositorio
        AccionesRepositorio accionesRepositorio = new AccionesRepositorio(jdbcTemplate);

        // Crear nuevo registro
        Accion accion = new Accion(usuario, "Intentó acceder a la url: " + urlDenegada, 1);

        // Agregar al repositorio
        accionesRepositorio.agregarRegistro(accion);

        // Redireccionar a la página correspondiente
        response.sendRedirect("/detta/youShallNotPass");
    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return the jdbcTemplate
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    // Setters
    // -----------------------------------------------------------------------------------------

    /**
     * @param jdbcTemplate the jdbcTemplate to set
     */
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
