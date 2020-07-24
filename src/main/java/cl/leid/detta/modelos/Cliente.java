package cl.leid.detta.modelos;

public class Cliente extends Usuario {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Nombre o Razón Social del {@link Cliente} */
    private String nombre;

    /** Rol Único Tributario del {@link Cliente} */
    private String rut;

    /** Teléfono de contacto del {@link Cliente} */
    private String telefono;

    /** Giro o Actividad Económica del {@link Cliente} */
    private String giro;

    /** Cantidad de empleados que trabajan para el {@link Cliente} */
    private int empleados;

    /**
     * Tipo de empresa (1:Principal, 2:Contratista, 3:Subcontratista, 4:De Servicios
     * Transitorios)
     */
    private int tipo;

    /**
     * Identificador numérico del {@link Usuario} relacionado con el {@link Cliente}
     */
    private int usuarioId;

    /**
     * Identificador numérico del {@link Profesional} que está a cargo del
     * {@link Cliente}
     */
    private int profesionalId;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Cliente}
     */
    public Cliente() {

    }

    // Getters
    // -----------------------------------------------------------------------------------------

    /**
     * @return el nombre o razón social
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return el rol único tributario
     */
    public String getRut() {
        return rut;
    }

    /**
     * @return el teléfono de contacto
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @return el giro o actividad económica
     */
    public String getGiro() {
        return giro;
    }

    /**
     * @return la cantidad de empleados
     */
    public int getEmpleados() {
        return empleados;
    }

    /**
     * @return el tipo de empresa
     * @see Cliente#tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @return el identificador numérico del {@link Usuario}
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    /**
     * @return el identificador numérico del {@link Profesional}
     */
    public int getProfesionalId() {
        return profesionalId;
    }

    // Setters
    // -----------------------------------------------------------------------------------------

    /**
     * @param nombre el nombre o razón social a establecer
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param rut el rol único tributario a establecer
     */
    public void setRut(String rut) {
        this.rut = rut;
    }

    /**
     * @param telefono el teléfono de contacto a establecer
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @param giro el giro o actividad económica a establecer
     */
    public void setGiro(String giro) {
        this.giro = giro;
    }

    /**
     * @param empleados la cantidad de empleados a establecer
     */
    public void setEmpleados(int empleados) {
        this.empleados = empleados;
    }

    /**
     * @param tipo el tipo de empresa a establecer
     * @see Cliente#tipo
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @param usuarioId el identificador numérico del {@link Usuario} a establecer
     */
    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    /**
     * @param profesionalId el identificador numérico del {@link Profesional} a
     *                      establecer
     */
    public void setProfesionalId(int profesionalId) {
        this.profesionalId = profesionalId;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();

        result = prime * result + ((rut == null) ? 0 : rut.hashCode());
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

        Cliente other = (Cliente) obj;

        if (rut == null) {
            if (other.rut != null)
                return false;
        } else if (!rut.equals(other.rut))
            return false;

        if (usuarioId != other.usuarioId)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Cliente [nombre=" + nombre + ", rut=" + rut + ", telefono=" + telefono + ", giro=" + giro
                + ", empleados=" + empleados + ", tipo=" + tipo + ", usuarioId=" + usuarioId + ", profesionalId="
                + profesionalId + ", getEmail()=" + getEmail() + ", getRole()=" + getRole() + ", isEnabled()="
                + isEnabled() + "]";
    }

}
