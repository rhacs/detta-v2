package cl.leid.detta.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

public class Accidente {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico del {@link Accidente} en base de datos */
    private int id;

    /** Fecha en que ocurrió el {@link Accidente} */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate fecha;

    /** Hora en que ocurrió el {@link Accidente} */
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime hora;

    /** Dirección donde ocurrió el {@link Accidente} */
    private String direccion;

    /** Lugar específico de la dirección donde ocurrió el {@link Accidente} */
    private String lugar;

    /** Qué estaba haciendo el trabajador al momento del {@link Accidente} */
    private String circunstancia;

    /** Qué pasó o cómo ocurrió el {@link Accidente} */
    private String detalles;

    /**
     * Clasificación del {@link Accidente} (1: Leve, 2: Grave, 3: Fatal, 4: Otro)
     */
    private int clasificacion;

    /** Tipo de {@link Accidente} (1: Trabajo, 2: Trayecto) */
    private int tipo;

    /**
     * Evidencia o medio de prueba que respalda al {@link Accidente} (1: Parte de
     * Carabineros, 2: Declaración, 3: Testigos, 4: Otro)
     */
    private int evidencia;

    /** Fecha en que se registró el {@link Accidente} en el repositorio */
    private LocalDateTime fechaRegistro;

    /**
     * Identificador numérico del {@link Cliente} relacionado con el
     * {@link Accidente}
     */
    private int clienteId;

    /** Nombre del {@link Cliente} relacionado con el {@link Accidente} */
    private String clienteNombre;

    /**
     * Identificador numérico del {@link Profesional} relacionado con el
     * {@link Cliente}
     */
    private Integer profesionalId;

    //
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Accidente}
     */
    public Accidente() {

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
    public LocalTime getHora() {
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
     * @return la clasificacion
     * @see Accidente#clasificacion
     */
    public int getClasificacion() {
        return clasificacion;
    }

    /**
     * @return el tipo
     * @see Accidente#tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @return la evidencia de respaldo
     * @see Accidente#evidencia
     */
    public int getEvidencia() {
        return evidencia;
    }

    /**
     * @return la fecha de registro en el sistema
     */
    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    /**
     * @return el identificador numérico del {@link Cliente}
     */
    public int getClienteId() {
        return clienteId;
    }

    /**
     * @return el nombre del {@link Cliente}
     */
    public String getClienteNombre() {
        return clienteNombre;
    }

    /**
     * @return el identificador numérico del {@link Profesional}
     */
    public Integer getProfesionalId() {
        return profesionalId;
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
    public void setHora(LocalTime hora) {
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
     * @param clasificacion la clasificación a establecer
     * @see Accidente#clasificacion
     */
    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    /**
     * @param tipo el tipo a establecer
     * @see Accidente#tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @param evidencia la evidencia de respaldo a establecer
     * @see Accidente#evidencia
     */
    public void setEvidencia(int evidencia) {
        this.evidencia = evidencia;
    }

    /**
     * @param fechaRegistro la fecha de registro en el sistema a establecer
     */
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /**
     * @param clienteId el identificador numérico del {@link Cliente} a establecer
     */
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    /**
     * @param clienteNombre el nombre del {@link Cliente} a establecer
     */
    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    /**
     * @param profesionalId el identificador numérico del {@link Profesional} a
     *                      establecer
     */
    public void setProfesionalId(Integer profesionalId) {
        this.profesionalId = profesionalId;
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
                + ", clienteId=" + clienteId + ", clienteNombre=" + clienteNombre + ", profesionalId=" + profesionalId
                + "]";
    }

}
