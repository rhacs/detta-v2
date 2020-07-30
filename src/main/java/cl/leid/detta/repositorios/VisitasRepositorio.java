package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.leid.detta.modelos.Asesoria;
import cl.leid.detta.modelos.Visita;

public interface VisitasRepositorio extends JpaRepository<Visita, Integer> {
    
    public List<Asesoria> findByAsesoria(Asesoria asesoria);
    public List<Asesoria> findByAsesoria(Asesoria asesoria, Sort sort);

}
