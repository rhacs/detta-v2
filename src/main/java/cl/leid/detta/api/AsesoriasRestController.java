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
import cl.leid.detta.modelos.Asesoria;
import cl.leid.detta.repositorios.AsesoriasRepositorio;

@RestController
@RequestMapping(path = "/asesorias")
public class AsesoriasRestController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private AsesoriasRepositorio asesoriasRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Asesoria}s
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping
    public ResponseEntity<List<Asesoria>> verListado(Locale locale) {
        // Buscar listado de asesorías
        List<Asesoria> asesorias = asesoriasRepositorio.findAll(Sort.by(Direction.DESC, "fecha", "hora"));

        // Verificar si tiene elementos
        if (!asesorias.isEmpty()) {
            // Crear respuesta
            return ResponseEntity.status(HttpStatus.OK).body(asesorias);
        }

        // Lanzar excepción
        throw new EmptyRepositoryException(messageSource.getMessage("api.emptyrepo", null, locale));
    }

    /**
     * Muestra la información de la {@link Asesoria} seleccionada
     * 
     * @param id     identificador numérico del a {@link Asesoria}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<Asesoria> verDetalles(@PathVariable int id, Locale locale) {
        // Buscar información de la asesoría
        Asesoria asesoria = asesoriasRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.OK).body(asesoria);
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo registro al repositorio
     * 
     * @param asesoria objeto {@link Asesoria} con la información a agregar
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PostMapping
    public ResponseEntity<Asesoria> agregarRegistro(@RequestBody @Valid Asesoria asesoria) {
        // Agregar registro al repositorio
        asesoria = asesoriasRepositorio.save(asesoria);

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(asesoria);
    }

    // Solicitudes PUT
    // -----------------------------------------------------------------------------------------

    /**
     * Edita la información de un registro en el repositorio
     * 
     * @param id       identificador numérico de la {@link Asesoria}
     * @param asesoria objeto {@link Asesoria} con la información a actualizar
     * @param locale   objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PutMapping(path = "/{id:\\d+}")
    public ResponseEntity<Asesoria> editarRegistro(@PathVariable int id, @RequestBody @Valid Asesoria asesoria,
            Locale locale) {
        // Buscar información de la asesoría
        Asesoria aux = asesoriasRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Verificar si corresponde
        if (aux.getId() == asesoria.getId()) {
            // Guardar cambios
            asesoria = asesoriasRepositorio.save(asesoria);

            // Crear respuesta
            return ResponseEntity.status(HttpStatus.OK).body(asesoria);
        }

        // Lanzar excepción
        throw new ConflictException(
                messageSource.getMessage("api.conflict", new Object[] { id, asesoria.getId() }, locale), "id", id);
    }

    // Solicitudes DELETE
    // -----------------------------------------------------------------------------------------

    /**
     * Elimina un registro del repositorio
     * 
     * @param id     identificador numérico de la {@link Asesoria}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta
     */
    @DeleteMapping(path = "/{id:\\d+}")
    public ResponseEntity<Asesoria> eliminarRegistro(@PathVariable int id, Locale locale) {
        // Buscar información de la asesoría
        Asesoria asesoria = asesoriasRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Eliminar registro
        asesoriasRepositorio.delete(asesoria);

        // Crear respuesta
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(asesoria);
    }

}
