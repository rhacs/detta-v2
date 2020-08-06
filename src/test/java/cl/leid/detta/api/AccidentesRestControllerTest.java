package cl.leid.detta.api;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cl.leid.detta.modelos.Accidente;
import cl.leid.detta.modelos.Cliente;
import cl.leid.detta.repositorios.ClientesRepositorio;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
@WebAppConfiguration
class AccidentesRestControllerTest {

    private MockMvc mvc;
    private Accidente accidente;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ClientesRepositorio clientesRepositorio;

    @BeforeEach
    void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

        accidente = new Accidente();
        accidente.setFecha(LocalDate.now());
        accidente.setHora("11:30");
        accidente.setDireccion("Una dirección cualquiera");
        accidente.setLugar("Un lugar cualquiera");
        accidente.setCircunstancia("Una circunstancia cualquiera");
        accidente.setDetalles("Lo que pasó y provocó el accidente que pudo ser evitado");
        accidente.setClasificacion(2);
        accidente.setTipo(1);
        accidente.setEvidencia(3);

        // Cliente
        Cliente cliente = clientesRepositorio.getOne(2);

        accidente.setCliente(cliente);
        System.out.println(accidente);
    }

    @Test
    void verListadoShouldReturnAnArray() throws Exception {
        mvc.perform(get("/api/accidentes").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$[1].id", is(121)));
    }

    @Test
    void verDetallesElementShouldExist() throws Exception {
        mvc.perform(get("/api/accidentes/{id}", 4).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fecha").exists());
    }

    @Test
    void verDetallesElementShouldNotExist() throws Exception {
        mvc.perform(get("/api/accidentes/{id}", 1000).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound()).andExpect(jsonPath("$.httpStatus").isString())
                .andExpect(jsonPath("$.httpStatus").value("NOT_FOUND"));
    }

    @Test
    void agregarRegistroTest() throws Exception {
        mvc.perform(post("/api/accidentes").with(user("admin@detta.cl").password("admin@detta.cl").roles("ADMIN"))
                .with(csrf()).contentType(MediaType.APPLICATION_JSON).content(new JSONObject(accidente).toString()))
                .andDo(print()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id").exists());
    }

}
