package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.repositorios.mappers.ProfesionalRowMapper;

public class ProfesionalesRepositorio {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Nombre de la tabla en la base de datos */
    private static final String TABLA = "detta_profesionales";

    /** Nombre de la tabla que tiene la información de los usuarios */
    private static final String TABLA_USUARIOS = "detta_usuarios";

    /** Nombre de la tabla que tiene la información de los roles de los usuarios */
    private static final String TABLA_ROLES = "detta_usuarios_roles";

    /** Consulta base para los SELECT */
    private static final String BASE_SELECT = "SELECT p.profesional_id, p.nombre, u.email, p.telefono, u.password, "
            + "r.role, u.enabled, p.usuario_id FROM " + TABLA + " p INNER JOIN " + TABLA_USUARIOS
            + " u ON p.usuario_id = u.usuario_id INNER " + "JOIN " + TABLA_ROLES + " r ON u.email = r.email";

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
     * Crea una nueva instancia del objeto {@link ProfesionalesRepositorio}
     * 
     * @param jdbcTemplate objeto {@link JdbcTemplate} con los métodos para la
     *                     manipulación de la base de datos
     */
    public ProfesionalesRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo registro al repositorio
     * 
     * @param profesional objeto {@link Profesional} con la información a agregar
     * @return {@code true} si el registro fue agregado, {@code false} en cualquier
     *         otro caso
     */
    public boolean agregarRegistro(Profesional profesional) {
        // Definir consulta
        String sql = "DECLARE\n\ttmp_id NUMBER;\n\ttmp_email NVARCHAR2(150);\nBEGIN\n\tINSERT INTO " + TABLA_USUARIOS
                + " (email, password, enabled) VALUES (?, ?, ?) RETURNING usuario_id, email INTO tmp_id, tmp_email;\n"
                + "\tINSERT INTO " + TABLA_ROLES + " (email, role) VALUES (tmp_email, 'ROLE_STAFF');\n"
                + "\tINSERT INTO " + TABLA + " (nombre, telefono, usuario_id) VALUES (?, ?, tmp_id);\n"
                + "\tCOMMIT;\nEND;";

        // Ejecutar consulta
        return jdbcTemplate.update(sql, profesional.getEmail(), profesional.getPassword(), profesional.isEnabled(),
                profesional.getNombre(), profesional.getTelefono()) > 0;
    }

    /**
     * Busca todos los registros del repositorio
     * 
     * @return un objeto {@link List} con los resultados
     */
    public List<Profesional> buscarTodos() {
        // Definir consulta
        String sql = BASE_SELECT + " ORDER BY p.nombre ASC";

        return jdbcTemplate.query(sql, new ProfesionalRowMapper());
    }

    /**
     * Busca un registro en el repositorio
     * 
     * @param id identificador numérico del {@link Profesional}
     * @return un objeto {@link Profesional} con el resultado, {@code null} en
     *         cualquier otro caso
     */
    public Profesional buscarPorId(int id) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE p.profesional_id = ?";

        // Buscar registros
        List<Profesional> profesionales = jdbcTemplate.query(sql, new Object[] { id }, new ProfesionalRowMapper());

        return profesionales.isEmpty() ? null : profesionales.get(0);
    }

    /**
     * Busca un registro en el repositorio
     * 
     * @param email dirección de correo electrónico del {@link Profesional}
     * @return un objeto {@link Profesional} con el resultado, {@code null} en
     *         cualquier otro caso
     */
    public Profesional buscarPorEmail(String email) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE u.email = ?";

        // Buscar registros
        List<Profesional> profesionales = jdbcTemplate.query(sql, new Object[] { email }, new ProfesionalRowMapper());

        return profesionales.isEmpty() ? null : profesionales.get(0);
    }

    /**
     * Actualiza la información de un registro en el repositorio
     * 
     * @param profesional objeto {@link Profesional} con la información a actualizar
     * @return {@code true} si el registro fue actualizado, {@code false} en
     *         cualquier otro caso
     */
    public boolean actualizarRegistro(Profesional profesional) {
        // Definir consulta
        String sql = "BEGIN\n\tUPDATE " + TABLA + " SET nombre = ?, telefono = ? WHERE profesional_id = ?;\n"
                + "\tUPDATE " + TABLA_USUARIOS + " SET password = ?, enabled = ? WHERE usuario_id = ?;"
                + "\n\tCOMMIT;\nEND;";

        // Ejecutar consulta y devolver resultado
        return jdbcTemplate.update(sql, profesional.getNombre(), profesional.getTelefono(), profesional.getId(),
                profesional.getPassword(), profesional.isEnabled(), profesional.getUsuarioId()) > 0;
    }

    /**
     * Elimina un registro del repositorio
     * 
     * @param profesional objeto {@link Profesional} con la información a eliminar
     * @return {@code true} si el registro fue eliminado, {@code false} en cualquier
     *         otro caso
     */
    public boolean eliminarRegistro(Profesional profesional) {
        // Definir consulta
        String sql = "BEGIN\n\tDELETE FROM " + TABLA + " WHERE profesional_id = ?;\n" + "\tDELETE FROM " + TABLA_ROLES
                + " WHERE email = ?;\n" + "\tDELETE FROM " + TABLA_USUARIOS + " WHERE usuario_id = ?;\n\tCOMMIT;\nEND;";

        // Ejecutar consulta y devolver resultado
        return jdbcTemplate.update(sql, profesional.getId(), profesional.getEmail(), profesional.getUsuarioId()) > 0;
    }

}
