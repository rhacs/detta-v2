package cl.leid.detta.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Usuario;

@Repository
public interface UsuariosRepositorio extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un registro en el repositorio
     * 
     * @param email dirección de correo electrónico
     * @return un objeto {@link Optional} con el resultado
     */
    public Optional<Usuario> findByEmail(String email);

}
