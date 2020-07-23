package cl.leid.detta.controladores;

import java.text.DateFormatSymbols;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import cl.leid.detta.modelos.Accion;
import cl.leid.detta.repositorios.AccionesRepositorio;

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
     * @param locale  objeto {@link Locale} con la información regional del cliente
     * @return un objeto {@link ModelAndView} con la respuesta al cliente
     */
    @GetMapping(path = "/")
    public ModelAndView mostrarHome(HttpServletRequest request, Locale locale) {
        // Crear vista
        ModelAndView vista = new ModelAndView("reportes");

        // Verificar permisos del usuario autenticado
        if (request.isUserInRole("ROLE_ADMIN")) {
            // Buscar todas las acciones
            List<Accion> acciones = new AccionesRepositorio(jdbcTemplate).buscarTodos();

            // Agregar a la vista
            vista.addObject("acciones", acciones);
        }

        // Inicializar objeto de símbolos
        DateFormatSymbols simbolos = new DateFormatSymbols(locale);

        // Obtener meses
        String[] meses = simbolos.getMonths();
        int[] mesesAccidentes = new int[meses.length];

        // Agregar listado de meses a la vista
        vista.addObject("perMonthLabels", new JSONArray(meses));
        vista.addObject("perMonthValues", new JSONArray(mesesAccidentes));

        // Generar arreglo de accidentes por tipo
        String[] tipos = new String[] { messageSource.getMessage("form.label.accident_type.work", null, locale),
                messageSource.getMessage("form.label.accident_type.journey", null, locale) };
        int[] tiposAccidentes = new int[tipos.length];

        // Agregar listado de tipos a la vista
        vista.addObject("perTypeLabels", new JSONArray(tipos));
        vista.addObject("perTypeValues", new JSONArray(tiposAccidentes));

        // Generar arreglo para accidentes por clasificación
        String[] clasificacion = new String[] {
                messageSource.getMessage("form.label.accident_class.mild", null, locale),
                messageSource.getMessage("form.label.accident_class.serious", null, locale),
                messageSource.getMessage("form.label.accident_class.fatal", null, locale),
                messageSource.getMessage("form.label.accident_class.other", null, locale) };
        int[] clasificacionAccidentes = new int[clasificacion.length];

        // Agregar a la vista
        vista.addObject("perClassLabels", new JSONArray(clasificacion));
        vista.addObject("perClassValues", new JSONArray(clasificacionAccidentes));

        // Agregar título
        vista.addObject("titulo", messageSource.getMessage("titles.dashboard", null, locale));

        // Devolver vista
        return vista;
    }

}
