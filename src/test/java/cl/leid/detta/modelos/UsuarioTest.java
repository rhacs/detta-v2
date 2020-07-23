package cl.leid.detta.modelos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    private Usuario usuario;

    private int id;
    private String email;
    private String password;
    private String role;
    private boolean enabled;

    @BeforeEach
    void setUp() throws Exception {
        id = 1;
        email = "admin@detta.cl";
        password = "$2a$10$wRF9B1YAD6Vi.xLURkT6J.PZMY0J2ihezyndp5bq1WfIKKlKuDxJK";
        role = "ROLE_ADMIN";
        enabled = true;

        usuario = new Usuario(id, email, password, role, enabled);
    }

    @Test
    void testGetId() {
        assertEquals(id, usuario.getId());
    }

    @Test
    void testGetEmail() {
        assertEquals(email, usuario.getEmail());
    }

    @Test
    void testGetPassword() {
        assertEquals(password, usuario.getPassword());
    }

    @Test
    void testGetRole() {
        assertEquals(role, usuario.getRole());
    }

    @Test
    void testIsEnabled() {
        assertEquals(enabled, usuario.isEnabled());
    }

    @Test
    void testSetId() {
        int id = 2;
        usuario.setId(id);

        assertEquals(id, usuario.getId());
    }

    @Test
    void testSetEmail() {
        String email = "another.admin@detta.cl";
        usuario.setEmail(email);

        assertEquals(email, usuario.getEmail());
    }

    @Test
    void testSetPassword() {
        String password = "$2a$10$2Rh1bwr7D6SnFq/ZktirdexvYUyfkX4LFGGJP5MHvDutb2ayCncJS";
        usuario.setPassword(password);

        assertEquals(password, usuario.getPassword());
    }

    @Test
    void testSetRole() {
        String role = "ROLE_STAFF";
        usuario.setRole(role);

        assertEquals(role, usuario.getRole());
    }

    @Test
    void testSetEnabled() {
        boolean enabled = false;
        usuario.setEnabled(enabled);

        assertEquals(enabled, usuario.isEnabled());
    }

}
