package cl.leid.detta.modelos;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_ACCIONES)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_ACCIONES, sequenceName = Constantes.SECUENCIA_ACCIONES)
public class Accion {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico de la {@link Accion} en base de datos */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constantes.SECUENCIA_ACCIONES)
    @Column(name = "accion_id", nullable = false, updatable = false)
    private int id;

    /** Detalles de la {@link Accion} */
    @Column(name = "detalles", nullable = false, updatable = false)
    private String detalles;

    /**
     * Categoría de la {@link Accion}
     * <ul>
     * <li>1: Seguridad</li>
     * <li>2: Profesionales</li>
     * <li>3: Clientes</li>
     * <li>4: Accidentes</li>
     * <ul>
     */
    @Column(name = "categoria", nullable = false, updatable = false)
    private int categoria;

    /** Instante en el que ocurrió la {@link Accion} */
    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private Timestamp timestamp;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

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
     * @param detalles  detalles de la {@link Accion}
     * @param categoria {@link #categoria} de la {@link Accion}
     * @param usuario   información del {@link Usuario}
     */
    public Accion(String detalles, int categoria, Usuario usuario) {
        this.detalles = detalles;
        this.categoria = categoria;
        this.usuario = usuario;
    }

    /**
     * Crea una nueva instancia del objeto {@link Accion}
     * 
     * @param id        identificador numérico
     * @param detalles  detalles
     * @param categoria categoría
     * @param timestamp marca de tiempo
     * @param usuario   {@link Usuario}
     */
    public Accion(int id, String detalles, int categoria, Timestamp timestamp, Usuario usuario) {
        this(detalles, categoria, usuario);

        this.id = id;
        this.timestamp = timestamp;
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * @return la fecha a partir del {@link #timestamp} con el formato yyyy/MM/dd
     */
    public String getFecha() {
        return timestamp.toLocalDateTime().toLocalDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    /**
     * @return la hora a partir del {@link #timestamp} con el formato HH:mm (24
     *         horas)
     */
    public String getHora() {
        return timestamp.toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
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
     * @return los detalles
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * @return la {@link #categoria}
     */
    public int getCategoria() {
        return categoria;
    }

    /**
     * @return la marca de tiempo
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * @return el {@link Usuario}
     */
    public Usuario getUsuario() {
        return usuario;
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
     * @param detalles los detalles a establecer
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    /**
     * @param categoria la {@link #categoria} a establecer
     */
    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    /**
     * @param timestamp la marca de tiempo a establecer
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @param usuario el {@link Usuario} a establecer
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        return "Accion [id=" + id + ", detalles=" + detalles + ", categoria=" + categoria + ", timestamp=" + timestamp
                + ", usuario=" + usuario + "]";
    }

}
