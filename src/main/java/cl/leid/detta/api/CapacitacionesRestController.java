package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.leid.detta.api.exceptions.EmptyRepositoryException;
import cl.leid.detta.modelos.Capacitacion;
import cl.leid.detta.repositorios.CapacitacionesRepositorio;

@RestController
@RequestMapping(path = "/capacitaciones")
public class CapacitacionesRestController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CapacitacionesRepositorio capacitacionesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Capacitacion}es
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping
    public ResponseEntity<List<Capacitacion>> verListado(Locale locale) {
        // Buscar listado de capacitaciones
        List<Capacitacion> capacitaciones = capacitacionesRepositorio.findAll(Sort.by(Direction.DESC, "fecha", "hora"));

        // Verificar si hay resultados
        if (capacitaciones.isEmpty()) {
            // Lanzar excepción
            throw new EmptyRepositoryException(messageSource.getMessage("api.emptyrepo", null, locale));
        }

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.OK).body(capacitaciones);
    }

}
