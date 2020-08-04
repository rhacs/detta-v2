package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.leid.detta.api.exceptions.EmptyRepositoryException;
import cl.leid.detta.api.exceptions.InformationNotFoundException;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.repositorios.AccionesRepositorio;

@RestController
@RequestMapping(path = "/acciones")
public class AccionesRestController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Accion}es
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping
    public ResponseEntity<List<Accion>> verListado(Locale locale) {
        // Buscar listado de Acciones
        List<Accion> acciones = accionesRepositorio.findAll(Sort.by(Direction.DESC, "timestamp"));

        // Verificar si hay resultados
        if (!acciones.isEmpty()) {
            // Crear y mostrar respuesta
            return ResponseEntity.status(HttpStatus.OK).body(acciones);
        }

        // Lanzar excepción
        throw new EmptyRepositoryException(messageSource.getMessage("api.emptyrepo", null, locale));
    }

    /**
     * Muestra los detalles de una {@link Accion}
     * 
     * @param id     identificador numérico de la {@link Accion}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<Accion> verDetalles(@PathVariable int id, Locale locale) {
        // Obtener información de la acción
        Optional<Accion> accion = accionesRepositorio.findById(id);

        // Verificar si existe
        if (accion.isPresent()) {
            // Generar respuesta
            return ResponseEntity.status(HttpStatus.OK).body(accion.get());
        }

        // Lanzar excepción
        throw new InformationNotFoundException(messageSource.getMessage("api.notfound", new Object[] { id }, locale));
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva {@link Accion} en el repositorio
     * 
     * @param accion objeto {@link Accion} con la información a agregar
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PostMapping(path = "/new")
    public ResponseEntity<Accion> agregarRegistro(@RequestBody @Valid Accion accion, Locale locale) {
        // Guardar registro
        accion = accionesRepositorio.save(accion);

        // Generar respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(accion);
    }

}
