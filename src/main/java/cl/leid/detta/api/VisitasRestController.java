package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
import cl.leid.detta.modelos.Asesoria;
import cl.leid.detta.modelos.Visita;
import cl.leid.detta.repositorios.AsesoriasRepositorio;
import cl.leid.detta.repositorios.VisitasRepositorio;

@RestController
@RequestMapping(path = "/api/asesorias/{as:\\d+}/visitas")
public class VisitasRestController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AsesoriasRepositorio asesoriasRepositorio;

    @Autowired
    private VisitasRepositorio visitasRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Visita}s asociadas con la {@link Asesoria}
     * 
     * @param as     identificador numérico de la {@link Asesoria}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping
    public ResponseEntity<List<Visita>> verListado(@PathVariable int as, Locale locale) {
        // Buscar información de la asesoría
        Asesoria asesoria = asesoriasRepositorio.findById(as).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { as }, locale)));

        // Buscar visitas de la asesoría
        List<Visita> visitas = visitasRepositorio.findByAsesoria(asesoria);

        // Verificar si hay registros
        if (visitas.isEmpty()) {
            throw new EmptyRepositoryException(messageSource.getMessage("api.emptyrepo", null, locale));
        }

        // Devolver listado
        return ResponseEntity.ok(visitas);
    }

    /**
     * Muestra el detalle de la {@link Visita} solicitada
     * 
     * @param as     identificador numérico de la {@link Asesoria}
     * @param id     identificador numérico de la {@link Visita}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<Visita> verDetalles(@PathVariable int as, @PathVariable int id, Locale locale) {
        // Buscar información de la asesoría
        Asesoria asesoria = asesoriasRepositorio.findById(as).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { as }, locale)));

        // Buscar información de la visita
        Visita visita = visitasRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Verificar si la visita no le pertenece a la asesoria
        if (!visita.getAsesoria().equals(asesoria)) {
            throw new ConflictException(
                    messageSource.getMessage("api.asesoria_visita", new Object[] { id, as }, locale), "id", id);
        }

        // Devolver objeto
        return ResponseEntity.ok(visita);
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo registro al repositorio
     * 
     * @param as     identificador numérico de la {@link Asesoria}
     * @param visita objeto {@link Visita} con la información a agregar
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PostMapping
    public ResponseEntity<Visita> agregarRegistro(@PathVariable int as, @RequestBody @Valid Visita visita) {
        // Agregar registro al repositorio
        visita = visitasRepositorio.save(visita);

        // Devolver respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(visita);
    }

    // Solicitudes PUT
    // -----------------------------------------------------------------------------------------

    /**
     * Edita la información de un registro en el repositorio
     * 
     * @param as     identificador numérico de la {@link Asesoria}
     * @param id     identificador numérico de la {@link Visita}
     * @param visita objeto {@link Visita} con los cambios a almacenar
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PutMapping(path = "/{id:\\d+}")
    public ResponseEntity<Visita> editarRegistro(@PathVariable int as, @PathVariable int id,
            @RequestBody @Valid Visita visita) {
        // Guardar cambios
        visita = visitasRepositorio.save(visita);

        // Devolver respuesta
        return ResponseEntity.status(HttpStatus.OK).body(visita);
    }

    // Solicitudes DELETE
    // -----------------------------------------------------------------------------------------

    /**
     * Elimina un registro del repositorio
     * 
     * @param as     identificador numérico de la {@link Asesoria}
     * @param id     identificador numérico de la {@link Visita}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @DeleteMapping(path = "/{id:\\d+}")
    public ResponseEntity<Visita> eliminarRegistro(@PathVariable int as, @PathVariable int id, Locale locale) {
        // Buscar información de la Visita
        Visita visita = visitasRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Eliminar registro
        visitasRepositorio.delete(visita);

        // Devolver respuesta
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(visita);
    }

}
