package cl.leid.detta.modelos;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import cl.leid.detta.Constantes;

/**
 * @author Alexis Ruiz.
 */
@Entity
@Table(name = Constantes.TABLA_VISITAS)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_VISITAS, sequenceName = Constantes.SECUENCIA_VISITAS)
public class Visita {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /**
     * Identificador numerico de la {@link Visita}
     */
    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_VISITAS, strategy = GenerationType.SEQUENCE)
    @Column(name = "visita_id", nullable = false, unique = true, updatable = false)
    private int id;

    /**
     * Fecha en la que se realizara la {@link Visita}
     */
    @NotNull
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /**
     * Hora en la que se realizara la {@link Visita}
     */
    @Pattern(regexp = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")
    @Size(min = 5, max = 5)
    @Column(name = "hora", nullable = false)
    private String hora;

    /**
     * Direccion donde se realizara la {@link Visita}
     */
    @Size(min = 5, max = 150)
    @Column(name = "direccion", nullable = false)
    private String direccion;

    /**
     * Motivo por el cual se genera la {@link Visita}
     * <ul>
     * <li>1: Accidente</li>
     * <li>2: Fiscalización</li>
     * <li>3: Prevención</li>
     * </ul>
     */
    @Min(1)
    @Max(3)
    @Column(name = "motivo", nullable = false)
    private int motivo;

    /**
     * Estado de la {@link Visita}
     * <ul>
     * <li>1: Pendiente</li>
     * <li>2: En Proceso</li>
     * <li>3: Realizado</li>
     * </ul>
     */
    @Min(1)
    @Max(3)
    @Column(name = "estado", nullable = false)
    private int estado;

    /**
     * Objeto {@link Asesoria} que esta relazionada la {@link Visita}
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "asesoria_id", referencedColumnName = "asesoria_id")
    private Asesoria asesoria;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una instancia vacia del objeto {@link Visita}
     */
    public Visita() {

    }

    // Getters
    // -----------------------------------------------------------------------------------------

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
    // -----------------------------------------------------------------------------------------

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
