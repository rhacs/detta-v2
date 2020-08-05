package cl.leid.detta.modelos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import cl.leid.detta.Constantes;

/**
 * @author Segundo Medina González
 */
@Entity
@Table(name = Constantes.TABLA_CAPACITACIONES)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_CAPACITACIONES, sequenceName = Constantes.SECUENCIA_CAPACITACIONES)
public class Capacitacion {

    // Atributos
    // ----------------------------------------------------------------------------------------------

    /**
     * Identificador numerico de la {@link Capacitacion}}
     */
    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_CAPACITACIONES, strategy = GenerationType.SEQUENCE)
    @Column(name = "capacitacion_id", nullable = false, unique = true, updatable = false)
    private int id;

    /**
     * Define el tipo de capacitacion {@link Capacitacion}
     */
    @Size(min = 5, max = 50)
    @Column(name = "tipo", nullable = false)
    private String tipo;

    /**
     * Define la fecha en la que va a realizarse {@link Capacitacion}
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Define la hora en la que va a realizarse {@link Capacitacion}
     */
    @Pattern(regexp = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")
    @Size(min = 5, max = 5)
    @Column(name = "hora", nullable = false)
    private String hora;

    /**
     * Define los objetivos a tratar en la reunion {@link Capacitacion}
     */
    @Size(min = 5, max = 250)
    @Column(name = "objetivos", nullable = false)
    private String objetivos;

    /**
     * Define el lugar en donde se va a realizar {@link Capacitacion}
     */
    @Size(min = 5, max = 150)
    @Column(name = "direccion", nullable = false)
    private String direccion;

    /**
     * Duracion de la capacitacion
     */
    @Size(min = 5, max = 5)
    @Column(name = "duracion", nullable = false)
    private String duracion;

    /**
     * Define el contenido a tratar en la reunion {@link Capacitacion}
     */
    @Size(min = 5, max = 1000)
    @Column(name = "contenido", nullable = false)
    private String contenido;

    /**
     * Estado de la {@link Capacitacion}
     * <ul>
     * <li>1: Pendiente</li>
     * <li>2: En Proceso</li>
     * <li>3: Realizado</li>
     * </ul>
     */
    @Column(name = "estado", nullable = false)
    private int estado;

    /**
     * Objeto {@link Cliente} que esta relacionado con la {@link Capacitacion}
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    /**
     * Objeto {@link Profecional} que esta relacionado con la {@link Capacitacion}
     */
    @OneToOne(optional = true)
    @JoinColumn(name = "profesional_id", referencedColumnName = "profesional_id")
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

    /**
     * @return la dirección
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return la duracion
     */
    public String getDuracion() {
        return duracion;
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
     * @return el objeto {@link Cliente} al cual hace referencia
     */
    public Cliente getCliente() {
        return cliente;
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
     * @param duracion duracion que va a tener la capacitacion
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
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
     * @param usuario el objeto {@link Cliente} el cual hace referencia
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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
        return "Capacitacion [id=" + id + ", tipo=" + tipo + ", fecha=" + fecha + ", hora=" + hora + ", objetivos="
                + objetivos + ", direccion=" + direccion + ", duracion=" + duracion + ", contenido=" + contenido
                + ", estado=" + estado + ", cliente=" + cliente + ", profesional=" + profesional + "]";
    }

}
