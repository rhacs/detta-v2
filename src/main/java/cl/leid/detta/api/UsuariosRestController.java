package cl.leid.detta.api;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    /**
     * Agrega un nuevo registro de {@link Usuario} al repositorio
     * 
     * @param usuario objeto {@link Usuario} con la información a agregar
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PostMapping(path = "/new")
    public ResponseEntity<Usuario> agregarRegistro(@RequestBody @Valid Usuario usuario) {
        // Codificar contraseña
        usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));

        // Guardar registro en el repositorio
        Usuario savedUsuario = usuariosRepositorio.save(usuario);

        // Depuración
        logger.info("[API] Se creó un nuevo Usuario: {}", savedUsuario);

        // Crear y mostrar respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUsuario);
    }

    // Solicitudes PUT
    // -----------------------------------------------------------------------------------------

    /**
     * Edita la información de un {@link Usuario}
     * 
     * @param usuario objeto {@link Usuario} con la información a editar
     * @param id      identificador numérico del {@link Usuario}
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @PutMapping(path = "/{id:\\d+}/edit")
    public ResponseEntity<Usuario> editarRegistro(@RequestBody @Valid Usuario usuario, @PathVariable int id,
            Locale locale) {
        // Buscar información del Usuario
        Optional<Usuario> aux = usuariosRepositorio.findById(id);

        // Verificar si existe
        if (aux.isPresent() && aux.get().getId() == usuario.getId()) {
            // Actualizar información del registro
            usuario = usuariosRepositorio.save(usuario);

            // Depuración
            logger.info("[API] Información del Usuario actualizada: {}", usuario);

            // Crear y devolver respuesta
            return ResponseEntity.ok(usuario);
        }

        // Lanzar excepción
        throw new InformationNotFoundException(messageSource.getMessage("api.notfound", new Object[] { id }, locale));
    }

    // Solicitudes DELETE
    // -----------------------------------------------------------------------------------------

    /**
     * Elimina un registro del repositorio
     * 
     * @param id     identificador numérico del {@link Usuario}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ResponseEntity} con la respuesta a la solicitud
     */
    @DeleteMapping(path = "/{id:\\d+}/del")
    public ResponseEntity<Usuario> eliminarRegistro(@PathVariable int id, Locale locale) {
        // Buscar información del registro
        Optional<Usuario> usuario = usuariosRepositorio.findById(id);

        // Verificar si existe
        if (usuario.isPresent()) {
            // Eliminar registro
            usuariosRepositorio.delete(usuario.get());

            // Depuración
            logger.info("[API] Usuario eliminado: {}", usuario.get());

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(usuario.get());
        }

        // Lanzar excepción
        throw new InformationNotFoundException(messageSource.getMessage("api.notfound", new Object[] { id }, locale));
    }

}
