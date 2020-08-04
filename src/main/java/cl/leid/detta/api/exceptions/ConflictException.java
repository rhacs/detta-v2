package cl.leid.detta.api.exceptions;

public class ConflictException extends RuntimeException {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Número de serie de la clase */
    private static final long serialVersionUID = 3609966189492238932L;

    // Atributos
    // ----------------------------------------------------------------------------------------

    /** Detalles del error */
    private final String mensaje;

    /** Nombre del campo donde ocurrió el error */
    private final String campo;

    /** Valor rechazado */
    private final transient Object valorRechazado;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia del objeto {@link ConflictException}
     * 
     * @param mensaje         mensaje que detalla el error
     * @param campo           campo donde ocurrió el error
     * @param valoreRechazado valor rechazado
     */
    public ConflictException(String mensaje, String campo, Object valoreRechazado) {
        super(mensaje);

        this.mensaje = mensaje;
        this.campo = campo;
        this.valorRechazado = valoreRechazado;
    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return los detalles del error
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @return el campo donde ocurrió el error
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @return el valor rechazado
     */
    public Object getValorRechazado() {
        return valorRechazado;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "ConflictException [mensaje=" + mensaje + ", campo=" + campo + ", valorRechazado=" + valorRechazado
                + "]";
    }

}
