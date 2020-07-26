package cl.leid.detta.modelos;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_ACCIONES)
public class Accion {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico de la {@link Accion} en base de datos */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constantes.SECUENCIA_ACCIONES)
    @SequenceGenerator(name = Constantes.SECUENCIA_ACCIONES, sequenceName = Constantes.SECUENCIA_ACCIONES, allocationSize = 1, initialValue = 1)
    @Column(name = "accion_id", nullable = false, updatable = false)
    private int id;

    /** Dirección de Correo Electrónico del {@link Usuario} que efectuó la acción */
    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    /** Detalles de la {@link Accion} */
    @Column(name = "detalles", nullable = false, updatable = false)
    private String detalles;

    /** Categoría de la {@link Accion} (1: Seguridad) */
    @Column(name = "categoria", nullable = false, updatable = false)
    private int categoria;

    /** Instante en el que ocurrió la {@link Accion} */
    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private Timestamp timestamp;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Accion}
     */
    public Accion() {

    }

    /**
     * Crea una nueva instancia del objeto {@link Accion}
     * 
     * @param email     correo electrónico del {@link Usuario}
     * @param detalles  detalles de la {@link Accion}
     * @param categoria número correspondiente a la categoría
     * @see Accion#categoria
     */
    public Accion(String email, String detalles, int categoria) {
        this.email = email;
        this.detalles = detalles;
        this.categoria = categoria;
    }

    /**
     * Crea una nueva instancia del objeto {@link Accion}
     * 
     * @param id        identificador numérico
     * @param email     correo electrónico del {@link Usuario}
     * @param detalles  detalles de la {@link Accion}
     * @param categoria categoría
     * @param timestamp instante de tiempo
     * 
     * @see Accion#categoria
     */
    public Accion(int id, String email, String detalles, int categoria, Timestamp timestamp) {
        this(email, detalles, categoria);

        this.id = id;
        this.timestamp = timestamp;
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * @return la fecha obtenida de {@link #timestamp}
     */
    public String getFecha() {
        // Obtener la fecha
        LocalDate fecha = timestamp.toLocalDateTime().toLocalDate();

        return fecha.toString();
    }

    /**
     * @return la hora obtenida de {@link #timestamp} con formato HH:mm (24 horas)
     */
    public String getHora() {
        // Obtener la hora
        LocalTime hora = timestamp.toLocalDateTime().toLocalTime();

        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
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
     * @return el correo electrónico del {@link Usuario}
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return los detalles
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * @return la categoría
     * @see Accion#categoria
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * @return el instante de tiempo
     */
    public Timestamp getTimestamp() {
        return timestamp;
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
     * @param email el correo electrónico a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param detalles los detalles a establecer
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    /**
     * @param categoria la categoría a establecer
     * @see Accion#categoria
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * @param timestamp el instante de tiempo a establecer
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

        Accion other = (Accion) obj;

        if (id != other.id)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Accion [id=" + id + ", email=" + email + ", detalles=" + detalles + ", categoria=" + categoria
                + ", timestamp=" + timestamp + "]";
    }

}
