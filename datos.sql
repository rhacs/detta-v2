----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios
----------------------------------------------------------------------------------------------------

INSERT INTO detta_usuarios (email, password, enabled) VALUES ('admin@detta.cl', '$2a$10$YW.QNtpYrJouwMMrmlT28OPxbXzxL2gUGFmbg8THUXaYXRKmcqrjW', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.uno@detta.cl', '$2a$10$JZvFcnU.ku4sDHCtBjGIOetcK506a0kq5KzvfTkTe7DNYlDKfwbsq', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.dos@detta.cl', '$2a$10$WfDYhKcRY/QxPAldeES6Q.ih5zSO9GwyNrCjkBWSU6QzLnzXAWjT2', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.tres@detta.cl', '$2a$10$bEjiVk9QTYyYrgW16ejyKeYwCeEB5jJuwKkzBqiGKzulvHrCppgua', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.cuatro@detta.cl', '$2a$10$5Nivt0GKMRId.174ZI77fudTFfCoiti/hL1xhpiH/QFhvfSXlOgW2', '0');

----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios_roles
----------------------------------------------------------------------------------------------------

INSERT INTO detta_usuarios_roles (email, role) VALUES ('admin@detta.cl', 'ROLE_ADMIN');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.uno@detta.cl', 'ROLE_STAFF');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.dos@detta.cl', 'ROLE_STAFF');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.tres@detta.cl', 'ROLE_STAFF');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.cuatro@detta.cl', 'ROLE_STAFF');

----------------------------------------------------------------------------------------------------
-- Tabla: detta_profesionales
----------------------------------------------------------------------------------------------------

INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Uno', '123456789', 2);
INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Dos', '234567891', 3);
INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Tres', '345678912', 4);
INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Cuatro', '456789123', 5);
