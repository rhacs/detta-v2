package cl.leid.detta.modelos;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_USUARIOS)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_USUARIOS, sequenceName = Constantes.SECUENCIA_USUARIOS)
public class Usuario {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico del {@link Usuario} en la base de datos */
    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_USUARIOS, strategy = GenerationType.SEQUENCE)
    @Column(name = "usuario_id", nullable = false, unique = true, updatable = false)
    private int id;

    /** Dirección de correo electrónico del {@link Usuario} */
    @Column(name = "email", nullable = false, unique = true, updatable = false)
    @Email
    @Size(min = 5, max = 250)
    private String email;

    /** Contraseña de acceso al sistema del {@link Usuario} */
    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    /** Indica si el {@link Usuario} está o no habilitado para acceder al sistema */
    @Column(name = "enabled", nullable = false, updatable = true)
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Rol rol;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Usuario}
     */
    public Usuario() {
        this.rol = new Rol();
    }

    /**
     * Crea una nueva instancia del objeto {@link Usuario}
     * 
     * @param id       identificador numérico
     * @param email    dirección de correo electrónico
     * @param password contraseña
     * @param enabled  si se le permitirá ({@code true}) o no ({@code false}) el
     *                 acceso al sistema
     * @param rol      {@link Rol}
     */
    public Usuario(int id, String email, String password, boolean enabled, Rol rol) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.rol = rol;
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
     * @return la contraseña
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return si el {@link Usuario} está o no habilitado para acceder al sistema
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @return el {@link Rol}
     */
    public Rol getRol() {
        return rol;
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
     * @param password la contraseña a establecer
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param enabled si se le permitirá al {@link Usuario} ingresar ({@code true})
     *                o no ({@code false}) al sistema
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @param rol el {@link Rol} a establecer
     */
    public void setRol(Rol rol) {
        this.rol = rol;
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
        return "Usuario [id=" + id + ", email=" + email + ", enabled=" + enabled + ", rol=" + rol + "]";
    }

}
