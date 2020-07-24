package cl.leid.detta.repositorios.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cl.leid.detta.modelos.Usuario;

public class UsuarioRowMapper implements RowMapper<Usuario> {

    /**
     * Extrae un {@link Usuario} de un {@link ResultSet}
     * 
     * @param rs     objeto {@link ResultSet} con la información a extraer
     * @param rowNum el número de la fila actual
     * @return un objeto {@link Usuario}
     * @throws SQLException si ocurre una excepción cuando se intentan obtener los
     *                      valores de las columnas
     */
    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Usuario(rs.getInt("usuario_id"), rs.getNString("email"), rs.getNString("password"),
                rs.getNString("role"), rs.getBoolean("enabled"));
    }

}
