package cl.leid.detta.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.repositorios.AccionesRepository;

@RestController
@RequestMapping(path = "/api/acciones")
public class AccionesController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link AccidentesRepository} con los métodos para la manipulación de
     * la base de datos
     */
    @Autowired
    private AccionesRepository accionesRepository;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muesta el listado de {@link Accion}es
     * 
     * @return un objeto {@link List} con el resultado
     */
    @GetMapping
    public List<Accion> getAllAcciones() {
        // Devolver el listado de objetos
        return accionesRepository.findAll();
    }

    /**
     * Muestra el detalle de una acción en particular
     * 
     * @param id identificador numérico de la {@link Accion}
     * @return un objeto {@link Accion} con el resultado, {@code null} en cualquier
     *         otro caso
     */
    @GetMapping(path = "/{id:^[0-9]+$}")
    public Accion getAccionById(@PathVariable int id) {
        // Buscar la acción en el repositorio
        Optional<Accion> accion = accionesRepository.findById(id);

        // Devolver si existe, null si no
        return accion.isPresent() ? accion.get() : null;
    }

}
