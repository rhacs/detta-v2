package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.mappers.UsuarioRowMapper;

public class UsuariosRepositorio {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Nombre de la tabla en la base de datos */
    private static final String TABLA = "detta_usuarios";

    /**
     * Nombre de la tabla que contiene la información de roles en la base de datos
     */
    private static final String TABLA_ROLES = "detta_usuarios_roles";

    /** Consulta base para las consultas SELECT */
    private static final String BASE_SELECT = "SELECT u.usuario_id, u.email, u.password, r.role, u.enabled FROM "
            + TABLA + " u INNER JOIN " + TABLA_ROLES + " r ON u.email = r.email";

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link JdbcTemplate} con los métodos para la manipulación de la base
     * de datos
     */
    private JdbcTemplate jdbcTemplate;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia del objeto {@link UsuariosRepositorio}
     * 
     * @param jdbcTemplate objeto {@link JdbcTemplate} con los métodos para la
     *                     manipulación de la base de datos
     */
    public UsuariosRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * Busca un registro en el repositorio
     * 
     * @param email dirección de correo electrónico
     * @return un objeto {@link Usuario} con el resultado, {@code null} en cualquier
     *         otro caso
     */
    public Usuario buscarPorEmail(String email) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE u.email = ?";

        // Buscar registros
        List<Usuario> usuarios = jdbcTemplate.query(sql, new Object[] { email }, new UsuarioRowMapper());

        // Devolver resultados
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

}
