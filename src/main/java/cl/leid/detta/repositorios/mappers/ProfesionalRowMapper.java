package cl.leid.detta.repositorios.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cl.leid.detta.modelos.Profesional;

public class ProfesionalRowMapper implements RowMapper<Profesional> {

    /**
     * Extrae un {@link Profesional} de un {@link ResultSet}
     * 
     * @param rs     objeto {@link ResultSet} con la información a extraer
     * @param rowNum el número de la fila actual
     * @return un objeto {@link Profesional}
     * @throws SQLException si ocurre una excepción cuando se intentan obtener los
     *                      valores de las columnas
     */
    @Override
    public Profesional mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Crear objeto
        Profesional profesional = new Profesional();

        // Poblar objeto
        profesional.setId(rs.getInt("id"));
        profesional.setNombre(rs.getNString("nombre"));
        profesional.setEmail(rs.getNString("email"));
        profesional.setTelefono(rs.getNString("telefono"));
        profesional.setPassword(rs.getNString("password"));
        profesional.setRole(rs.getNString("role"));
        profesional.setEnabled(rs.getBoolean("enabled"));
        profesional.setUsuarioId(rs.getInt("usuario_id"));

        // Devolver objeto
        return profesional;
    }

}
