package expeditors.backend.custapp.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import expeditors.backend.custapp.domain.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class CustomerRepoControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testGetAll() throws Exception {
        MockHttpServletRequestBuilder builder = get("/customerrepo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());

    }

    @Test
    public void testGetOneWithGoodId() throws Exception {
        MockHttpServletRequestBuilder builder = get("/customerrepo/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
    }

    @Test
    public void testGetOneWithBadId() throws Exception {
        MockHttpServletRequestBuilder builder = get("/customerrepo/{id}", 1000)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isNotFound());
    }

    @Test
    public void testPostCustomer() throws Exception {
        Customer c = new Customer("New Customer", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        String jsonString = mapper.writeValueAsString(c);

        ResultActions actions = mockMvc.perform(post("/customerrepo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString));

        actions = actions.andExpect(status().isCreated());

        MvcResult result = actions.andReturn();
        String locHeader = result.getResponse().getHeader("Location");
        assertNotNull(locHeader);
        System.out.println("locHeader: " + locHeader);
    }

    @Test
    public void testDeleteCustomerWithGoodId() throws Exception {
        ResultActions actions = mockMvc.perform(delete("/customerrepo/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCustomerWithBadId() throws Exception {
        ResultActions actions = mockMvc.perform(delete("/customerrepo/{id}", 1000)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        actions.andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCustomerWithGoodId() throws Exception {
        MockHttpServletRequestBuilder builder = get("/customerrepo/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Customer customer = mapper.treeToValue(node, Customer.class);
        customer.setName("Blech");

        String updatedJson = mapper.writeValueAsString(customer);

        actions = mockMvc.perform(put("/customerrepo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson));

        actions = actions.andExpect(status().isNoContent());

        builder = get("/customerrepo/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        result = actions.andReturn();

        jsonResult = result.getResponse().getContentAsString();
        node = mapper.readTree(jsonResult);
        customer = mapper.treeToValue(node, Customer.class);

        assertEquals("Blech", customer.getName());
    }

    @Test
    public void testUpdateCustomerWithBadId() throws Exception {
        MockHttpServletRequestBuilder builder = get("/customerrepo/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions actions = mockMvc.perform(builder)
                .andExpect(status().isOk());
        MvcResult result = actions.andReturn();

        String jsonResult = result.getResponse().getContentAsString();
        JsonNode node = mapper.readTree(jsonResult);
        Customer customer = mapper.treeToValue(node, Customer.class);
        customer.setName("Blech");
        customer.setId(1000);

        String updatedJson = mapper.writeValueAsString(customer);

        actions = mockMvc.perform(put("/customerrepo")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson));

        actions = actions.andExpect(status().isNotFound());

    }
}
