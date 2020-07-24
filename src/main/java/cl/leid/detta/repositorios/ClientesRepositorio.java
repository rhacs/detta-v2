package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.repositorios.mappers.ClienteRowMapper;

public class ClientesRepositorio {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Nombre de la tabla en la base de datos */
    private static final String TABLA = "detta_clientes";

    /** Nombre de la tabla que tiene la información de los {@link Usuario}s */
    private static final String TABLA_USUARIOS = "detta_usuarios";

    /** Nombre de la tabla que tiene la información de los roles */
    private static final String TABLA_ROLES = "detta_usuarios_roles";

    /** Instrucción base para las consultas SELECT */
    private static final String BASE_SELECT = "SELECT c.cliente_id, c.nombre, c.rut, u.email, c.telefono, "
            + "c.giro, c.empleados, u.password, u.enabled, r.role, c.usuario_id, c.profesional_id FROM " + TABLA
            + " c INNER JOIN " + TABLA_USUARIOS + " u ON c.usuario_id = u.usuario_id INNER JOIN " + TABLA_ROLES
            + " r ON u.email = r.email";

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
     * Crea una nueva instancia del objeto {@link ClientesRepositorio}
     * 
     * @param jdbcTemplate objeto {@link JdbcTemplate} con los métodos para la
     *                     manipulación de la base de datos
     */
    public ClientesRepositorio(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * Busca todos los registros del repositorio
     * 
     * @return un objeto {@link List} con los resultados
     */
    public List<Cliente> buscarTodos() {
        // Definir consulta
        String sql = BASE_SELECT + " ORDER BY c.rut ASC";

        return jdbcTemplate.query(sql, new ClienteRowMapper());
    }

    /**
     * Busca un registro en el repositorio
     * 
     * @param id identificador numérico del {@link Cliente}
     * @return un objeto {@link Cliente} con el resultado, {@code null} en cualquier
     *         otro caso
     */
    public Cliente buscarPorId(int id) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE c.cliente_id = ?";

        // Ejecutar consulta
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] { id }, new ClienteRowMapper());

        return clientes.isEmpty() ? null : clientes.get(0);
    }

    /**
     * Busca un registro en el repositorio
     * 
     * @param email dirección de correo electrónico
     * @return un objeto {@link Cliente} con el resultado, {@code null} en cualquier
     *         otro caso
     */
    public Cliente buscarPorEmail(String email) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE u.email = ?";

        // Ejecutar consulta
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] { email }, new ClienteRowMapper());

        return clientes.isEmpty() ? null : clientes.get(0);
    }

    /**
     * Busca un listado de registros en el repositorio
     * 
     * @param profesionalId identificador numérico del {@link Profesional}
     * @return un objeto {@link List} con el resultado
     */
    public List<Cliente> buscarPorProfesionalId(int profesionalId) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE c.profesional_id = ?";

        return jdbcTemplate.query(sql, new Object[] { profesionalId }, new ClienteRowMapper());
    }

}
