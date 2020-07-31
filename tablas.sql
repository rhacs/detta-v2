----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_usuarios (
    usuario_id NUMBER NOT NULL,
    nombre NVARCHAR2(250) NOT NULL,
    telefono NVARCHAR2(50) NOT NULL,
    email NVARCHAR2(150) NOT NULL,
    password NVARCHAR2(250) NOT NULL,
    enabled NUMBER(1,0) NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_usuarios_pk PRIMARY KEY (usuario_id),

    -- Columnas únicas
    CONSTRAINT detta_usuarios_uq UNIQUE (email)
);

-- Secuencia
CREATE SEQUENCE detta_usuarios_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios_roles
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_usuarios_roles (
    rol_id NUMBER NOT NULL,
    role NVARCHAR2(20) NOT NULL,
    usuario_id NUMBER NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_usuarios_roles_pk PRIMARY KEY (rol_id),

    -- Llave foránea
    CONSTRAINT detta_usuarios_roles_fk FOREIGN KEY (usuario_id)
        REFERENCES detta_usuarios (usuario_id) ON DELETE CASCADE,

    -- Columnas únicas
    CONSTRAINT detta_usuarios_roles_uq UNIQUE (usuario_id, role)
);

-- Secuencia
CREATE SEQUENCE detta_usuarios_roles_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_acciones
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_acciones (
    accion_id NUMBER NOT NULL,
    detalles NVARCHAR2(1000) NOT NULL,
    categoria NUMBER(1) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    usuario_id NUMBER NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_acciones_pk PRIMARY KEY (accion_id),

    -- Llave foránea
    CONSTRAINT detta_acciones_fk FOREIGN KEY (usuario_id)
        REFERENCES detta_usuarios (usuario_id) ON DELETE CASCADE
);

-- Secuencia
CREATE SEQUENCE detta_acciones_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_profesionales
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_profesionales (
    profesional_id NUMBER NOT NULL,
    usuario_id NUMBER NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_profesionales_pk PRIMARY KEY (profesional_id),

    -- Llave foránea
    CONSTRAINT detta_profesionales_fk FOREIGN KEY (usuario_id)
        REFERENCES detta_usuarios (usuario_id) ON DELETE CASCADE,

    -- Columnas únicas
    CONSTRAINT detta_profesionales_uq UNIQUE (usuario_id)
);

-- Secuencia
CREATE SEQUENCE detta_profesionales_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_clientes
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_clientes (
    cliente_id NUMBER NOT NULL,
    rut NVARCHAR2(50) NOT NULL,
    giro NVARCHAR2(100) NOT NULL,
    empleados NUMBER NOT NULL,
    tipo NUMBER(1,0) NOT NULL,
    usuario_id NUMBER NOT NULL,
    profesional_id NUMBER DEFAULT NULL NULL,

    -- Llave primaria
    CONSTRAINT detta_clientes_pk PRIMARY KEY (cliente_id),

    -- Llaves foráneas
    CONSTRAINT detta_clientes_fku FOREIGN KEY (usuario_id)
        REFERENCES detta_usuarios (usuario_id) ON DELETE CASCADE,
    CONSTRAINT detta_clientes_fkp FOREIGN KEY (profesional_id)
        REFERENCES detta_profesionales (profesional_id) ON DELETE SET NULL,

    -- Columnas únicas
    CONSTRAINT detta_clientes_uq UNIQUE (usuario_id, rut)
);

-- Secuencia
CREATE SEQUENCE detta_clientes_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_accidentes
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_accidentes (
    accidente_id NUMBER NOT NULL,
    fecha DATE NOT NULL,
    hora NVARCHAR2(5) NOT NULL,
    direccion NVARCHAR2(250) NOT NULL,
    lugar NVARCHAR2(250) NOT NULL,
    circunstancia NVARCHAR2(250) NOT NULL,
    detalles NVARCHAR2(2000) NOT NULL,
    clasificacion NUMBER(1) NOT NULL,
    tipo NUMBER(1) NOT NULL,
    evidencia NUMBER(1) NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cliente_id NUMBER NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_accidentes_pk PRIMARY KEY (accidente_id),

    -- Llaves foráneas
    CONSTRAINT detta_accidentes_fk FOREIGN KEY (cliente_id)
        REFERENCES detta_clientes (cliente_id) ON DELETE CASCADE
);

-- Secuencia
CREATE SEQUENCE detta_accidentes_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_capacitaciones
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_capacitaciones (
    capacitacion_id NUMBER NOT NULL,
    tipo NVARCHAR2(50) NOT NULL,
    fecha DATE NOT NULL,
    hora NVARCHAR2(5) NOT NULL,
    objetivos NVARCHAR2(250) NOT NULL,
    direccion NVARCHAR2(150) NOT NULL,
    duracion NVARCHAR2(5) NOT NULL,
    contenido NVARCHAR2(1000) NOT NULL,
    estado NUMBER(1,0) DEFAULT 1 NOT NULL,
    cliente_id NUMBER NOT NULL,
    profesional_id NUMBER NULL,

    -- Llave primaria
    CONSTRAINT detta_capacitaciones_pk PRIMARY KEY ( capacitacion_id ),

    -- Llaves foráneas
    CONSTRAINT detta_capacitaciones_fku FOREIGN KEY (cliente_id)
        REFERENCES detta_clientes (cliente_id) ON DELETE CASCADE,
    CONSTRAINT detta_capacitaciones_fkp FOREIGN KEY (profesional_id)
        REFERENCES detta_profesionales (profesional_id) ON DELETE SET NULL
);

-- Secuencia
CREATE SEQUENCE detta_capacitaciones_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_asesorias
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_asesorias (
    asesoria_id NUMBER NOT NULL,
    tema NVARCHAR2(250) NOT NULL,
    descripcion NVARCHAR2(1000) NOT NULL,
    fiscalizador NVARCHAR2(250) NULL,
    departamento NVARCHAR2(250) NOT NULL,
    estado NUMBER(1,0) NOT NULL,
    fecha DATE NOT NULL,
    hora NVARCHAR2(5) NOT NULL,
    direccion NVARCHAR2(250) NOT NULL,
    cliente_id NUMBER NOT NULL,
    profesional_id NUMBER NULL,

    -- Llave primaria
    CONSTRAINT detta_asesorias_pk PRIMARY KEY (asesoria_id),

    -- Llaves foráneas
    CONSTRAINT detta_asesorias_fkc FOREIGN KEY (cliente_id)
        REFERENCES detta_clientes (cliente_id) ON DELETE CASCADE,
    CONSTRAINT detta_asesorias_fkp FOREIGN KEY (profesional_id)
        REFERENCES detta_profesionales (profesional_id) ON DELETE SET NULL
);

-- Secuencia
CREATE SEQUENCE detta_asesorias_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

----------------------------------------------------------------------------------------------------
-- Tabla: detta_visitas
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_visitas (
    visita_id NUMBER NOT NULL,
    fecha DATE NOT NULL,
    hora NVARCHAR2(5) NOT NULL,
    direccion NVARCHAR2(150) NOT NULL,
    motivo NUMBER(1,0) NOT NULL,
    estado NUMBER(1,0) NOT NULL,
    asesoria_id NUMBER NOT NULL,

    -- Llave Primaria
    CONSTRAINT detta_visitas_pk PRIMARY KEY (visita_id),

    -- Llave Foránea
    CONSTRAINT detta_visitas_fk FOREIGN KEY (asesoria_id)
        REFERENCES detta_asesorias (asesoria_id) ON DELETE CASCADE
);

-- Secuencia
CREATE SEQUENCE detta_visitas_sq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;
