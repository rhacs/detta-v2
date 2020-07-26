package cl.leid.detta.repositorios;

import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.repositorios.mappers.AccidenteRowMapper;

public class AccidentesRepositorio {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Nombre de la tabla en base de datos */
    private static final String TABLA = "detta_accidentes";

    /** Nombre de la tabla que guarda la información de los {@link Cliente}s */
    private static final String TABLA_CLIENTES = "detta_clientes";

    /** Instrucción base para las consultas SELECT */
    private static final String BASE_SELECT = "SELECT a.accidente_id, a.fecha, a.hora, a.direccion, a.lugar, a.circunstancia, "
            + "a.detalles, a.clasificacion, a.tipo, a.evidencia, a.fecha_registro, a.cliente_id, c.nombre, c.profesional_id FROM "
            + TABLA + " a INNER JOIN " + TABLA_CLIENTES + " c ON a.cliente_id = c.cliente_id";

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
     * Crea una nueva instancia del objeto {@link AccidentesRepositorio}
     * 
     * @param jdbcTemplate objeto {@link JdbcTemplate} con los métodos para la
     *                     manipulación de la base de datos
     */
    public AccidentesRepositorio(JdbcTemplate jdbcTemplate) {
        super();
        this.jdbcTemplate = jdbcTemplate;
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo registro al repositorio
     * 
     * @param accidente objeto {@link Accidente} con la información a agregar
     * @return {@code true} si el registro fue agregado, {@code false} en cualquier
     *         otro caso
     */
    public boolean agregarRegistro(Accidente accidente) {
        // Definir consulta
        String sql = "INSERT INTO " + TABLA + " (fecha, hora, direccion, lugar, circunstancia, detalles, "
                + "clasificacion, tipo, evidencia, cliente_id) VALUES (?, ?, ?, ?, ?, ?, ? ,? ,?, ?)";

        return jdbcTemplate.update(sql, Date.valueOf(accidente.getFecha()),
                accidente.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), accidente.getDireccion(),
                accidente.getLugar(), accidente.getCircunstancia(), accidente.getDetalles(),
                accidente.getClasificacion(), accidente.getTipo(), accidente.getEvidencia(),
                accidente.getClienteId()) > 0;
    }

    /**
     * Busca un registro en el repositorio
     * 
     * @param id identificador numérico del {@link Accidente}
     * @return un objeto {@link Accidente} con el resultado, {@code null} en
     *         cualquier otro caso
     */
    public Accidente buscarPorId(int id) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE a.accidente_id = ?";

        // Ejecutar consulta
        List<Accidente> accidentes = jdbcTemplate.query(sql, new Object[] { id }, new AccidenteRowMapper());

        return accidentes.isEmpty() ? null : accidentes.get(0);
    }

    /**
     * Busca todos los registros del repositorio
     * 
     * @return un objeto {@link List} con el resultado
     */
    public List<Accidente> buscarTodos() {
        // Definir consulta
        String sql = BASE_SELECT + " ORDER BY fecha DESC, hora DESC";

        return jdbcTemplate.query(sql, new AccidenteRowMapper());
    }

    /**
     * Busca todos los registros del repositorio relacionados con un {@link Cliente}
     * 
     * @param clienteId identificador numérico del {@link Cliente}
     * @return un objeto {@link List} con el resultado
     */
    public List<Accidente> buscarPorClienteId(int clienteId) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE a.cliente_id = ? ORDER BY fecha DESC, hora DESC";

        return jdbcTemplate.query(sql, new Object[] { clienteId }, new AccidenteRowMapper());
    }

    /**
     * Busca todos los registros del repositorio que están relacionados con un
     * {@link Profesional}
     * 
     * @param profesionalId identificador numérico del {@link Profesional}
     * @return un objeto {@link List} con el resultado
     */
    public List<Accidente> buscarPorProfesionalId(int profesionalId) {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE c.profesional_id = ? ORDER BY fecha DESC, hora DESC";

        return jdbcTemplate.query(sql, new Object[] { profesionalId }, new AccidenteRowMapper());
    }

    /**
     * Busca el último registro insertado en el repositorio
     * 
     * @return un objeto {@link Accidente}
     */
    public Accidente buscarUltimo() {
        // Definir consulta
        String sql = BASE_SELECT + " WHERE a.accidente_id = (SELECT max(accidente_id) FROM " + TABLA + ")";

        return jdbcTemplate.queryForObject(sql, new AccidenteRowMapper());
    }

    /**
     * Actualiza la información de un registro en el repositorio
     * 
     * @param accidente objeto {@link Accidente} con la información a actualizar
     * @return {@code true} si el registro fue actualizado, {@code false} en
     *         cualquier otro caso
     */
    public boolean actualizarRegistro(Accidente accidente) {
        // Definir consulta
        String sql = "UPDATE " + TABLA + " SET fecha = ?, hora = ?, direccion = ?, lugar = ?, circunstancia = ?, "
                + "detalles = ?, clasificacion = ?, tipo = ? evidencia = ?, cliente_id = ? WHERE accidente_id = ?";

        return jdbcTemplate.update(sql, Date.valueOf(accidente.getFecha()),
                accidente.getHora().format(DateTimeFormatter.ofPattern("HH:mm")), accidente.getDireccion(),
                accidente.getLugar(), accidente.getCircunstancia(), accidente.getDetalles(),
                accidente.getClasificacion(), accidente.getTipo(), accidente.getEvidencia(), accidente.getClienteId(),
                accidente.getId()) > 0;
    }

    /**
     * Elimina un registro del repositorio
     * 
     * @param id identificador numérico del {@link Accidente}
     * @return {@code true} si el objeto fue eliminado, {@code false} en cualquier
     *         otro caso
     */
    public boolean eliminarRegistro(int id) {
        // Definir consulta
        String sql = "DELETE FROM " + TABLA + " WHERE accidente_id = ?";

        return jdbcTemplate.update(sql, id) > 0;
    }

}
