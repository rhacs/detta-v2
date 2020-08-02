package cl.leid.detta.api.exceptions;

public class InformationNotFoundException extends RuntimeException {

    // Constantes
    // -----------------------------------------------------------------------------------------

    /** Número de serie de la clase */
    private static final long serialVersionUID = -2673557523878603710L;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia del objeto {@link InformationNotFoundException}
     * 
     * @param message los detalles
     */
    public InformationNotFoundException(String message) {
        super(message);
    }

}
