package cl.leid.detta.controladores;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.Constantes;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Rol;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
@RequestMapping(path = "/profesionales")
public class ProfesionalesController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(ProfesionalesController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link MessageSource} con lo métodos de parametrización e
     * internacionalización de los mensajes
     */
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra el listado de {@link Profesional}es
     * 
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping
    public ModelAndView mostrarListado(Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales");

        // Buscar listado de profesionales
        List<Profesional> profesionales = profesionalesRepositorio.findAll(Sort.by(Sort.Direction.ASC, "nombre"));

        // Agregar listado a la vista
        vista.addObject("profesionales", profesionales);

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        return vista;
    }

    /**
     * Muestra los detalles del {@link Profesional} seleccionado
     * 
     * @param id     identificador numérico del {@link Profesional}
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = "/{id}")
    public ModelAndView mostrarDetalles(@PathVariable int id, Locale locale) {
        // Buscar el profesional
        Optional<Profesional> profesional = profesionalesRepositorio.findById(id);

        // Verificar si existe
        if (profesional.isPresent()) {
            // Crear vista
            ModelAndView vista = new ModelAndView("profesionales/detalles");

            // Agregar profesional a la vista
            vista.addObject("profesional", profesional.get());

            // Buscar listado de clientes
            List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get());

            // Agregar listado a la vista
            vista.addObject("clientes", clientes);

            // Agregar título
            vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

            return vista;
        }

        // Depuración
        logger.info("{} solicitó la información de un profesional que no existe: {}",
                SecurityContextHolder.getContext().getAuthentication().getName(), id);

        return new ModelAndView("redirect:/profesionales", "noid", id);
    }

    /**
     * Muestra el formulario de edición/inserción de un {@link Profesional}
     * 
     * @param id     identificador numérico del {@link Profesional} (opcional, puede
     *               ser {@code null})
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta
     */
    @GetMapping(path = { "/{id}/editar", "/agregar" })
    public ModelAndView mostrarFormulario(@PathVariable Optional<Integer> id, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("profesionales/formulario");

        // Verificar si es una edición
        if (id.isPresent()) {
            // Buscar registro
            Optional<Profesional> profesional = profesionalesRepositorio.findById(id.get());

            // Verificar si existe
            if (profesional.isPresent()) {
                // Agregar a la vista
                vista.addObject("profesional", profesional.get());

                // Agregar acción a la vista
                vista.addObject("accion", "editar");
            } else {
                // Depuración
                logger.error("{} intentó editar la información de un Profesional que no existe: {}",
                        SecurityContextHolder.getContext().getAuthentication().getName(), id.get());

                // Redireccionar al usuario si el registro no es encontrado
                return new ModelAndView("profesionales", "noid", id.get());
            }
        } else {
            // Agregar nuevo profesional a la vista
            vista.addObject("profesional", new Profesional());

            // Agregar acción
            vista.addObject("accion", "agregar");
        }

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.professionals", null, locale));

        // Devolver vista
        return vista;
    }

    // Solicitudes POST
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo {@link Profesional} al repositorio
     * 
     * @param profesional   objeto {@link Profesional} enviado por el cliente
     * @param bindingResult objeto {@link BindingResult} con los errores de
     *                      validación
     * @param locale        objeto {@link Locale} con la información regional del
     *                      cliente
     * @param model         objeto {@link Model}
     * @return un objeto {@link String} con la respuesta al cliente
     */
    @PostMapping(path = "/agregar")
    public String agregarProfesional(@Valid Profesional profesional, BindingResult bindingResult, Locale locale,
            Model model) {
        // Verificar que no hayan errores
        if (bindingResult.hasErrors()) {
            return "profesionales/formulario";
        } else {
            // Buscar correo electrónico en el sistema
            Optional<Usuario> existente = usuariosRepositorio.findByEmail(profesional.getUsuario().getEmail());

            // Verificar que no exista
            if (existente.isPresent()) {
                model.addAttribute("error", messageSource.getMessage("form.error.used_email",
                        new Object[] { profesional.getUsuario().getEmail() }, locale));

                return "profesionales/formulario";
            }

            // Rellenar campos faltantes
            Usuario usuario = profesional.getUsuario();
            usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getEmail()));

            Rol rol = usuario.getRol();
            rol.setRole(Constantes.ROLE_STAFF);
            rol.setUsuario(usuario);
            usuario.setRol(rol);

            // Guardar profesional
            profesional.setUsuario(usuario);
            profesional = profesionalesRepositorio.save(profesional);

            // Redireccionar
            return "redirect:/profesionales/" + profesional.getId();
        }
    }

    /**
     * Edita la información de un {@link Profesional}
     * 
     * @param idnt          identificador numérico del {@link Profesional}
     * @param profesional   objeto {@link Profesional} con los datos a editar
     * @param bindingResult objeto {@link BindingResult} con los errores de
     *                      validación
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = "/{idnt}/editar")
    public String editarRegistro(@PathVariable int idnt, @Valid Profesional profesional, BindingResult bindingResult) {
        // Verificar si hay errores
        if (bindingResult.hasErrors()) {
            // Mostrar errores
            return "profesionales/formulario";
        }

        // Buscar información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(profesional.getUsuario().getEmail());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Agregar datos faltantes
            profesional.setUsuario(usuario.get());

            // Guardar cambios
            profesionalesRepositorio.save(profesional);

            return "redirect:/profesionales/" + profesional.getId();
        }

        // Redireccionar
        return "redirect:/profesionales?noid=" + idnt;
    }

    /**
     * Elimina un registro del repositorio
     * 
     * @param id     identificador numérico del {@link Profesional}
     * @param auth   objeto {@link Authentication} con la información del
     *               {@link Usuario} autenticado
     * @param locale objeto {@link Locale} con la información regional del cliente
     * @return objeto {@link String} con la respuesta a la solicitud
     */
    @PostMapping(path = "/{id}/eliminar")
    public String eliminarRegistro(@PathVariable int id, Authentication auth, Locale locale) {
        // Obtener información del Profesional
        Optional<Profesional> profesional = profesionalesRepositorio.findById(id);

        // Verificar si existe
        if (profesional.isPresent()) {
            // Buscar usuario
            Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

            // Verificar si existe
            if (usuario.isPresent()) {
                // Crear registro
                Accion accion = new Accion("Eliminó un profesional: " + profesional.get().getUsuario().getEmail(), 2,
                        usuario.get());

                // Guardar registro
                accionesRepositorio.save(accion);
            }

            // Eliminar registro
            profesionalesRepositorio.delete(profesional.get());

            // Redireccionar
            return "redirect:/profesionales?remid=" + id;
        }

        // Redireccionar
        return "redirect:/profesionales?noid=" + id;
    }

}
