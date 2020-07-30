package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.leid.detta.modelos.Asesoria;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;

public interface AsesoriasRepositorio extends JpaRepository<Asesoria, Integer> {

    public List<Asesoria> findByCliente(Cliente cliente);
    public List<Asesoria> findByCliente(Cliente cliente, Sort sort);
    
    public List<Asesoria> findByProfesional(Profesional profesional);
    public List<Asesoria> findByProfesional(Profesional profesional, Sort sort);
    
}
