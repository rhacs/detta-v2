package cl.leid.detta.controladores;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.modelos.Accion;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.modelos.Profesional;
import cl.leid.detta.repositorios.AccidentesRepositorio;
import cl.leid.detta.repositorios.AccionesRepositorio;
import cl.leid.detta.repositorios.ClientesRepositorio;
import cl.leid.detta.repositorios.ProfesionalesRepositorio;

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

    /**
     * Objeto {@link JdbcTemplate} con los métodos de manipulación de la base de
     * datos
     */
    @Autowired
    private JdbcTemplate jdbcTemplate;

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

        // Inicializar repositorios
        ProfesionalesRepositorio profesionalesRepositorio = new ProfesionalesRepositorio(jdbcTemplate);
        ClientesRepositorio clientesRepositorio = new ClientesRepositorio(jdbcTemplate);
        AccidentesRepositorio accidentesRepositorio = new AccidentesRepositorio(jdbcTemplate);

        // Inicializar objeto de símbolos
        DateFormatSymbols simbolos = new DateFormatSymbols(locale);

        // Obtener meses
        String[] meses = simbolos.getMonths();
        int[] mesesAccidentes = new int[meses.length];

        // Generar arreglo de accidentes por tipo
        String[] tipos = new String[] { messageSource.getMessage("form.label.accident_type.work", null, locale),
                messageSource.getMessage("form.label.accident_type.journey", null, locale) };
        int[] tiposAccidentes = new int[tipos.length];

        // Generar arreglo para accidentes por clasificación
        String[] clasificacion = new String[] {
                messageSource.getMessage("form.label.accident_class.mild", null, locale),
                messageSource.getMessage("form.label.accident_class.serious", null, locale),
                messageSource.getMessage("form.label.accident_class.fatal", null, locale),
                messageSource.getMessage("form.label.accident_class.other", null, locale) };
        int[] clasificacionAccidentes = new int[clasificacion.length];

        // Inicializar listado de accidentes
        List<Accidente> accidentes = new ArrayList<>();

        // Verificar permisos del usuario autenticado
        if (request.isUserInRole("ROLE_ADMIN")) {
            // Buscar todas las acciones
            List<Accion> acciones = new AccionesRepositorio(jdbcTemplate).buscarTodos();

            // Agregar a la vista
            vista.addObject("acciones", acciones);

            // Buscar todos los accidentes
            accidentes = accidentesRepositorio.buscarTodos();
        } else if (request.isUserInRole("ROLE_STAFF")) {
            // Buscar información del profesional
            Profesional profesional = profesionalesRepositorio.buscarPorEmail(auth.getName());

            // Buscar todos los clientes del profesional
            List<Cliente> clientes = clientesRepositorio.buscarPorProfesionalId(profesional.getId());

            // Verificar si hay resultados
            if (clientes != null) {
                // Por cada cliente
                for (Cliente c : clientes) {
                    // Buscar los accidentes del cliente
                    List<Accidente> aux = accidentesRepositorio.buscarPorClienteId(c.getId());

                    // Verificar resultados
                    if (aux != null) {
                        accidentes.addAll(aux);
                    }
                }
            }
        } else if (request.isUserInRole("ROLE_CLIENT")) {
            // Buscar información del cliente
            Cliente cliente = clientesRepositorio.buscarPorEmail(auth.getName());

            // Buscar todos los accidentes del cliente
            accidentes = accidentesRepositorio.buscarPorClienteId(cliente.getId());
        }

        // Verificar si hay resultados
        if (accidentes != null) {
            // Obtener año actual
            int year = Year.now().getValue();

            // Por cada accidente, extraer la información necesaria
            accidentes.forEach(accidente -> {
                // Obtener fecha
                LocalDate fecha = accidente.getFecha();

                // Verificar si ocurrió el año actual
                if (fecha.getYear() == year) {
                    // Obtener mes
                    int mes = fecha.getMonthValue();

                    // Sumar una unidad a la cantidad de accidentes por mes
                    mesesAccidentes[mes - 1] += 1;

                    // Obtener tipo y sumar una unidad
                    int tipo = accidente.getTipo();
                    tiposAccidentes[tipo - 1] += 1;

                    // Obtener clasificacion y sumar una unidad
                    int clas = accidente.getClasificacion();
                    clasificacionAccidentes[clas - 1] += 1;
                }
            });
        }

        // Agregar listado de meses a la vista
        vista.addObject("perMonthLabels", new JSONArray(meses));
        vista.addObject("perMonthValues", new JSONArray(mesesAccidentes));

        // Agregar listado de tipos a la vista
        vista.addObject("perTypeLabels", new JSONArray(tipos));
        vista.addObject("perTypeValues", new JSONArray(tiposAccidentes));

        // Agregar a la vista
        vista.addObject("perClassLabels", new JSONArray(clasificacion));
        vista.addObject("perClassValues", new JSONArray(clasificacionAccidentes));

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.dashboard", null, locale));

        // Devolver vista
        return vista;
    }

}
