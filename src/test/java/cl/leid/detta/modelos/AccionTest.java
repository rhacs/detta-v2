package cl.leid.detta.modelos;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccionTest {

    private Accion accion;

    private int id;
    private String email;
    private String detalles;
    private int categoria;
    private Timestamp timestamp;

    @BeforeEach
    void setUp() throws Exception {
        id = 1;
        email = "test@test.test";
        detalles = "detalles";
        categoria = 1;
        timestamp = new Timestamp(System.currentTimeMillis());

        accion = new Accion(id, email, detalles, categoria, timestamp);
    }

    @Test
    void testGetId() {
        assertEquals(id, accion.getId());
    }

    @Test
    void testGetEmail() {
        assertEquals(email, accion.getEmail());
    }

    @Test
    void testGetDetalles() {
        assertEquals(detalles, accion.getDetalles());
    }

    @Test
    void testGetCategoria() {
        assertEquals(categoria, accion.getCategoria());
    }

    @Test
    void testGetTimestamp() {
        assertEquals(timestamp, accion.getTimestamp());
    }

    @Test
    void testSetId() {
        int id = 2;
        accion.setId(id);

        assertEquals(id, accion.getId());
    }

    @Test
    void testSetEmail() {
        String email = "another@test.test";
        accion.setEmail(email);

        assertEquals(email, accion.getEmail());
    }

    @Test
    void testSetDetalles() {
        String detalles = "another detail";
        accion.setDetalles(detalles);

        assertEquals(detalles, accion.getDetalles());
    }

    @Test
    void testSetCategoria() {
        int categoria = 2;
        accion.setCategoria(categoria);

        assertEquals(categoria, accion.getCategoria());
    }

    @Test
    void testSetTimestamp() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        accion.setTimestamp(ts);

        assertEquals(ts, accion.getTimestamp());
    }

}
