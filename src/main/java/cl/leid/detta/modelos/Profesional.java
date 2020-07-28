package cl.leid.detta.modelos;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_PROFESIONALES)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_PROFESIONALES, sequenceName = Constantes.SECUENCIA_PROFESIONALES)
public class Profesional {

    // Atributos
    // -----------------------------------------------------------------------------------------

    /** Identificador numérico del {@link Profesional} en la base de datos */
    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_PROFESIONALES, strategy = GenerationType.SEQUENCE)
    @Column(name = "profesional_id", nullable = false, unique = true, updatable = false)
    private int id;

    /** Nombre completo del {@link Profesional} */
    @Column(name = "nombre", nullable = false)
    @Size(min = 5, max = 100)
    private String nombre;

    /** Teléfono de contacto del {@link Profesional} */
    @Column(name = "telefono", nullable = false)
    @Size(min = 7, max = 20)
    @Pattern(regexp = "[0-9]+")
    private String telefono;

    /** Objeto {@link Usuario} con la información de acceso al sistema */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", unique = true, updatable = false)
    @Valid
    private Usuario usuario;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Profesional}
     */
    public Profesional() {
        // this.usuario = new Usuario();
    }

    /**
     * Crea una nueva instancia del objeto {@link Profesional}
     * 
     * @param id       identificador numérico
     * @param nombre   nombre completo
     * @param telefono teléfono de contacto
     * @param usuario  objeto {@link Usuario} relacionado
     */
    public Profesional(int id, String nombre, String telefono, Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
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

        Profesional other = (Profesional) obj;

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
        return "Profesional [id=" + id + ", nombre=" + nombre + ", telefono=" + telefono + ", usuario=" + usuario + "]";
    }

}
