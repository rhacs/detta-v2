package cl.leid.detta.repositorios.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import cl.leid.detta.modelos.Accidente;

public class AccidenteRowMapper implements RowMapper<Accidente> {

    /**
     * Extrae un {@link Accidente} de un {@link ResultSet}
     * 
     * @param rs     objeto {@link ResultSet} con la información a extraer
     * @param rowNum el número de la fila actual
     * @return un objeto {@link Accidente} con el resultado
     * @throws SQLException si ocurre una excepción cuando se intenta obtener el
     *                      valor de una columna
     */
    @Override
    public Accidente mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Crear objeto
        Accidente accidente = new Accidente();

        // Poblar objeto
        accidente.setId(rs.getInt("accidente_id"));
        accidente.setFecha(rs.getDate("fecha").toLocalDate());
        accidente.setHora(LocalTime.parse(rs.getNString("hora"), DateTimeFormatter.ofPattern("HH:mm")));
        accidente.setDireccion(rs.getNString("direccion"));
        accidente.setLugar(rs.getNString("lugar"));
        accidente.setCircunstancia(rs.getNString("circunstancia"));
        accidente.setDetalles(rs.getNString("detalles"));
        accidente.setClasificacion(rs.getInt("clasificacion"));
        accidente.setTipo(rs.getInt("tipo"));
        accidente.setEvidencia(rs.getInt("evidencia"));
        accidente.setFechaRegistro(rs.getTimestamp("fecha_registro").toLocalDateTime());
        accidente.setClienteId(rs.getInt("cliente_id"));
        accidente.setClienteNombre(rs.getNString("nombre"));
        accidente.setProfesionalId(rs.getInt("profesional_id"));

        // Devolver objeto
        return accidente;
    }

}
