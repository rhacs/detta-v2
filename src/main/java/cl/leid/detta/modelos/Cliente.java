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

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_CLIENTES)
@SequenceGenerator(allocationSize = 1, initialValue = 1, name = Constantes.SECUENCIA_CLIENTES, sequenceName = Constantes.SECUENCIA_CLIENTES)
public class Cliente {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_CLIENTES, strategy = GenerationType.SEQUENCE)
    @Column(name = "cliente_id", nullable = false, unique = true, updatable = false)
    private int id;

    /** Nombre o Razón Social del {@link Cliente} */
    @Column(name = "nombre", nullable = false)
    private String nombre;

    /** Rol Único Tributario del {@link Cliente} */
    @Column(name = "rut", nullable = false, unique = true, updatable = false)
    private String rut;

    /** Teléfono de contacto del {@link Cliente} */
    @Column(name = "telefono", nullable = false)
    private String telefono;

    /** Giro o Actividad Económica del {@link Cliente} */
    @Column(name = "giro", nullable = false)
    private String giro;

    /** Cantidad de empleados que trabajan para el {@link Cliente} */
    @Column(name = "empleados", nullable = false)
    private int empleados;

    /**
     * Tipo de empresa. Posibles valores
     * <ul>
     * <li>1: Principal</li>
     * <li>2: Contratista</li>
     * <li>3: Subcontratista</li>
     * <li>4: Servicios Transitorios</li>
     * </ul>
     */
    @Column(name = "tipo", nullable = false)
    private int tipo;

    /** Objeto {@link Usuario} con la información de acceso al sistema */
    @OneToOne(cascade = CascadeType.ALL, optional = false, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Cliente}
     */
    public Cliente() {

    }

    /**
     * Crea una nueva instancia del objeto {@link Cliente}
     * 
     * @param id        identificador numérico
     * @param nombre    nombre o razón social
     * @param rut       rol único tributario
     * @param telefono  teléfono de contacto
     * @param giro      giro o actividad económica
     * @param empleados cantidad de empleados
     * @param tipo      {@link #tipo} de empresa
     * @param usuario   objeto {@link Usuario} relacionado con el {@link Cliente}
     */
    public Cliente(int id, String nombre, String rut, String telefono, String giro, int empleados, int tipo,
            Usuario usuario) {
        this.id = id;
        this.nombre = nombre;
        this.rut = rut;
        this.telefono = telefono;
        this.giro = giro;
        this.empleados = empleados;
        this.tipo = tipo;
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
     * @return el {@link #tipo} de empresa
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @return el {@link Usuario} relacionado
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
     * @param tipo el {@link #tipo} a establecer
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
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
        int result = super.hashCode();

        result = prime * result + id;
        result = prime * result + ((rut == null) ? 0 : rut.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());

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

        if (id != other.id)
            return false;

        if (rut == null) {
            if (other.rut != null)
                return false;
        } else if (!rut.equals(other.rut))
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
        return "Cliente [id=" + id + ", nombre=" + nombre + ", rut=" + rut + ", telefono=" + telefono + ", giro=" + giro
                + ", empleados=" + empleados + ", tipo=" + tipo + ", usuario=" + usuario + "]";
    }

}
