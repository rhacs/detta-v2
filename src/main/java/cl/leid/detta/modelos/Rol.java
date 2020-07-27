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

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_ROLES)
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = Constantes.SECUENCIA_ROLES)
public class Rol {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico del {@link Rol} en la base de datos */
    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_ROLES, strategy = GenerationType.SEQUENCE)
    @Column(name = "rol_id", nullable = false, unique = true, updatable = false)
    private int id;

    /**
     * Rol que cumple el {@link Usuario} en el sistema. Posibles valores:
     * <ul>
     * <li>'ROLE_ADMIN' administrador</li>
     * <li>'ROLE_STAFF' profesional</li>
     * <li>'ROLE_CLIENT' cliente</li>
     * </ul>
     */
    @Column(name = "role", nullable = false, updatable = false)
    private String role;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "email", optional = false, orphanRemoval = true)
    private Usuario usuario;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Rol}
     */
    public Rol() {

    }

    /**
     * Crea una nueva instancia del objeto {@link Rol}
     * 
     * @param id      identificador numérico
     * @param role    rol que cumple el usuario en el sistema
     * @param usuario objeto {@link Usuario}
     */
    public Rol(int id, String role, Usuario usuario) {
        this.id = id;
        this.role = role;
        this.usuario = usuario;
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
     * @return el rol que cumple en el sistema
     * @see Rol#role
     */
    public String getRole() {
        return role;
    }

    /**
     * @return el {@link Usuario}
     */
    public Usuario getUsuario() {
        return usuario;
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
     * @param role el rol a establecer
     * @see Rol#role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * @param usuario el {@link Usuario} a establecer
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // Herencias (Object)
    // -----------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + id;
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());

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

        Rol other = (Rol) obj;

        if (id != other.id)
            return false;

        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;

        return true;
    }

    @Override
    public String toString() {
        return "Rol [id=" + id + ", role=" + role + "]";
    }

}
