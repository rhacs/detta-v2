package cl.leid.detta.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;

@Repository
public interface ProfesionalesRepositorio extends JpaRepository<Profesional, Integer> {

    /**
     * Busca un registro en el repositorio
     * 
     * @param usuario {@link Usuario}
     * @return un objeto {@link Optional} con el resultado
     */
    public Optional<Profesional> findByUsuario(Usuario usuario);

}
