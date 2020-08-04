package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.leid.detta.api.exceptions.ConflictException;
import cl.leid.detta.api.exceptions.EmptyRepositoryException;
import cl.leid.detta.api.exceptions.InformationNotFoundException;
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

    /**
     * Muestra los detalles de una {@link Capacitacion}
     * 
     * @param id     identificador numérico de la {@link Capacitacion}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<Capacitacion> verDetalles(@PathVariable int id, Locale locale) {
        // Buscar información de la Capacitación
        Capacitacion capacitacion = capacitacionesRepositorio.findById(id)
                .orElseThrow(() -> new InformationNotFoundException(
                        messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.OK).body(capacitacion);
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo registro al repositorio
     * 
     * @param capacitacion objeto {@link Capacitacion} con la información a agregar
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PostMapping
    public ResponseEntity<Capacitacion> agregarRegistro(@RequestBody @Valid Capacitacion capacitacion) {
        // Agregar registro al repositorio
        capacitacion = capacitacionesRepositorio.save(capacitacion);

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(capacitacion);
    }

    // Solicitudes PUT
    // -----------------------------------------------------------------------------------------

    /**
     * Edita la información de un registro
     * 
     * @param id           identificador numérico de la {@link Capacitacion}
     * @param capacitacion objeto {@link Capacitacion} con la información a editar
     * @param locale       objeto {@link Locale} con la información regional del
     *                     cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PutMapping(path = "/{id:\\d+}")
    public ResponseEntity<Capacitacion> editarRegistro(@PathVariable int id,
            @RequestBody @Valid Capacitacion capacitacion, Locale locale) {
        // Buscar información de la capacitación
        Capacitacion aux = capacitacionesRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Verificar si corresponse
        if (aux.getId() == capacitacion.getId()) {
            // Guardar cambios
            capacitacion = capacitacionesRepositorio.save(capacitacion);

            // Crear respuesta
            return ResponseEntity.ok(capacitacion);
        }

        // Lanzar excepción
        throw new ConflictException(
                messageSource.getMessage("api.conflict", new Object[] { id, capacitacion.getId() }, locale), "id", id);
    }

    // Solicitudes DELETE
    // -----------------------------------------------------------------------------------------

    /**
     * Elimina un registro del repositorio
     * 
     * @param id     identificador numérico de la {@link Capacitacion}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @DeleteMapping(path = "/{id:\\d+}")
    public ResponseEntity<Capacitacion> eliminarRegistro(@PathVariable int id, Locale locale) {
        // Buscar información de la capacitación
        Capacitacion aux = capacitacionesRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Eliminar registro
        capacitacionesRepositorio.deleteById(id);

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(aux);
    }

}
