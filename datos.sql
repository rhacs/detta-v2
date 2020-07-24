----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios
----------------------------------------------------------------------------------------------------

INSERT INTO detta_usuarios (email, password, enabled) VALUES ('admin@detta.cl', '$2a$10$YW.QNtpYrJouwMMrmlT28OPxbXzxL2gUGFmbg8THUXaYXRKmcqrjW', '1');

INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.uno@detta.cl', '$2a$10$JZvFcnU.ku4sDHCtBjGIOetcK506a0kq5KzvfTkTe7DNYlDKfwbsq', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.dos@detta.cl', '$2a$10$WfDYhKcRY/QxPAldeES6Q.ih5zSO9GwyNrCjkBWSU6QzLnzXAWjT2', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.tres@detta.cl', '$2a$10$bEjiVk9QTYyYrgW16ejyKeYwCeEB5jJuwKkzBqiGKzulvHrCppgua', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('profesional.cuatro@detta.cl', '$2a$10$5Nivt0GKMRId.174ZI77fudTFfCoiti/hL1xhpiH/QFhvfSXlOgW2', '0');

INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.uno', '$2a$10$5WPTogd6NaSQEW8JH5yxw.O77Ch/VHPxNHZJDKtAsU5SNZc6ITaQu', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.dos', '$2a$10$CfhUiOs8xbm1i3wuRtHXw.iVs4phuWS4cjmd.ZFlRj4.GgwbHCm6m', '0');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.tres', '$2a$10$Ow9uR9YBQU9zqgW/.OsTle1sNJKYGh1ICf3I2t6.5hs4dXPFdlwNa', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.cuatro', '$2a$10$aB5/P.K4fFuTUpMSr80xa.017e.z4HC/7YTXkLvyBmDNg8QQPSQle', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.cinco', '$2a$10$DEAeT9FSZZlLfECxJJKQkusTGSMi596d19NlufHUgB.Tj4j3lHziO', '0');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.seis', '$2a$10$47aFWWvfILdM81uAsp5Ne.2FR0GgOpjWDWf5NwlIjFeHNBjBBw1de', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.siete', '$2a$10$xfuMSDnMtLY.2GysKecq9eGQ1pzGwl.bJpNO8./75GnDbg2ccWI2a', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.ocho', '$2a$10$kNM8kzF/E629XnDro1/3xO8VZLFf/4CBeGR6lBtmAbKN9TL/LqmbO', '0');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.nueve', '$2a$10$mQ4ZrUiMU/U8SXxeahhsEeu9.lusq.cD0l82j8etowFlnE5oQY5tG', '1');
INSERT INTO detta_usuarios (email, password, enabled) VALUES ('cliente@empresa.diez', '$2a$10$B3YdPQNBNwcvHvTcybWp0uGidLWx0JihCzghS05JU7khz4jnrKVrO', '1');

----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios_roles
----------------------------------------------------------------------------------------------------

INSERT INTO detta_usuarios_roles (email, role) VALUES ('admin@detta.cl', 'ROLE_ADMIN');

INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.uno@detta.cl', 'ROLE_STAFF');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.dos@detta.cl', 'ROLE_STAFF');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.tres@detta.cl', 'ROLE_STAFF');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('profesional.cuatro@detta.cl', 'ROLE_STAFF');

INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.uno', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.dos', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.tres', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.cuatro', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.cinco', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.seis', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.siete', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.ocho', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.nueve', 'ROLE_CLIENT');
INSERT INTO detta_usuarios_roles (email, role) VALUES ('cliente@empresa.diez', 'ROLE_CLIENT');

----------------------------------------------------------------------------------------------------
-- Tabla: detta_profesionales
----------------------------------------------------------------------------------------------------

INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Uno', '123456789', 2);
INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Dos', '234567891', 3);
INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Tres', '345678912', 4);
INSERT INTO detta_profesionales (nombre, telefono, usuario_id) VALUES ('Profesional Cuatro', '456789123', 5);

----------------------------------------------------------------------------------------------------
-- Tabla: detta_clientes
----------------------------------------------------------------------------------------------------

INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Uno', '111111111', '123456789', 'Construcción', 125, 1, 6, 2);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Dos', '222222222', '234567891', 'Construcción', 85, 2, 7, NULL);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Tres', '333333333', '345678912', 'Aseo', 20, 4, 8, 2);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Cuatro', '444444444', '456789123', 'Minería', 44, 1, 9, 3);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Cinco', '555555555', '567891234', 'Minería', 68, 1, 10, NULL);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Seis', '666666666', '678912345', 'Construcción', 25, 3, 11, 4);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Siete', '777777777', '789123456', 'Aseo', 14, 4, 12, 3);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Ocho', '888888888', '891234567', 'Construcción', 33, 2, 13, NULL);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Nueve', '999999999', '912345678', 'Explosivos', 29, 1, 14, 5);
INSERT INTO detta_clientes (nombre, rut, telefono, giro, empleados, tipo, usuario_id, profesional_id) VALUES ('Cliente Diez', '000000000', '012345678', 'Catástrofes', 2, 1, 15, 2);
