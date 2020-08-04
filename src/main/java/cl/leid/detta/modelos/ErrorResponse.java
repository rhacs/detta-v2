package cl.leid.detta.modelos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Marca de tiempo cuando ocurrió el error */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss (z)")
    private Date timestamp;

    /** Estado HTTP */
    private HttpStatus httpStatus;

    /** Código de estado HTTP */
    private int httpStatusCode;

    /** Mensaje principal definiendo el error */
    private String mensaje;

    /** Listado de errores */
    private Set<ErrorDetail> errorDetails;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link ErrorResponse}
     */
    public ErrorResponse() {
        timestamp = new Date();
        mensaje = "Unknown error";
        errorDetails = new HashSet<>();
    }

    /**
     * Crea una nueva instancia del objeto {@link ErrorResponse}
     * 
     * @param httpStatus objeto {@link HttpStatus} con la información del estado
     *                   http
     */
    public ErrorResponse(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
        this.httpStatusCode = httpStatus.value();
    }

    /**
     * Crea una nueva instancia del objeto {@link ErrorResponse}
     * 
     * @param httpStatus objeto {@link HttpStatus} con la información del estado
     *                   http
     * @param exception  objeto {@link Exception} con la información del error
     */
    public ErrorResponse(HttpStatus httpStatus, Exception exception) {
        this(httpStatus);
        this.mensaje = exception.getLocalizedMessage() == null ? exception.getMessage()
                : exception.getLocalizedMessage();
    }

    // Métodos
    // -----------------------------------------------------------------------------------------

    /**
     * Agrega un nuevo error al listado
     * 
     * @param campo   el campo que produjo el error
     * @param mensaje los detalles del error
     */
    public void agregarError(String campo, String mensaje) {
        errorDetails.add(new ErrorDetail(campo, mensaje));
    }

    /**
     * Agrega un nuevo error al listado
     * 
     * @param campo          nombre del campo donde se produjo el error
     * @param mensaje        mensaje con los detalles del error
     * @param valorRechazado valor rechazado
     */
    public void agregarError(String campo, String mensaje, Object valorRechazado) {
        errorDetails.add(new ErrorDetail(campo, mensaje, valorRechazado));
    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return la marca de tiempo
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @return el estado http
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * @return el código de estado http
     */
    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * @return el mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @return los detalles del error
     */
    public Set<ErrorDetail> getErrorDetails() {
        return errorDetails;
    }

    // Setters
    // -----------------------------------------------------------------------------------------

    /**
     * @param timestamp la marca de tiempo a establecer
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @param httpStatus el estado http a establecer
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    /**
     * @param httpStatusCode the el código de estado http a establecer
     */
    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * @param mensaje el mensaje a establecer
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @param errorDetails el listado de errores a establecer
     */
    public void setErrorDetails(Set<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "ErrorResponse [timestamp=" + timestamp + ", httpStatus=" + httpStatus + ", httpStatusCode="
                + httpStatusCode + ", mensaje=" + mensaje + ", errorDetails=" + errorDetails + "]";
    }

}
