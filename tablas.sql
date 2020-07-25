----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_usuarios (
    usuario_id NUMBER NOT NULL,
    email NVARCHAR2(150) NOT NULL,
    password NVARCHAR2(250) NOT NULL,
    enabled CHAR(1) NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_usuarios_pk PRIMARY KEY (usuario_id),

    -- Columnas únicas
    CONSTRAINT detta_usuarios_uq UNIQUE (email)
);

-- Secuencia
CREATE SEQUENCE detta_usuarios_seq
    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_usuarios_trg
    BEFORE INSERT ON detta_usuarios FOR EACH ROW
BEGIN
    :new.usuario_id := detta_usuarios_seq.nextval;
END;
/

----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios_roles
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_usuarios_roles (
    rol_id NUMBER NOT NULL,
    email NVARCHAR2(150) NOT NULL,
    role NVARCHAR2(20) NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_usuarios_roles_pk PRIMARY KEY (rol_id),

    -- Llave foránea
    CONSTRAINT detta_usuarios_roles_fk FOREIGN KEY (email)
        REFERENCES detta_usuarios (email) ON DELETE CASCADE,

    -- Columnas únicas
    CONSTRAINT detta_usuarios_roles_uq UNIQUE (email, role)
);

-- Secuencia
CREATE SEQUENCE detta_usuarios_roles_sq
    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_usuarios_roles_tr
    BEFORE INSERT ON detta_usuarios_roles FOR EACH ROW
BEGIN
    :new.rol_id := detta_usuarios_roles_sq.nextval;
END;
/

----------------------------------------------------------------------------------------------------
-- Tabla: detta_acciones
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_acciones (
    accion_id NUMBER NOT NULL,
    email NVARCHAR2(150) NOT NULL,
    detalles NVARCHAR2(1000) NOT NULL,
    categoria NUMBER(1) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_acciones_pk PRIMARY KEY (accion_id),

    -- Llave foránea
    CONSTRAINT detta_acciones_fk FOREIGN KEY (email)
        REFERENCES detta_usuarios (email) ON DELETE CASCADE
);

-- Secuencia
CREATE SEQUENCE detta_acciones_sq
    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_acciones_tr
    BEFORE INSERT ON detta_acciones FOR EACH ROW
BEGIN
    :new.accion_id := detta_acciones_sq.nextval;
END;
/

----------------------------------------------------------------------------------------------------
-- Tabla: detta_profesionales
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_profesionales (
    profesional_id NUMBER NOT NULL,
    nombre NVARCHAR2(100) NOT NULL,
    telefono NVARCHAR2(20) NOT NULL,
    usuario_id NUMBER NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_profesionales_pk PRIMARY KEY (profesional_id),

    -- Llave foránea
    CONSTRAINT detta_profesionales_fk FOREIGN KEY (usuario_id)
        REFERENCES detta_usuarios (id) ON DELETE CASCADE,

    -- Columnas únicas
    CONSTRAINT detta_profesionales_uq UNIQUE (usuario_id)
);

-- Secuencia
CREATE SEQUENCE detta_profesionales_sq
    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_profesionales_tr
    BEFORE INSERT ON detta_profesionales FOR EACH ROW
BEGIN
	:new.profesional_id := detta_profesionales_sq.nextval;
END;
/

----------------------------------------------------------------------------------------------------
-- Tabla: detta_clientes
----------------------------------------------------------------------------------------------------

CREATE TABLE detta_clientes (
    cliente_id NUMBER NOT NULL,
    nombre NVARCHAR2(250) NOT NULL,
    rut NVARCHAR2(50) NOT NULL,
    telefono NVARCHAR2(20) NOT NULL,
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
CREATE SEQUENCE detta_clientes_sq
    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_clientes_tr
    BEFORE INSERT ON detta_clientes FOR EACH ROW
BEGIN
    :new.cliente_id := detta_clientes_sq.nextval;
END;
/

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
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cliente_id NUMBER NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_accidentes_pk PRIMARY KEY (accidente_id),

    -- Llaves foráneas
    CONSTRAINT detta_accidentes_fk FOREIGN KEY (cliente_id)
        REFERENCES detta_clientes (cliente_id) ON DELETE CASCADE,
);

-- Secuencia
CREATE SEQUENCE detta_accidentes_sq
    START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE ORDER;

-- Dispara usted o disparo yo
CREATE OR REPLACE TRIGGER detta_accidentes_tr
    BEFORE INSERT ON detta_accidentes FOR EACH ROW
BEGIN
    :new.accidente_id := detta_accidentes_sq.nextval;
END;
/
