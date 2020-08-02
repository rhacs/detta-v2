package cl.leid.detta.modelos;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(content = JsonInclude.Include.NON_EMPTY)
public class ErrorDetail {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Nombre del campo donde ocurrió el error */
    private String campo;

    /** Detalles del error */
    private String mensaje;

    /** Nombre del objeto donde ocurrió el error */
    private String nombreObjeto;

    /** Qué valor se rechazó */
    private Object valorRechazado;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia del objeto {@link ErrorDetail}
     * 
     * @param campo   nombre del campo donde ocurrió el error
     * @param mensaje detalles del error
     */
    public ErrorDetail(String campo, String mensaje) {
        this.campo = campo;
        this.mensaje = mensaje;
    }

    /**
     * Crea una nueva instancia del objeto {@link ErrorDetail}
     * 
     * @param campo          nombre del campo donde ocurrió el error
     * @param mensaje        detalles del error
     * @param valorRechazado valor rechazado
     */
    public ErrorDetail(String campo, String mensaje, Object valorRechazado) {
        this(campo, mensaje);
        this.valorRechazado = valorRechazado;
    }

    /**
     * Crea una nueva instancia del objeto {@link ErrorDetail}
     * 
     * @param campo          nombre del campo donde ocurrió el error
     * @param mensaje        detalles del error
     * @param nombreObjeto   nombre del objeto donde ocurrió el error
     * @param valorRechazado valor rechazado
     */
    public ErrorDetail(String campo, String mensaje, String nombreObjeto, Object valorRechazado) {
        this(campo, mensaje, valorRechazado);
        this.nombreObjeto = nombreObjeto;
    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return el nombre del campo
     */
    public String getCampo() {
        return campo;
    }

    /**
     * @return el mensaje de detalle
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @return el nombre del objeto
     */
    public String getNombreObjeto() {
        return nombreObjeto;
    }

    /**
     * @return el valor rechazado
     */
    public Object getValorRechazado() {
        return valorRechazado;
    }

    // Setters
    // -----------------------------------------------------------------------------------------

    /**
     * @param campo el nombre del campo a establecer
     */
    public void setCampo(String campo) {
        this.campo = campo;
    }

    /**
     * @param mensaje el mensaje a establecer
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @param nombreObjeto el nombre del objeto a establecer
     */
    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }

    /**
     * @param valorRechazado el valor rechazado a establecer
     */
    public void setValorRechazado(Object valorRechazado) {
        this.valorRechazado = valorRechazado;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "ErrorDetail [campo=" + campo + ", mensaje=" + mensaje + ", nombreObjeto=" + nombreObjeto
                + ", valorRechazado=" + valorRechazado + "]";
    }

}
