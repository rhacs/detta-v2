----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios
----------------------------------------------------------------------------------------------------

INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('admin@detta.cl', '$2a$10$dpafuX88Pq8WKRSw8EoGm.UJqNZ9LoU/AcL8eP/fvdsuEghFGKcA2', '1');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('pro.uno@detta.cl', '$2a$10$SeGtKLj3gNgmDA20ZWrlZOAz35Oa1i9zmF86HPsXPOEINghqbcCtm', '1');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('pro.dos@detta.cl', '$2a$10$sOhI80Ii4NK9PemZ78H36uk5UIY78G.QI65ghlib2E54qlXsToBG6', '1');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('cliente@empresa.uno', '$2a$10$EjGdjZ0UR3nBZZ94WVGIZOS7n2qQML3uyqCaXEcz2HWkufh0GIsxm', '1');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('cliente@empresa.dos', '$2a$10$Qa00uhAGogwrmpzb6wB01OdJec9UVHc61qPGR1fAiBD5E64z7Coxy', '1');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('cliente@empresa.tres', '$2a$10$UZ7RYL8X1p0LLCi2gShlbu2zqVD7.pbdTLUZHwamA/pYj5ioxnYdy', '0');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('cliente@empresa.cuatro', '$2a$10$mGzXz4pOCocp.dCctc4/O.OHW/Kb2Z5i14KvO/PVpzVSxLs0U05AO', '0');
INSERT INTO detta_usuarios (email, password, habilitado) VALUES ('cliente@empresa.cinco', '$2a$10$lCqFEtFqsVxT0fNaIROR/up6k/ZAD3LxI7nvUeH2lRyhNFr7W.i4a', '1');

----------------------------------------------------------------------------------------------------
-- Tabla: detta_usuarios_roles
----------------------------------------------------------------------------------------------------

INSERT INTO detta_usuarios_roles (email, rol) VALUES ('admin@detta.cl', 'admin');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('pro.uno@detta.cl', 'profesional');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('pro.dos@detta.cl', 'profesional');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('cliente@empresa.uno', 'cliente');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('cliente@empresa.dos', 'cliente');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('cliente@empresa.tres', 'cliente');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('cliente@empresa.cuatro', 'cliente');
INSERT INTO detta_usuarios_roles (email, rol) VALUES ('cliente@empresa.cinco', 'cliente');
