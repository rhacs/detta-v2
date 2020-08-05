package cl.leid.detta.modelos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import cl.leid.detta.Constantes;

/**
 * @author Ana Luisa Medina
 */
@Entity
@Table(name = Constantes.TABLA_ASESORIAS)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_ASESORIAS, sequenceName = Constantes.SECUENCIA_ASESORIAS)
public class Asesoria {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Identificador numérico de la {@link Asesoria}
     */
    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_ASESORIAS, strategy = GenerationType.SEQUENCE)
    @Column(name = "asesoria_id", nullable = false, unique = true, updatable = false)
    private int id;

    /**
     * Tema principal de la {@link Asesoria}
     */
    @Column(name = "tema", nullable = false)
    @Size(min = 5, max = 250)
    private String tema;

    /**
     * Detalle a tratar en la asesoría de la {@link Asesoria}
     */
    @Column(name = "descripcion", nullable = false)
    @Size(min = 5, max = 1000)
    private String descripcion;

    /**
     * Entidad que realiza fiscalización para la cual se requiere {@link Asesoria}
     */
    @Column(name = "fiscalizador", nullable = true)
    @Size(min = 5, max = 250)
    private String fiscalizador;

    /**
     * Departamento o área en el que se va a generar la {@link Asesoria}
     */
    @Column(name = "departamento", nullable = false)
    @Size(min = 5, max = 250)
    private String departamento;

    /**
     * Estado del proceso en el que se encuentra la {@link Asesoria}
     * <ul>
     * <li>1: Pendiente</li>
     * <li>2: En proceso</li>
     * <li>3: Realizado</li>
     * </ul>
     */
    @Min(1)
    @Max(3)
    @Column(name = "estado", nullable = false)
    private int estado;

    /**
     * Fecha para cuando está programada la {@link Asesoria}
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Hora en la que está programada la {@link Asesoria}
     */
    @Pattern(regexp = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")
    @Size(min = 5, max = 5)
    @Column(name = "hora", nullable = false)
    private String hora;

    /**
     * Dirección en dónde se realizará la {@link Asesoria}
     */
    @Size(min = 5, max = 250)
    @Column(name = "direccion", nullable = false)
    private String direccion;

    /**
     * {@link Visita}s de la {@link Asesoria}
     */
    @OneToMany(mappedBy = "asesoria")
    private Set<Visita> visitas;

    /**
     * Objeto {@link Cliente} que está relacionado con la {@link Asesoria}
     */
    @OneToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    /**
     * Objeto {@link Profesional} que está relacionado con la {@link Asesoria}
     */
    @OneToOne(optional = true)
    @JoinColumn(name = "profesional_id", referencedColumnName = "profesional_id")
    private Profesional profesional;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Asesoria}
     */
    public Asesoria() {
        visitas = new HashSet<>();
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * @return la cantidad de {@link Visita}s de la {@link Asesoria}
     */
    public int cantidadVisitas() {
        return visitas.size();
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
    public int getEstado() {
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
     * @return dirección donde se llevará a cabo la asesoría
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return las {@link Visita}s de la {@link Asesoria}
     */
    public Set<Visita> getVisitas() {
        return visitas;
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
    public void setEstado(int estado) {
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
     * @param visitas las {@link Visita}s a establecer
     */
    public void setVisitas(Set<Visita> visitas) {
        this.visitas = visitas;
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
                + ", hora=" + hora + ", direccion=" + direccion + ", visitas=" + visitas + ", cliente=" + cliente
                + ", profesional=" + profesional + "]";
    }

}
