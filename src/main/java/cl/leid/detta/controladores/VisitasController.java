package cl.leid.detta.controladores;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cl.leid.detta.modelos.Asesoria;
import cl.leid.detta.modelos.Visita;
import cl.leid.detta.repositorios.AsesoriasRepositorio;
import cl.leid.detta.repositorios.VisitasRepositorio;

@Controller
@RequestMapping(path = "/asesorias/{as:\\d+}/visitas")
public class VisitasController {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Objeto {@link Logger} con los métodos de depuración */
    private static final Logger logger = LogManager.getLogger(VisitasController.class);

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Autowired
    private AsesoriasRepositorio asesoriasRepositorio;

    @Autowired
    private VisitasRepositorio visitasRepositorio;

    // Solicitudes GET
    // -----------------------------------------------------------------------------------------

    /**
     * Muestra los detalles de una {@link Visita}
     * 
     * @param as    identificador numérico de la {@link Asesoria}
     * @param id    identificador numérico de la {@link Visita}
     * @param model objeto {@link Model} con el modelo de la vista
     * @return un objeto {@link String} con la respuesta a la solicitud
     */
    @GetMapping(path = "/{id:\\d+}")
    public String verDetalles(@PathVariable int as, @PathVariable int id, Model model) {
        // Obtener información de la asesoría
        Optional<Asesoria> asesoria = asesoriasRepositorio.findById(as);

        // Verificar si existe
        if (asesoria.isPresent()) {
            // Obtener información de la visita
            Optional<Visita> visita = visitasRepositorio.findById(id);

            // Verificar si existe
            if (visita.isPresent()) {
                // Agregar visita al modelo
                model.addAttribute("visita", visita);

                // Mostrar detalles
                return "asesorias/visita.detalle";
            }

            // Depuración
            logger.info("[WEB] No se pudo encontrar los detalles de la Visita ({}) para la Asesoría: {}", id,
                    asesoria.get());

            // Redireccionar
            return "redirect:/asesorias/" + as + "?noid=" + id;
        }

        // Depuración
        logger.info("[WEB] No se encontró la Asesoría solicitada: {}", as);

        // Redireccionar
        return "redirect:/asesorias?noid=" + as;
    }

}
