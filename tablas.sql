----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios

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

CREATE TABLE detta_usuarios_roles (
    rol_id NUMBER NOT NULL,
    email NVARCHAR2(150) NOT NULL,
    role NVARCHAR2(20) NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_usuarios_roles_pk PRIMARY KEY (rol_id),

    -- Llave foránea
    CONSTRAINT detta_usuarios_roles_fk FOREIGN KEY (email) REFERENCES detta_usuarios (email),

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

CREATE TABLE detta_acciones (
    accion_id NUMBER NOT NULL,
    email NVARCHAR2(150) NOT NULL,
    codigo NVARCHAR2(1000) NOT NULL,
    categoria NUMBER(1) NOT NULL,

    -- Llave primaria
    CONSTRAINT detta_acciones_pk PRIMARY KEY (accion_id),

    -- Llave foránea
    CONSTRAINT detta_acciones_fk FOREIGN KEY (email) REFERENCES detta_usuarios (email)
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
