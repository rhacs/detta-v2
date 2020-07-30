package cl.leid.detta.modelos;

import java.time.LocalDate;

/**
 * @author Ana Luisa Medina
 */
public class Asesoria {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Identificador numérico de la {@link Asesoria}
     */
    private int id;

    /**
     * Tema principal de la {@link Asesoria}
     */
    private String tema;

    /**
     * Detalle a tratar en la asesoría de la {@link Asesoria}
     */
    private String descripcion;

    /**
     * Entidad que realiza fiscalización para la cual se requiere {@link Asesoria}
     */
    private String fiscalizador;

    /**
     * Departamento o área en el que se va a generar la {@link Asesoria}
     */
    private String departamento;

    /**
     * Estado del proceso en el que se encuentra la {@link Asesoria}
     */
    private String estado;

    /**
     * Fecha para cuando está programada la {@link Asesoria}
     */
    private LocalDate fecha;

    /**
     * Hora en la que está programada la {@link Asesoria}
     */
    private String hora;

    /**
     * Dirección en dónde se realizará la {@link Asesoria}
     */
    private String direccion;

    /**
     * Objeto {@link Cliente} que está relacionado con la {@link Asesoria}
     */
    private Cliente cliente;

    /**
     * Objeto {@link Profesional} que está relacionado con la {@link Asesoria}
     */
    private Profesional profesional;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Asesoria}
     */
    public Asesoria() {

    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return el identificador numérico
     */
    public int getId() {
        return id;
    }

    /**
     * @return el tema principal
     */
    public String getTema() {
        return tema;
    }

    /**
     * @return la descripción de que tratará la asesoría
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return entidad que realizará la fiscalización
     */
    public String getFiscalizador() {
        return fiscalizador;
    }

    /**
     * @return departamento que recibirá la asesoría
     */
    public String getDepartamento() {
        return departamento;
    }

    /**
     * @return estado en que se encuentra la asesoría
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @return fecha en que está programada la asesoría
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * @return hora en que está programada la asesoría
     */
    public String getHora() {
        return hora;
    }

    /**
     * @return dirección donde se llevará a cabo la sesoría
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return un objeto con la información del {@link Cliente} que recibe la
     *         asesoría
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @return un objeto con la información del {@link Profesional} que entregará la
     *         asesoría
     */
    public Profesional getProfesional() {
        return profesional;
    }

    // Setters
    // -----------------------------------------------------------------------------------------

    /**
     * @param id el identificador numérico a establecer
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param tema tema principal que tratará la asesoría
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * @param descripcion detalles de los puntos a tratar en la asesoría
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param fiscalizador Entidad fiscalizadora que ejecutará la fiscalización al
     *                     cliente
     */
    public void setFiscalizador(String fiscalizador) {
        this.fiscalizador = fiscalizador;
    }

    /**
     * @param departamento departamento que recibirá la asesoría
     */
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    /**
     * @param estado etapa del proceso en el que se encuentra la asesoría
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @param fecha indica la fecha para la cual está programada la asesoría
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @param hora indica la hora en cual se agendó la asesoría
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @param direccion es la dirección dónde se llevará a cabo la asesoría
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param cliente El objeto {@link Cliente} al cual hace referencia
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @param profesional El objeto {@link Profesional} al cual hace referencia
     */
    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

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
        Asesoria other = (Asesoria) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Asesoria [id=" + id + ", tema=" + tema + ", descripcion=" + descripcion + ", fiscalizador="
                + fiscalizador + ", departamento=" + departamento + ", estado=" + estado + ", fecha=" + fecha
                + ", hora=" + hora + ", direccion=" + direccion + ", cliente=" + cliente + ", profesional="
                + profesional + "]";
    }

}