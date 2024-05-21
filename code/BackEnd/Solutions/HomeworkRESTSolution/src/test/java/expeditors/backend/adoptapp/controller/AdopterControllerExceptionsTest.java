package expeditors.backend.adoptapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AdopterControllerExceptionsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetOneWithBadIntegerShouldThrowResourceNotFoundException() throws Exception {
        MockHttpServletRequestBuilder builder = get("/petservice/{id}", "abc")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testPostAdopterWithNullNameShouldThrowValidationException() throws Exception {
        Adopter c = new Adopter(null, "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").breed("mixed").build());
        String jsonString = mapper.writeValueAsString(c);

        ResultActions actions = mockMvc.perform(post("/petservice")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions = actions.andExpect(status().isBadRequest());

        MvcResult result = actions.andReturn();
        System.out.println("body: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testPostAdopterWithNullPetTypeShouldThrowValidationException() throws Exception {
        Adopter c = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
              Pet.builder(null).name("woofie").breed("mixed").build());
        String jsonString = mapper.writeValueAsString(c);

        ResultActions actions = mockMvc.perform(post("/petservice")
              .accept(MediaType.APPLICATION_JSON)
              .contentType(MediaType.APPLICATION_JSON)
              .content(jsonString));

        actions = actions.andExpect(status().isBadRequest());

        MvcResult result = actions.andReturn();
        System.out.println("body: " + result.getResponse().getContentAsString());
    }

    @Test
    public void testBadContentTypeThrowsMediaTypeNotSupportedException() throws Exception {
        Adopter c = new Adopter("Adopter For Delete", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(PetType.DOG).name("woofie").breed("mixed").build());
        String jsonString = mapper.writeValueAsString(c);

        ResultActions actions = mockMvc.perform(post("/petservice")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_CBOR)
                .content(jsonString));

        actions = actions.andExpect(status().isUnsupportedMediaType());

        System.out.println("body: " + actions.andReturn().getResponse().getContentAsString());
    }

    @Test
    public void testBadAcceptHeaderThrowsNotAcceptableException() throws Exception {
        MockHttpServletRequestBuilder builder = get("/petservice/{id}", "1")
              .accept(MediaType.APPLICATION_CBOR)
              .contentType(MediaType.APPLICATION_JSON);

        var actions = mockMvc.perform(builder);

        actions = actions.andExpect(status().isNotAcceptable());

        System.out.println("body: " + actions.andReturn().getResponse().getContentAsString());
    }
}
