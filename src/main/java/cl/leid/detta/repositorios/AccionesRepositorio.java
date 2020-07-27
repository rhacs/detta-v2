package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Usuario;

@Repository
public interface AccionesRepositorio extends JpaRepository<Accion, Integer> {

    /**
     * Busca todos los registros del repositorio que estén relacionados con el
     * {@link Usuario}
     * 
     * @param usuario {@link Usuario} a buscar
     * @return un objeto {@link List} con los resultados
     */
    public List<Accion> findByUsuario(Usuario usuario);

    /**
     * Busca todos los registros del repositorio que tengan la categoría
     * especificada
     * 
     * @param categoria la {@link Accion#categoria} a buscar
     * @return un objeto {@link List} con los resultados
     */
    public List<Accion> findByCategoria(int categoria);

}
