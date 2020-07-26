package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Accion;

@Repository
public interface AccionesRepository extends JpaRepository<Accion, Integer> {

    /**
     * Busca todos los registros del repositorio que coinciden con la dirección de
     * correo electrónico proporcinada
     * 
     * @param email dirección de correo electrónico
     * @return un objeto {@link List} con el resultado
     */
    public List<Accion> findByEmail(String email);

    /**
     * Busca todos los registros del repositorio que coinciden con la categoría
     * especificada
     * 
     * @param categoria categoría de la {@link Accion}
     * @return un objeto {@link List} con el resultado
     */
    public List<Accion> findByCategoria(int categoria);

}
