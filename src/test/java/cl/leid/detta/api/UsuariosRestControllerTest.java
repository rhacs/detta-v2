package cl.leid.detta.api;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/servlet-context.xml",
        "file:src/main/webapp/WEB-INF/spring/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring/spring-security.xml" })
@WebAppConfiguration
class UsuariosRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void verListadoWithAdminShouldReturnAList() throws Exception {
        mvc.perform(get("/api/usuarios").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$[0].email").value("admin@detta.cl"));
    }

    @Test
    @WithAnonymousUser
    void verListadoWithAnonShouldRedirect() throws Exception {
        mvc.perform(get("/api/usuarios").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "STAFF")
    void verListadoWithStaffShouldRedirect() throws Exception {
        mvc.perform(get("/api/usuarios").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void verDetallesShouldReturnAnObject() throws Exception {
        mvc.perform(get("/api/usuarios/{id}", 4).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("profesional.tres@detta.cl"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void verDetallesShouldReturnNotFound() throws Exception {
        mvc.perform(get("/api/usuarios/{id}", 1000).contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void agregarRegistroWithEmptyBodyShouldReturnBadRequest() throws Exception {
        mvc.perform(post("/api/usuarios").contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest());
    }

}
