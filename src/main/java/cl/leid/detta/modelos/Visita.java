package cl.leid.detta.modelos;

import java.time.LocalDate;
/**
 * 
 * @author Alexis Ruiz.
 *
 */
public class Visita {

    // Atributos
    // -------------------------------------

    /**
     * Identificador numerico de la {@link Visita}
     */
    private int id;
    /**
     * Fecha en la que se realizara la {@link Visita}
     */
    private LocalDate fecha;
    /**
     * Hora en la que se realizara la {@link Visita}
     */
    private String hora;
    /**
     * Direccion donde se realizara la {@link Visita}
     */
    private String direccion;
    /**
     * Motivo por el cual se genera la {@link Visita}
     */
    private int motivo;
    /**
     * Estado de la {@link Visita} 
     */
    private int estado;
    /**
     * Objeto {@link Asesoria} que esta relazionada la {@link Visita}
     */
    private Asesoria asesoria;

    // Constructores
    // ------------------------------------
    /**
     * Crea una instancia vacia del objeto {@link Visita}
     */
    public Visita() {

    }

    // gettetrs
    // ------------------------------------
    /**
     * @return el id
     */
    public int getId() {
        return id;
    }

    /**
     * @return la fecha
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @return la hora
     */
    public String getHora() {
        return hora;
    }

    /**
     * @return la dirrecion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return el motivo
     */
    public int getMotivo() {
        return motivo;
    }

    /**
     * @return el estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * @return la {@link Asesoria}
     */
    public Asesoria getAsesoria() {
        return asesoria;
    }

    // Setters
    // ------------------------------------
    /**
     * @param id id que recibe la visita
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param fecha fecha en que se genera la visita
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @param hora hora en que se genera la visita
     */
    public void setHora(String hora) {
        this.hora = hora;
    }
    /**
     * @param direccion direccion donde se raliza la visita
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    /**
     * @param motivo motivo de la visita
     */
    public void setMotivo(int motivo) {
        this.motivo = motivo;
    }
    /**
     * @param estado estado de la visita
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @param asesoria el objeto {@link Asesoria} al cual hace referencia
     */
    public void setAsesoria(Asesoria asesoria) {
        this.asesoria = asesoria;
    }
    // Herencias (Objects)
    // ----------------------------

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Visita other = (Visita) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Visita [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", direccion=" + direccion + ", motivo="
                + motivo + ", estado=" + estado + "]";
    }

}
