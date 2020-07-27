package cl.leid.detta.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;

@Repository
public interface ClientesRepositorio extends JpaRepository<Cliente, Integer> {

    /**
     * Busca un registro en el repositorio
     * 
     * @param rut rol único tributario
     * @return un objeto {@link Optional} con el resultado
     */
    public Optional<Cliente> findByRut(String rut);

    /**
     * Busca todos los registros en el repositorio que coincidan con el
     * {@link Profesional} proporcionado
     * 
     * @param profesional objeto {@link Profesional} a buscar
     * @return un objeto {@link List} con los resultados
     */
    public List<Cliente> findByProfesional(Profesional profesional);

    /**
     * Busca un registro en el repositorio
     * 
     * @param usuario objeto {@link Usuario} relacionado con el {@link Cliente}
     * @return un objeto {@link Optional} con el resultado
     */
    public Optional<Cliente> findByUsuario(Usuario usuario);

}
