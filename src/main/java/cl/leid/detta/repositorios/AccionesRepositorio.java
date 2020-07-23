package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.repositorios.mappers.AccionRowMapper;

public class AccionesRepositorio {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Nombre de la tabla en la base de datos */
    private final String TABLA = "detta_acciones";

    private final String BASE_SELECT = "SELECT accion_id, email, detalles, categoria, timestamp FROM " + TABLA;
    
    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link JdbcTemplate} con los métodos de manipulación de la base de
     * datos
     */
    private JdbcTemplate jdbcTemplate;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia del objeto {@link AccionesRepositorio}
     * 
     * @param jdbcTemplate objeto {@link JdbcTemplate} con los métodos de
     *                     manipulación de la base de datos
     */
    public AccionesRepositorio(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo registro al repositorio
     * 
     * @param accion objeto {@link Accion} con la información a agregar
     * @return {@code true} si el registro fue agregado, {@code false} en cualquier
     *         otro caso
     */
    public boolean agregarRegistro(Accion accion) {
        // Definir consulta
        String sql = "INSERT INTO " + TABLA + " (email, detalles, categoria) VALUES (?, ?, ?)";

        return jdbcTemplate.update(sql,
                new Object[] { accion.getEmail(), accion.getDetalles(), accion.getCategoria() }) > 0;
    }

    /**
     * Busca todos los registros del repositorio
     * 
     * @return un objeto {@link List} con el resultado
     */
    public List<Accion> buscarTodos() {
        // Definir consulta
        String sql = BASE_SELECT + " ORDER BY timestamp DESC";

        return jdbcTemplate.query(sql, new AccionRowMapper());
    }

}
