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
            + "c.giro, c.empleados, c.tipo, u.password, u.enabled, r.role, c.usuario_id, c.profesional_id FROM " + TABLA
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
     * Agrega un nuevo registro al repositorio
     * 
     * @param cliente objeto {@link Cliente} con la información a agregar
     * @return {@code true} si el registro fue agregado, {@code false} en cualquier
     *         otro caso
     */
    public boolean agregarRegistro(Cliente cliente) {
        // Definir consulta (que asco de consulta FIXME!!!)
        String sql = "DECLARE\n\ttmp_id NUMERIC;\n\ttmp_email NVARCHAR2(150);\nBEGIN\n" + "\tINSERT INTO "
                + TABLA_USUARIOS + " (email, password, enabled) VALUES (?, ?, ?) RETURNING usuario_id, email "
                + "INTO tmp_id, tmp_email;\n\tINSERT INTO " + TABLA_ROLES + " (email, role) VALUES (tmp_email, "
                + "'ROLE_CLIENT');\n" + "\tINSERT INTO " + TABLA + " (nombre, rut, telefono, giro, empleados, "
                + "tipo, usuario_id, profesional_id) VALUES (?, ?, ?, ?, ?, ?, tmp_id, ?);\n\tCOMMIT;\nEND;";

        return jdbcTemplate.update(sql, cliente.getEmail(), cliente.getPassword(), cliente.isEnabled(),
                cliente.getNombre(), cliente.getRut(), cliente.getTelefono(), cliente.getGiro(), cliente.getEmpleados(),
                cliente.getTipo(), cliente.getProfesionalId()) > 0;
    }

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

    /**
     * Busca un registro en el repositorio
     * 
     * @param rut rol único tributario
     * @return un objeto {@link Cliente} con el resultado, {@code null} en cualquier
     *         otro caso
     */
    public Cliente buscarPorRut(String rut) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE c.rut = ?";

        // Ejecutar consulta
        List<Cliente> clientes = jdbcTemplate.query(sql, new Object[] { rut }, new ClienteRowMapper());

        return clientes.isEmpty() ? null : clientes.get(0);
    }

    /**
     * Actualiza la información de un registro en el repositorio
     * 
     * @param cliente objeto {@link Cliente} con la información a actualizar
     * @return {@code true} si el registro fue actualizado, {@code false} en
     *         cualquier otro caso
     */
    public boolean actualizarRegistro(Cliente cliente) {
        // Definir consulta
        String sql = "BEGIN\n\tUPDATE " + TABLA_USUARIOS + " SET password = ?, enabled = ? WHERE usuario_id = ?;\n"
                + "\tUPDATE " + TABLA + " SET nombre = ?, telefono = ?, giro = ?, empleados = ?, "
                + "tipo = ?, profesional_id = ? WHERE cliente_id = ?;\n\tCOMMIT;\nEND;";

        // Ejecutar y devolver resultado
        return jdbcTemplate.update(sql, cliente.getPassword(), cliente.isEnabled(), cliente.getUsuarioId(),
                cliente.getNombre(), cliente.getTelefono(), cliente.getGiro(), cliente.getEmpleados(),
                cliente.getTipo(), cliente.getProfesionalId(), cliente.getId()) > 0;
    }

}
