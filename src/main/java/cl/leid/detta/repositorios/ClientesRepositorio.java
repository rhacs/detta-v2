package cl.leid.detta.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Cliente;

@Repository
public interface ClientesRepositorio extends JpaRepository<Cliente, Integer> {

    /**
     * Busca un registro en el repositorio
     * 
     * @param rut rol único tributario
     * @return un objeto {@link Optional} con el resultado
     */
    public Optional<Cliente> findByRut(String rut);

}
