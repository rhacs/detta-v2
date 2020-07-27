package cl.leid.detta;

public class Constantes {

    // Tablas
    // -----------------------------------------------------------------------------------------

    /** Tabla con la información de los {@link Usuario}s */
    public static final String TABLA_USUARIOS = "detta_usuarios";

    /** Tabla con la información de los roles de los {@link Usuario}s */
    public static final String TABLA_ROLES = "detta_usuarios_roles";

    /** Tabla con la información de los {@link Profesional}es */
    public static final String TABLA_PROFESIONALES = "detta_profesionales";

    /** Tabla con la información de los {@link Cliente}s */
    public static final String TABLA_CLIENTES = "detta_clientes";

    /** Tabla con la información de los {@link Accidente}s */
    public static final String TABLA_ACCIDENTES = "detta_accidentes";

    /** Tabla con la información de las {@link Accion}es */
    public static final String TABLA_ACCIONES = "detta_acciones";

    // Secuencias
    // -----------------------------------------------------------------------------------------

    /** Secuencia generadora de IDs para {@link Usuario} */
    public static final String SECUENCIA_USUARIOS = "detta_usuarios_sq";

    /** Secuencia generadora de IDs para {@link Rol} */
    public static final String SECUENCIA_ROLES = "detta_usuarios_roles_sq";

    /** Secuencia generadora de IDs para {@link Profesional} */
    public static final String SECUENCIA_PROFESIONALES = "detta_profesionales_sq";

    /** Secuencia generadora de IDs para {@link Cliente} */
    public static final String SECUENCIA_CLIENTES = "detta_clientes_sq";

    /** Secuencia generadora de IDs para {@link Accidente} */
    public static final String SECUENCIA_ACCIDENTES = "detta_accidentes_sq";

    /** Secuencia generadora de IDs para {@link Accion} */
    public static final String SECUENCIA_ACCIONES = "detta_acciones_sq";

    // Roles
    // -----------------------------------------------------------------------------------------

    /** Rol del Administrador en el sistema */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /** Rol del {@link Profesional} en el sistema */
    public static final String ROLE_STAFF = "ROLE_STAFF";

    /** Rol del {@link Cliente} en el sistema */
    public static final String ROLE_CLIENT = "ROLE_CLIENT";

    // Constructores
    // -----------------------------------------------------------------------------------------

    /**
     * Crea una nueva instancia vacía del objeto {@link Constantes}
     */
    private Constantes() {

    }

}
