package cl.leid.detta.repositorios.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cl.leid.detta.modelos.Accion;

public class AccionRowMapper implements RowMapper<Accion> {

    /**
     * Extrae una {@link Accion} de un {@link ResultSet}
     * 
     * @param rs     objeto {@link ResultSet} con la información a extraer
     * @param rowNum el número de la fila actual
     * @return un objeto {@link Accion}
     * @throws SQLException si ocurre una excepción cuando se intentan obtener los
     *                      valores de las columnas
     */
    @Override
    public Accion mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Accion(rs.getInt("accion_id"), rs.getNString("email"), rs.getNString("detalles"),
                rs.getInt("categoria"), rs.getTimestamp("timestamp"));
    }

}
