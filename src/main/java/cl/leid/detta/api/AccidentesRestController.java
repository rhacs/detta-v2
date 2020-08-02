package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.leid.detta.api.exceptions.EmptyRepositoryException;
import cl.leid.detta.api.exceptions.InformationNotFoundException;
import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.repositorios.AccidentesRepositorio;

@RestController
@RequestMapping(path = "/api/accidentes", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AccidentesRestController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(AccidentesRestController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AccidentesRepositorio accidentesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Accidente}s
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping
    public ResponseEntity<List<Accidente>> verListado(Locale locale) {
        // Buscar listado de Accidentes
        List<Accidente> accidentes = accidentesRepositorio.findAll(Sort.by(Direction.DESC, "fecha", "hora"));

        // Verificar si no tiene resultados
        if (accidentes.isEmpty()) {
            // Depuración
            logger.info("Repositorio de Accidentes vacío");

            // Lanzar excepción
            throw new EmptyRepositoryException(messageSource.getMessage("api.emptyrepo", null, locale));
        }

        // Crear y mostrar respuesta
        return ResponseEntity.ok(accidentes);
    }

    /**
     * Muestra el detalle del {@link Accidente} seleccionado
     * 
     * @param id     identificador numérico del {@link Accidente}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Accidente> verDetalles(@PathVariable int id, Locale locale) {
        // Buscar información del Accidente
        Accidente accidente = accidentesRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Depuración
        logger.info("[API] Mostrando detalles del Accidente: {}", accidente);

        // Crear y mostrar respuesta
        return ResponseEntity.ok(accidente);
    }

}
