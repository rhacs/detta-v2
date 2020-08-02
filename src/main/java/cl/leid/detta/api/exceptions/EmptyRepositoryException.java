package cl.leid.detta.api.exceptions;

public class EmptyRepositoryException extends RuntimeException {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Número de serie de la clase */
    private static final long serialVersionUID = -675634297302842277L;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia del objeto {@link EmptyRepositoryException}
     * 
     * @param mensaje detalles del error
     */
    public EmptyRepositoryException(String mensaje) {
        super(mensaje);
    }

}
