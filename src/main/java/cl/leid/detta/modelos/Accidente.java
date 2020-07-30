package cl.leid.detta.modelos;

import java.sql.Timestamp;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_ACCIDENTES)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_ACCIDENTES, sequenceName = Constantes.SECUENCIA_ACCIDENTES)
public class Accidente {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico del {@link Accidente} en base de datos */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = Constantes.SECUENCIA_ACCIDENTES)
    @Column(name = "accidente_id", nullable = false, updatable = false)
    private int id;

    /** Fecha en que ocurrió el {@link Accidente} */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    /** Hora en que ocurrió el {@link Accidente} */
    @Column(name = "hora", nullable = false)
    @Pattern(regexp = "(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]")
    @Size(min = 5, max = 5)
    private String hora;

    /** Dirección donde ocurrió el {@link Accidente} */
    @Column(name = "direccion", nullable = false)
    @Size(min = 5, max = 250)
    private String direccion;

    /** Lugar específico de la dirección donde ocurrió el {@link Accidente} */
    @Column(name = "lugar", nullable = false)
    @Size(min = 5, max = 250)
    private String lugar;

    /** Qué estaba haciendo el trabajador al momento del {@link Accidente} */
    @Column(name = "circunstancia", nullable = false)
    @Size(min = 5, max = 250)
    private String circunstancia;

    /** Qué pasó o cómo ocurrió el {@link Accidente} */
    @Column(name = "detalles", nullable = false)
    @Size(min = 5, max = 2000)
    private String detalles;

    /**
     * Clasificación del {@link Accidente}
     * <ul>
     * <li>1: Leve</li>
     * <li>2: Grave</li>
     * <li>3: Fatal</li>
     * <li>4: Otro</li>
     * </ul>
     */
    @Column(name = "clasificacion", nullable = false)
    private int clasificacion;

    /**
     * Tipo de {@link Accidente}
     * <ul>
     * <li>1: Trabajo</li>
     * <li>2: Trayecto</li>
     * </ul>
     */
    @Column(name = "tipo", nullable = false)
    private int tipo;

    /**
     * Evidencia o medio de prueba que respalda al {@link Accidente}
     * <ul>
     * <li>1: Parte Policial</li>
     * <li>2: Declaración</li>
     * <li>3: Testigos</li>
     * <li>4: Otro</li>
     */
    @Column(name = "evidencia", nullable = false)
    private int evidencia;

    /** Fecha en que se registró el {@link Accidente} en el repositorio */
    @CreationTimestamp
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Timestamp fechaRegistro;

    /** {@link Cliente} asociado al {@link Accidente} */
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", referencedColumnName = "cliente_id")
    private Cliente cliente;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Accidente}
     */
    public Accidente() {
        // Implementación vacía
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
     * @return la dirección
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @return el lugar
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * @return la circunstancia
     */
    public String getCircunstancia() {
        return circunstancia;
    }

    /**
     * @return los detalles
     */
    public String getDetalles() {
        return detalles;
    }

    /**
     * @return la {@link #clasificacion}
     */
    public int getClasificacion() {
        return clasificacion;
    }

    /**
     * @return el {@link #tipo}
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @return la {@link #evidencia}
     */
    public int getEvidencia() {
        return evidencia;
    }

    /**
     * @return la fecha de registro en el sistema
     */
    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @return el {@link Cliente}
     */
    public Cliente getCliente() {
        return cliente;
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
     * @param fecha la fecha a establecer
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * @param hora la hora a establecer
     */
    public void setHora(String hora) {
        this.hora = hora;
    }

    /**
     * @param direccion la dirección a establecer
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @param lugar el lugar a establecer
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * @param circunstancia la circunstancia a establecer
     */
    public void setCircunstancia(String circunstancia) {
        this.circunstancia = circunstancia;
    }

    /**
     * @param detalles los detalles a establecer
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    /**
     * @param clasificacion la {@link #clasificacion} a establecer
     */
    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @param tipo el {@link #tipo} a establecer
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @param evidencia la {@link #evidencia} a establecer
     */
    public void setEvidencia(int evidencia) {
        this.evidencia = evidencia;
    }

    /**
     * @param fechaRegistro la fecha de registro a establecer
     */
    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @param cliente el {@link Cliente} a establecer
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

        Accidente other = (Accidente) obj;

        if (id != other.id)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Accidente [id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", direccion=" + direccion + ", lugar="
                + lugar + ", circunstancia=" + circunstancia + ", detalles=" + detalles + ", clasificacion="
                + clasificacion + ", tipo=" + tipo + ", evidencia=" + evidencia + ", fechaRegistro=" + fechaRegistro
                + ", cliente=" + cliente + "]";
    }

}
