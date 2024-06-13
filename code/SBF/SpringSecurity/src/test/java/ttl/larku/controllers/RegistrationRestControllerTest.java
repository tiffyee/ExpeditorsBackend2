package ttl.larku.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import ttl.larku.controllers.rest.RegistrationRestController;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.ClassService;
import ttl.larku.service.StudentService;
import ttl.larku.sql.SqlScriptBase;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test A controller with Security enabled.
 * We use the @WithMockUser annotation to fake the Roles
 * for a user.
 */

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
@Transactional
public class RegistrationRestControllerTest extends SqlScriptBase {

    @Autowired
    private RegistrationRestController controller;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ClassService classService;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private DateTimeFormatter dtFormatter;

    @BeforeEach
    public void init() {
        //magic security sauce from springSecurity()
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
        dtFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    }

    //Don't need a User here because GET's are free
    @Test
    public void testGetAll() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/adminrest/class").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        actions = actions.andExpect(jsonPath("$.entity", hasSize(3)));

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Reo is " + reo);
    }

    @Test
    //Don't need a user here because we have opened up all GET requests.
    public void testGetOne() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/adminrest/class/1").accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        actions = actions.andExpect(jsonPath("$.entity.startDate")
                .value("2022-10-10"));

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Reo is " + reo);
    }

    @Test
    //Don't need a user here because we have opened up all GET requests.
    public void testGetOnePath() throws Exception {
        ResultActions actions = mockMvc
                .perform(get("/adminrest/class/code/MATH-101")
                        .param("startDate", "2022-10-10")
                        .param("endDate", "2013-10-10")
                        .accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));


        actions = actions.andExpect(jsonPath("$.entity[0].startDate")
                .value("2022-10-10"));

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Reo is " + reo);
    }

    //This should work because only Role ADMIN is allowed to
    //do a POST to /adminrest/**
    @Test
    @WithMockUser(roles = "ADMIN")
    public void addOneQueryParams() throws Exception {
        ResultActions actions = mockMvc
                .perform(post("/adminrest/class")
                        .param("courseCode", "BKTW-101")
                        .param("startDate", "2019-05-05")
                        .param("endDate", "2019-10-05")
                        .accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isCreated());

        actions = actions.andExpect(jsonPath("$.entity.startDate").value("2019-05-05"));

        MvcResult mvcr = actions.andReturn();
        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Reo is " + reo);
    }


    @Test
    //No User here, should get 401 Unauthorised (which really
    //means Unauthenticated)
    public void testRegisterStudentPathUnAuthorised() throws Exception {
        List<Student> students = studentService.getAllStudents();
        List<ScheduledClass> classes = classService.getAllScheduledClasses();
        int studentId = students.get(0).getId();
        int classId = classes.get(0).getId();

        ResultActions actions = mockMvc
                .perform(post("/adminrest/class/register/{studentId}/{classId}", studentId, classId)
                        .accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isUnauthorized());

    }

    //With some arbitrary user.  It's the Role that matters
    //This should give us a 403 Forbidden.
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "USER")
    public void testRegisterStudentPathForbidden() throws Exception {
        List<Student> students = studentService.getAllStudents();
        List<ScheduledClass> classes = classService.getAllScheduledClasses();
        int studentId = students.get(0).getId();
        int classId = classes.get(0).getId();

        ResultActions actions = mockMvc
                .perform(post("/adminrest/class/register/{studentId}/{classId}", studentId, classId)
                        .accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isForbidden());

    }

    //Again, it's the role that is important.
    //This should work
    @Test
    @WithMockUser(username = "user1", password = "pwd", roles = "ADMIN")
    public void testRegisterStudentAllGood() throws Exception {
        List<Student> students = studentService.getAllStudents();
        List<ScheduledClass> classes = classService.getAllScheduledClasses();
        int studentId = students.get(0).getId();
        int classId = classes.get(0).getId();

        ResultActions actions = mockMvc
                .perform(post("/adminrest/class/register/{studentId}/{classId}", studentId, classId)
                        .accept(MediaType.APPLICATION_JSON));

        actions = actions.andExpect(status().isOk());

        actions = actions.andExpect(content().contentType(MediaType.APPLICATION_JSON));


        //Get the student and make sure they have to class
        Student s = studentService.getStudent(studentId);
        List<ScheduledClass> sClasses = s.getClasses();

        boolean hasIt = sClasses.stream().anyMatch(sc -> sc.getId() == classId);
        assertTrue(hasIt);

        MvcResult mvcr = actions.andReturn();

        String reo = (String) mvcr.getResponse().getContentAsString();
        System.out.println("Reo is " + reo);
    }
}
