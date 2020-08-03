package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.leid.detta.api.exceptions.EmptyRepositoryException;
import cl.leid.detta.api.exceptions.InformationNotFoundException;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@RestController
@RequestMapping(path = "/api/usuarios", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuariosRestController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(UsuariosRestController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Usuario}s
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> verListado(Locale locale) {
        // Obtener listado de Usuarios
        List<Usuario> usuarios = usuariosRepositorio.findAll(Sort.by("id"));

        // Verificar si no hay registros
        if (usuarios.isEmpty()) {
            // Depuración
            logger.info("Repositorio de Usuarios vacío");

            // Lanzar excepción
            throw new EmptyRepositoryException(messageSource.getMessage("api.emptyrepo", null, locale));
        }

        // Crear y mostrar respuesta
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Muestra el detalle del {@link Usuario} seleccionado
     * 
     * @param id     identificador numérico del {@link Usuario}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id:\\d+}")
    public ResponseEntity<Usuario> verDetalles(@PathVariable int id, Locale locale) {
        // Buscar información del usuario
        Usuario usuario = usuariosRepositorio.findById(id).orElseThrow(() -> new InformationNotFoundException(
                messageSource.getMessage("api.notfound", new Object[] { id }, locale)));

        // Depuración
        logger.info("[API] Mostrando información del Usuario: {}", usuario);

        // Crear y mostrar respuesta
        return ResponseEntity.ok(usuario);
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    @PostMapping
    public ResponseEntity<Usuario> agregarRegistro(@RequestBody @Valid Usuario usuario) {
        // Guardar registro en el repositorio
        Usuario savedUsuario = usuariosRepositorio.save(usuario);

        // Depuración
        logger.info("[API] Se creó un nuevo Usuario: {}", savedUsuario);

        // Crear y mostrar respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

}