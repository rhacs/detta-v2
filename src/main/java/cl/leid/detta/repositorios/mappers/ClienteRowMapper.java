package cl.leid.detta.repositorios.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import cl.leid.detta.modelos.Cliente;

public class ClienteRowMapper implements RowMapper<Cliente> {

    /**
     * Extrae un {@link Cliente} de un {@link ResultSet}
     * 
     * @param rs     objeto {@link ResultSet} con la información a extraer
     * @param rowNum el número de la fila actual
     * @return un objeto {@link Cliente}
     * @throws SQLException si ocurre una excepción cuando se intentan obtener los
     *                      valores de las columnas
     */
    @Override
    public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
        // Crear objeto
        Cliente cliente = new Cliente();

        // Poblar cliente
        cliente.setId(rs.getInt("cliente_id"));
        cliente.setNombre(rs.getNString("nombre"));
        cliente.setRut(rs.getNString("rut"));
        cliente.setEmail(rs.getNString("email"));
        cliente.setTelefono(rs.getNString("telefono"));
        cliente.setGiro(rs.getNString("giro"));
        cliente.setEmpleados(rs.getInt("empleados"));
        cliente.setPassword(rs.getNString("password"));
        cliente.setEnabled(rs.getBoolean("enabled"));
        cliente.setRole(rs.getNString("role"));
        cliente.setUsuarioId(rs.getInt("usuario_id"));
        cliente.setProfesionalId(rs.getInt("profesional_id"));

        // Devolver objeto
        return cliente;
    }

}
