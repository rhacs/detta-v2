package cl.leid.detta.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;

public interface ProfesionalesRepositorio extends JpaRepository<Profesional, Integer> {

    /**
     * Busca un registro en el repositorio
     * 
     * @param usuario {@link Usuario}
     * @return un objeto {@link Optional} con el resultado
     */
    public Optional<Profesional> findByUsuario(Usuario usuario);

}
