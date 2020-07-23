package cl.leid.detta.modelos;

public class Profesional extends Usuario {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Nombre completo del {@link Profesional} */
    private String nombre;

    /** Teléfono de contacto del {@link Profesional} */
    private String telefono;

    /**
     * Identificador numérico del {@link Usuario} relacionado con el
     * {@link Profesional}
     */
    private int usuarioId;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Profesional}
     */
    public Profesional() {
        super();
    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return el nombre completo
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return el teléfono de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @return el identificador numérico del {@link Usuario} relacionado
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    // Setters
    // -----------------------------------------------------------------------------------------

    /**
     * @param nombre el nombre completo a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param telefono el teléfono de contacto a establecer
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @param usuarioId el identificador numérico del {@link Usuario} a establecer
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();

        result = prime * result + usuarioId;

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!super.equals(obj))
            return false;

        if (getClass() != obj.getClass())
            return false;

        Profesional other = (Profesional) obj;

        if (usuarioId != other.usuarioId)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Profesional [id=" + getId() + ", nombre=" + nombre + ", email=" + getEmail() + ", telefono=" + telefono
                + ", password=" + getPassword() + ", usuarioId=" + usuarioId + ", role=" + getRole() + ", enabled="
                + isEnabled() + "]";
    }

}
