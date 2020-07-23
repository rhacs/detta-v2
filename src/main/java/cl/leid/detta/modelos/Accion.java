package cl.leid.detta.modelos;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Accion {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico de la {@link Accion} en base de datos */
    private int id;

    /** Dirección de Correo Electrónico del {@link Usuario} que efectuó la acción */
    private String email;

    /** Detalles de la {@link Accion} */
    private String detalles;

    /** Categoría de la {@link Accion} (1: Seguridad) */
    private int categoria;

    /** Instante en el que ocurrió la {@link Accion} */
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

    public String getFecha() {
        // Obtener la fecha
        LocalDate fecha = timestamp.toLocalDateTime().toLocalDate();

        return fecha.toString();
    }

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
