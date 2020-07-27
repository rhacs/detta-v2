package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.modelos.Cliente;

@Repository
public interface AccidentesRepositorio extends JpaRepository<Accidente, Integer> {

    /**
     * Busca todos los registros en el repositorio que estén relacionados con un
     * {@link Cliente}
     * 
     * @param cliente objeto {@link Cliente} a buscar
     * @return un objeto {@link List} con los resultados
     */
    public List<Accidente> findByCliente(Cliente cliente);

}
