package cl.leid.detta.modelos;

import java.time.LocalDate;

/**
 * 
 * @author Segundo Medina González
 *
 */

public class Capacitacion {

    // Atributos
    // ----------------------------------------------------------------------------------------------

    /**
     * Identificador numerico de la {@link Capacitacion}}
     */

    private int id;

    /**
     * Define el tipo de capacitacion {@link Capacitacion}
     */

    private String tipo;

    /**
     * Define la fecha en la que va a realizarse {@link Capacitacion}
     */

    private LocalDate fecha;

    /**
     * Define la hora en la que va a realizarse {@link Capacitacion}
     */

    private String hora;

    /**
     * Define los objetivos a tratar en la reunion {@link Capacitacion}
     */

    private String objetivos;

    /**
     * Define el lugar en donde se va a realizar {@link Capacitacion}
     */

    private String direccion;

    /**
     * Define el contenido a tratar en la reunion {@link Capacitacion}
     */

    private String contenido;

    /**
     * Define el estado en el cual se encuentra la capacitacion {@link Capacitacion}
     */

    private int estado;

    /**
     * Objeto {@link Usuario} que esta relacionado con la {@link Capacitacion}
     */

    private Usuario usuario;

    /**
     * Objeto {@link Profecional} que esta relacionado con la {@link Capacitacion}
     */

    private Profesional profesional;

    // Constructores
    // ----------------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacia del objeto {@link Capacitacion}
     */

    public Capacitacion() {

    }

    // Getters
    // ----------------------------------------------------------------------------------------------

    /**
     * @return el identificador numérico
     */

    public int getId() {
        return id;
    }

    /**
     * @return el tipo
     */

    public String getTipo() {
        return tipo;
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
     * @return los objetivos
     */

    public String getObjetivos() {
        return objetivos;
    }

    public String getDireccion() {
        return direccion;
    }

    /**
     * @return el contenido
     */

    public String getContenido() {
        return contenido;
    }

    /**
     * @return el estado
     */

    public int getEstado() {
        return estado;
    }

    /**
     * @return el objeto {@link Usuario} al cual hace referencia
     */

    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @return el objeto {@link Profecional} al cual hace referencia
     */

    public Profesional getProfesional() {
        return profesional;
    }

    // Setters
    // ----------------------------------------------------------------------------------------------

    /**
     * @param id el identificador numérico a establecer
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param tipo de capacitacion que se va a realizar
     */

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @param fecha a realizarse la capacitacion
     */

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @param hora en la que se realizara la capacitacion
     */

    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @param objetivos a tratar en la capacitacion
     */

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    /**
     * @param direccion en la que se realizara la capacitacion
     */

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param contenido tratado en la capacitacion
     */

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * @param estado en el que se encuentra la capacitacion
     */

    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * @param usuario el objeto {@link Usuario} el cual hace referencia
     */

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @param profesional el objeto {@link Profecional} el cual hace referencia
     */

    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    // Herencias (Object)
    // ----------------------------------------------------------------------------------------------

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
        Capacitacion other = (Capacitacion) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Capacitacion [id=" + id + ", tipo=" + tipo + ", hora=" + hora + ", objetivos=" + objetivos
                + ", direccion=" + direccion + ", contenido=" + contenido + ", estado=" + estado + ", usuario="
                + usuario + ", profesional=" + profesional + "]";
    }

}
