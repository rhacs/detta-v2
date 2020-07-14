----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios
----------------------------------------------------------------------------------------------------
CREATE TABLE detta_usuarios (
    id NUMBER NOT NULL,
    email VARCHAR2(150 CHAR) NOT NULL,
    password VARCHAR2(250 CHAR) NOT NULL,
    habilitado CHAR(1) NOT NULL
);
/

-- Llave primaria
ALTER TABLE detta_usuarios
    ADD CONSTRAINT detta_usuarios_pk PRIMARY KEY (id);
    
-- Valores únicos
ALTER TABLE detta_usuarios
    ADD CONSTRAINT detta_usuarios_uq UNIQUE (email);

-- Secuencia
CREATE SEQUENCE detta_usuarios_seq START WITH 1 NOCACHE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_usuarios_trg BEFORE
    INSERT ON detta_usuarios
    FOR EACH ROW
BEGIN
    :new.id := detta_usuarios_seq.nextval;
END;
/

----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios_roles
----------------------------------------------------------------------------------------------------
CREATE TABLE detta_usuarios_roles (
    id NUMBER NOT NULL,
    email VARCHAR2(150 CHAR) NOT NULL,
    rol VARCHAR2(20) NOT NULL
);
/

-- Llave primaria
ALTER TABLE detta_usuarios_roles
    ADD CONSTRAINT detta_usuarios_roles_pk PRIMARY KEY (id);

-- Valores únicos
ALTER TABLE detta_usuarios_roles
    ADD CONSTRAINT detta_usuarios_roles_uq UNIQUE (email, rol);

-- Referencia de llave foránea
ALTER TABLE detta_usuarios_roles
    ADD CONSTRAINT detta_usuarios_roles_fk FOREIGN KEY (email)
        REFERENCES detta_usuarios (email);

-- Secuencia
CREATE SEQUENCE detta_usuarios_roles_seq START WITH 1 NOCACHE ORDER;

-- Disparador
CREATE OR REPLACE TRIGGER detta_usuarios_roles_trg BEFORE
    INSERT ON detta_usuarios_roles
    FOR EACH ROW
BEGIN
    :new.id := detta_usuarios_roles_seq.nextval;
END;
/
