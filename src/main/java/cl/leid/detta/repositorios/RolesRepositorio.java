package cl.leid.detta.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.leid.detta.modelos.Rol;

@Repository
public interface RolesRepositorio extends JpaRepository<Rol, Integer> {

}
