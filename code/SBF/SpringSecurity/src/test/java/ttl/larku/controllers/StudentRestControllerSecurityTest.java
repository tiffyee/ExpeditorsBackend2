package ttl.larku.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Student;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class StudentRestControllerSecurityTest {
    @Autowired
    private MockMvc mockMvc;

    private final int goodStudentId = 1;
    private final int badStudentId = 10000;
    /**
     * "roberta" should only be allowed to update her own record.
     * i.e. the one with id == roberta's id and username = "roberta" and student name = "Roberta-h2".
     *
     * @throws Exception
     */
    @Test
    @WithUserDetails(value = "roberta")
    public void testUpdateStudentWithOwnUserId() throws Exception {

        var principal = SecurityContextHolder.getContext().getAuthentication();
        int goodId = 3;
        ResultActions actions = mockMvc
                .perform(get("/adminrest/student/{id}", goodId)
                        .accept(MediaType.APPLICATION_JSON));
        String jsonString = actions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(jsonString).path("entity");

        Student student = mapper.treeToValue(node, Student.class);

        student.setPhoneNumber("202 383-9393");
        String updatedJson = mapper.writeValueAsString(student);

        ResultActions putActions = mockMvc.perform(put("/adminrest/student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson));

        putActions = putActions.andExpect(status().isNoContent());

    }

    /**
     * Trying to update a record which does not belong to you.
     * This test should fail.
     *
     * @throws Exception
     */
    @Test
    @WithUserDetails(value = "roberta")
    public void testUpdateStudentWithNotOwnUserId() throws Exception {

        int badId = 2;
        ResultActions actions = mockMvc
                .perform(get("/adminrest/student/{id}", badId)
                        .accept(MediaType.APPLICATION_JSON));
        String jsonString = actions.andReturn().getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode node = mapper.readTree(jsonString).path("entity");

        Student student = mapper.treeToValue(node, Student.class);

        student.setPhoneNumber("202 383-9393");
        String updatedJson = mapper.writeValueAsString(student);

        ResultActions putActions = mockMvc.perform(put("/adminrest/student")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedJson));

        putActions = putActions.andExpect(status().isForbidden());

    }
}
