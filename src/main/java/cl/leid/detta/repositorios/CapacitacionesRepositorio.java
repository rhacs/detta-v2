package cl.leid.detta.repositorios;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import cl.leid.detta.modelos.Capacitacion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;

public interface CapacitacionesRepositorio extends JpaRepository<Capacitacion, Integer> {

    public List<Capacitacion> findByCliente(Cliente cliente);
    public List<Capacitacion> findByCliente(Cliente cliente, Sort sort);
    public List<Capacitacion> findByProfesional(Profesional profesional);
    public List<Capacitacion> findByProfesional(Profesional profesional, Sort sort);
    
}
