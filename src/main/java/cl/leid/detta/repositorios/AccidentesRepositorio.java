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

    /** Instrucción base para las consultas SELECT */
    private static final String BASE_SELECT = "SELECT accidente_id, fecha, hora, direccion, lugar, circunstancia, "
            + "detalles, clasificacion, tipo, evidencia, fecha_registro, cliente_id FROM " + TABLA;

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
        String sql = BASE_SELECT + " WHERE accidente_id = ?";

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

}
