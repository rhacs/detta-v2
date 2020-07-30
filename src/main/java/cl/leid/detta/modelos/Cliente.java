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
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import cl.leid.detta.Constantes;

@Entity
@Table(name = Constantes.TABLA_CLIENTES)
@SequenceGenerator(allocationSize = 1, name = Constantes.SECUENCIA_CLIENTES, sequenceName = Constantes.SECUENCIA_CLIENTES)
public class Cliente {

    // Atributos
    // -----------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(generator = Constantes.SECUENCIA_CLIENTES, strategy = GenerationType.SEQUENCE)
    @Column(name = "cliente_id", nullable = false, unique = true, updatable = false)
    private int id;

    /** Rol Único Tributario del {@link Cliente} */
    @Column(name = "rut", nullable = false, unique = true, updatable = false)
    @Size(min = 9, max = 10)
    @Pattern(regexp = "[1-9][0-9]{8,9}")
    private String rut;

    /** Giro o Actividad Económica del {@link Cliente} */
    @Column(name = "giro", nullable = false)
    @Size(min = 5, max = 250)
    private String giro;

    /** Cantidad de empleados que trabajan para el {@link Cliente} */
    @Column(name = "empleados", nullable = false)
    @Min(value = 1)
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
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,
            CascadeType.REMOVE }, optional = false, orphanRemoval = true)
    @JoinColumn(name = "usuario_id")
    @Valid
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "profesional_id", referencedColumnName = "profesional_id")
    private Profesional profesional;

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
     * @param id          identificador numérico
     * @param rut         rol único tributario
     * @param giro        giro o actividad económica
     * @param empleados   cantidad de empleados
     * @param tipo        {@link #tipo} de empresa
     * @param usuario     {@link Usuario} relacionado
     * @param profesional {@link Profesional} relacionado
     */
    public Cliente(int id, @Size(min = 9, max = 10) @Pattern(regexp = "[1-9][0-9]{8,9}") String rut,
            @Size(min = 5, max = 250) String giro, @Min(1) int empleados, int tipo, @Valid Usuario usuario,
            Profesional profesional) {
        this.id = id;
        this.rut = rut;
        this.giro = giro;
        this.empleados = empleados;
        this.tipo = tipo;
        this.usuario = usuario;
        this.profesional = profesional;
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
     * @return el rol único tributario
     */
    public String getRut() {
        return rut;
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

    /**
     * @return el {@link Profesional}
     */
    public Profesional getProfesional() {
        return profesional;
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
     * @param rut el rol único tributario a establecer
     */
    public void setRut(String rut) {
        this.rut = rut;
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

    /**
     * @param profesional el {@link Profesional} a establecer
     */
    public void setProfesional(Profesional profesional) {
        this.profesional = profesional;
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
        return "Cliente [id=" + id + ", rut=" + rut + ", giro=" + giro + ", empleados=" + empleados + ", tipo=" + tipo
                + ", usuario=" + usuario + ", profesional=" + profesional + "]";
    }

}
