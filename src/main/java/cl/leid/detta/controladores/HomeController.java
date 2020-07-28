package cl.leid.detta.controladores;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.Constantes;
import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.modelos.Usuario;
import cl.leid.detta.repositorios.AccidentesRepositorio;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;
import cl.leid.detta.repositorios.UsuariosRepositorio;

@Controller
public class HomeController {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Objeto {@link MessageSource} con lo métodos de parametrización e
     * internacionalización de los mensajes
     */
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UsuariosRepositorio usuariosRepositorio;

    @Autowired
    private AccionesRepositorio accionesRepositorio;

    @Autowired
    private AccidentesRepositorio accidentesRepositorio;

    @Autowired
    private ProfesionalesRepositorio profesionalesRepositorio;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    // Solicitudes GET
    // ----------------------------------------------------------------------------------------

    /**
     * Maneja las solicitudes que se le hacen a la raíz del sitio
     * 
     * @param request objeto {@link HttpServletRequest} con la información de la
     *                consulta que le hizo el cliente al {@link HttpServlet}
     * @param auth    objeto {@link Authentication} con la información del usuario
     *                autenticado
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta al cliente
     */
    @GetMapping(path = "/")
    public ModelAndView mostrarHome(HttpServletRequest request, Authentication auth, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("reportes");

        // Obtener información del usuario
        Optional<Usuario> usuario = usuariosRepositorio.findByEmail(auth.getName());

        // Verificar si existe
        if (usuario.isPresent()) {
            // Inicializar listado
            List<Accidente> accidentes = null;

            // Verificar autoridad
            if (request.isUserInRole(Constantes.ROLE_ADMIN)) {
                // Buscar todos los accidentes
                accidentes = accidentesRepositorio.findAll();

                // Buscar todas las acciones
                List<Accion> acciones = accionesRepositorio.findAll(Sort.by(Direction.DESC, "timestamp"));

                // Agregar a la vista
                vista.addObject("acciones", acciones);
            } else if (request.isUserInRole(Constantes.ROLE_STAFF)) {
                // Buscar información del profesional
                Optional<Profesional> profesional = profesionalesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (profesional.isPresent()) {
                    // Buscar los clientes del profesional
                    List<Cliente> clientes = clientesRepositorio.findByProfesional(profesional.get());

                    // Verificar si hay clientes
                    if (clientes != null && !clientes.isEmpty()) {
                        // Inicializar listado de accidentes
                        accidentes = new ArrayList<>();

                        // Por cada cliente
                        for (Cliente c : clientes) {
                            // Buscar listado de accidentes
                            List<Accidente> aux = accidentesRepositorio.findByCliente(c);

                            // Si tiene accidentes
                            if (aux != null && !aux.isEmpty()) {
                                // Agregar al listado
                                accidentes.addAll(aux);
                            }
                        }
                    }
                }
            } else if (request.isUserInRole(Constantes.ROLE_CLIENT)) {
                // Buscar información del cliente
                Optional<Cliente> cliente = clientesRepositorio.findByUsuario(usuario.get());

                // Verificar si existe
                if (cliente.isPresent()) {
                    // Buscar todos los accidentes del cliente
                    accidentes = accidentesRepositorio.findByCliente(cliente.get());
                }
            }

            // Verificar que hayan accidentes
            if (accidentes != null && !accidentes.isEmpty()) {
                // Inicializar listados
                String[] meses = new DateFormatSymbols(locale).getMonths();
                String[] tipos = new String[] { messageSource.getMessage("form.label.accident_type.work", null, locale),
                        messageSource.getMessage("form.label.accident_type.journey", null, locale) };
                String[] clasificaciones = new String[] {
                        messageSource.getMessage("form.label.accident_class.mild", null, locale),
                        messageSource.getMessage("form.label.accident_class.serious", null, locale),
                        messageSource.getMessage("form.label.accident_class.fatal", null, locale),
                        messageSource.getMessage("form.label.accident_class.other", null, locale) };

                int[] mesesValores = new int[meses.length];
                int[] tiposValores = new int[tipos.length];
                int[] clasificacionesValores = new int[clasificaciones.length];

                // Por cada accidente
                accidentes.forEach(accidente -> {
                    // Obtener fecha
                    LocalDate fecha = accidente.getFecha();

                    // Año actual
                    int year = Year.now().getValue();

                    // Capturar los accidentes del año actual
                    if (fecha.getYear() == year) {
                        // Sumar una unidad
                        mesesValores[fecha.getMonthValue() - 1] += 1;
                        tiposValores[accidente.getTipo() - 1] += 1;
                        clasificacionesValores[accidente.getClasificacion() - 1] += 1;
                    }
                });

                // Agregar valores a la vista
                vista.addObject("perMonthLabels", new JSONArray(meses));
                vista.addObject("perMonthValues", new JSONArray(mesesValores));

                vista.addObject("perTypeLabels", new JSONArray(tipos));
                vista.addObject("perTypeValues", new JSONArray(tiposValores));

                vista.addObject("perClassLabels", new JSONArray(clasificaciones));
                vista.addObject("perClassValues", new JSONArray(clasificacionesValores));
            }
        }

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.dashboard", null, locale));

        // Devolver vista
        return vista;
    }

}
