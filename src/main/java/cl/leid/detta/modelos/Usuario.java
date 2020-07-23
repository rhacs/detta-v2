package cl.leid.detta.modelos;

public class Usuario {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico del {@link Usuario} en la base de datos */
    private int id;

    /** Dirección de correo electrónico del {@link Usuario} */
    private String email;

    /** Contraseña de acceso al sistema del {@link Usuario} */
    private String password;

    /** Rol del {@link Usuario} en el sistema */
    private String role;

    /** Indica si el {@link Usuario} está o no habilitado para acceder al sistema */
    private boolean enabled;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Usuario}
     */
    public Usuario() {

    }

    /**
     * Crea una nueva instancia del objeto {@link Usuario}
     * 
     * @param id       identificador numérico
     * @param email    dirección de correo electrónico
     * @param password contraseña de acceso al sistema
     * @param role     rol en el sistema
     * @param enabled  habilitado o no para acceder al sistema
     */
    public Usuario(int id, String email, String password, String role, boolean enabled) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
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
     * @return la dirección de correo electrónico
     */
    public String getEmail() {
        return email;
    }

    /**
     * @return la contraseña de acceso
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return el rol del {@link Usuario} en el sistema
     */
    public String getRole() {
        return role;
    }

    /**
     * @return si el usuario está o no habilitado para acceder al sistema
     */
    public boolean isEnabled() {
        return enabled;
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
     * @param email la dirección de correo electrónico a establecer
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @param password la contraseña de acceso a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param role el rol a establecer
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @param enabled si el usuario está o no habilitado para acceder al sistema
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + ((email == null) ? 0 : email.hashCode());
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

        Usuario other = (Usuario) obj;

        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;

        if (id != other.id)
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Usuario [id=" + id + ", email=" + email + ", role=" + role + ", enabled=" + enabled + "]";
    }

}
