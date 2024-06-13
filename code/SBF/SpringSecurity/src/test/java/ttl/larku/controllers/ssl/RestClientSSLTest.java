package ttl.larku.controllers.ssl;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.web.client.RestClient;
import ttl.larku.controllers.rest.RestResultWrapper;
import ttl.larku.controllers.rest.RestResultWrapper.Status;
import ttl.larku.domain.Student;
import ttl.larku.jconfig.client.RestClientFactory;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Tag("expensive")
@EnabledIf(expression = "#{environment.matchesProfiles('ssltest')}", loadContext = true)
public class RestClientSSLTest extends SqlScriptBase {

    @LocalServerPort
    private int port; // = 8443;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private RestClientFactory clientFactory;

    private RestClient restClient;

    @Value("${CLIENT_PASSWORD}")
    private String password;

    private String baseUrl;
    private String rootUrl;
    private String oneStudentUrl;

    RestClient ratingClient;

    @PostConstruct
    public void init() {
        baseUrl = "https://localhost:" + port;
        rootUrl = "/adminrest/student";
        oneStudentUrl = rootUrl + "/{id}";

        //We have to use the non "bundle" version of the clients if we don't
        //put the self-signed certificates to our JDK (see README.SSL)
        this.restClient = clientFactory.sslClientFromRestClient(baseUrl, "bobby", password);

        //We can use the "...clientFromBundle" clients Only IF we have added the
        //self-signed certificate of the client to the trustStore (e.g. 'cacerts' file) of the JDK
        //running this code. (see README.SSL)
//        this.restClient = clientFactory.sslClientFromBundle(baseUrl, "bobby", password);
        this.ratingClient = clientFactory.sslClientFromBundle("https://localhost:10043/rating", "bobby", password);
    }


    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCallCourseRatingServiceRestClientAndStraighHttpsURL() {
        var myRatingClient= clientFactory.sslClientFromBundle("https://localhost:10043/rating", "bobby", password);
//        var myRatingClient= clientFactory.sslClientFromRestClient("https://localhost:10043/rating", "bobby", password);
//        var myRatingClient= clientFactory.sslFromRestTemplate("https://localhost:10043/rating", "bobby", password);
        var response = myRatingClient.get()
              .uri("/{id}", 2)
              .retrieve()
              .toEntity(BigDecimal.class);

        var result = response.getBody();

        System.out.println("Result: " + result);


        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCallCourseRatingService() {
       var result = ratingClient.get()
             .uri("/{id}", 2)
             .retrieve()
             .toEntity(BigDecimal.class);

       assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void testGetOneStudentUsingAutoUnmarshalling() throws IOException {
        Student s = getStudentWithId(2);
        assertTrue(s.getName().contains("Ana"));
    }

    public Student getStudentWithId(int id) throws IOException {
        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<RestResultWrapper<Student>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<Student>>() {
        };


        ResponseEntity<RestResultWrapper<Student>> response = restClient.get()
                .uri(oneStudentUrl, id)
//                .uri("/adminrest/student/{id}", id)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        RestResultWrapper<Student> rr = response.getBody();
        Status status = rr.getStatus();
        assertTrue(status == Status.Ok);

        //Still need the mapper to convert the entity Object
        //which should be represented by a map of student properties
        //Student s = mapper.convertValue(rr.getEntity(), Student.class);
        Student s = rr.getEntity();
        System.out.println("Student is " + s);

        return s;
    }

    @Test
    public void testGetOneStudentWithManualJson() throws IOException {
        ResponseEntity<String> response = restClient.get()
                .uri(oneStudentUrl, 2)
                .retrieve()
                .toEntity(String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        String raw = response.getBody();
        JsonNode root = mapper.readTree(raw);
        Status status = Status.valueOf(root.path("status").asText());
        assertTrue(status == Status.Ok);

        JsonNode entity = root.path("entity");
        Student s = mapper.treeToValue(entity, Student.class);
        System.out.println("Student is " + s);
        assertTrue(s.getName().contains("Ana"));
    }

    @Test
    public void testGetOneStudentBadId() throws IOException {
        ResponseEntity<String> response = restClient.get()
                .uri(oneStudentUrl, 1000)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    //Do Nothing because we are going to deal with it with
                    //An assertion
//                    System.out.println("Got 4xxxClientError");
                })
                .toEntity(String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        String raw = response.getBody();
        JsonNode root = mapper.readTree(raw);
        Status status = Status.valueOf(root.path("status").asText());
        assertTrue(status == Status.Error);

        JsonNode errors = root.path("errors");
        assertTrue(errors != null);

        StringBuffer sb = new StringBuffer(100);
        errors.forEach(node -> {
            sb.append(node.asText());
        });
        String reo = sb.toString();
        System.out.println("Error is " + reo);
        assertTrue(reo.contains("not found"));
    }

    @Test
    public void testGetAllUsingAutoUnmarshalling() throws IOException {
        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<RestResultWrapper<List<Student>>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<List<Student>>>() {
        };

        ResponseEntity<RestResultWrapper<List<Student>>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        RestResultWrapper<List<Student>> rr = response.getBody();
        Status status = rr.getStatus();
        assertTrue(status == Status.Ok);

        List<Student> students = rr.getEntity();
        System.out.println("l2 is " + students);

        assertEquals(4, students.size());
    }

    /**
     * Here we test getting the response as Json and then
     * picking our way through it using the ObjectMapper
     * We use RestResultGeneric here
     *
     * @throws IOException
     */
    @Test
    public void testGetAllWithJsonUsingRestResultGeneric() throws IOException {

        ResponseEntity<String> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        String raw = response.getBody();
        JsonNode root = mapper.readTree(raw);

        Status status = Status.valueOf(root.path("status").asText());
        assertTrue(status == Status.Ok);

        //Have to make this complicated mapping to get
        //ResutResultGeneric<List<Student>>
        CollectionType listType = mapper.getTypeFactory()
                .constructCollectionType(List.class, Student.class);
        JavaType type = mapper.getTypeFactory()
                .constructParametricType(RestResultWrapper.class, listType);

        //We could unmarshal the whole entity
        RestResultWrapper<List<Student>> rr = mapper.readerFor(type).readValue(root);
        System.out.println("List is " + rr.getEntity());

        List<Student> l1 = rr.getEntity();

        // Create the collection type (since it is a collection of Authors)

        //Or we could step through the json to the entity and just unmarshal that
        JsonNode entity = root.path("entity");
        List<Student> l2 = mapper.readerFor(listType).readValue(entity);
        System.out.println("l2 is " + l2);
    }

    /**
     * Here we are using RestResultGeneric having Jackson
     * do all the unmarshalling and give us the correct object
     *
     * @throws IOException
     */
    @Test
    public void testGetAllUsingRestResultGeneric() throws IOException {
        //This is the Spring REST mechanism to create a paramterized type
        ParameterizedTypeReference<RestResultWrapper<List<Student>>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<List<Student>>>() {
        };


        ResponseEntity<RestResultWrapper<List<Student>>> response = restClient.get()
                .uri(rootUrl)
                .retrieve()
                .toEntity(ptr);


        assertEquals(HttpStatus.OK, response.getStatusCode());

        RestResultWrapper<List<Student>> rr = response.getBody();

        Status status = rr.getStatus();
        assertTrue(status == Status.Ok);

        List<Student> l1 = rr.getEntity();
        //assertEquals(4, l1.size());
    }

    /**
     * Here we are using RestResultGeneric having Jackson
     * do all the unmarshalling and give us the correct object
     *
     * @throws IOException
     */
    @Test
    public void testPostOneStudent() throws IOException {
       postOneStudent();
    }

    public Student postOneStudent() throws IOException {
        //This is the Spring REST mechanism to create a parameterized type
        ParameterizedTypeReference<RestResultWrapper<Student>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<Student>>() {
        };

        Student student = new Student("Curly", "339 03 03030", LocalDate.of(2000, 10, 10), Student.Status.HIBERNATING);


        ResponseEntity<RestResultWrapper<Student>> response = restClient.post()
                .uri(rootUrl)
                .body(student)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        RestResultWrapper<Student> rr = response.getBody();
        Status status = rr.getStatus();
        assertTrue(status == Status.Ok);

        Student newStudent = rr.getEntity();
        return newStudent;
    }

    @Test
    public void postOneStudentFor401() {
        //This is the Spring REST mechanism to create a parameterized type
        ParameterizedTypeReference<RestResultWrapper<Student>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<Student>>() {
        };

        Student student = new Student("Curly", "339 03 03030", LocalDate.of(2000, 10, 10), Student.Status.HIBERNATING);

        String basicAuthHeader = "basic " + Base64.getEncoder().encodeToString(("xyz" + ":" + "abc").getBytes());

        ResponseEntity<RestResultWrapper<Student>> response = restClient.post()
                .uri(rootUrl)
                .header("Authorization", basicAuthHeader)
                .body(student)
                .retrieve()
                .onStatus(st -> st == HttpStatus.UNAUTHORIZED, (req, resp) -> {})
                .toEntity(ptr);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    /**
     * Here we are using RestResultGeneric having Jackson
     * do all the unmarshalling and give us the correct object
     *
     * @throws IOException
     */
    @Test
    public void testUpdateOneStudent() throws IOException {

        //This is the Spring REST mechanism to create a parameterized type
        ParameterizedTypeReference<RestResultWrapper<Void>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<Void>>() {
        };

        Student newStudent = postOneStudent();
        String newPhoneNumber = "888 777-333456";
        newStudent.setPhoneNumber(newPhoneNumber);

        ResponseEntity<RestResultWrapper<Void>> response = restClient.put()
                .uri(rootUrl)
                .body(newStudent)
                .retrieve()
                .toEntity(ptr);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        Student updatedStudent = getStudentWithId(newStudent.getId());
        assertEquals(newPhoneNumber, updatedStudent.getPhoneNumber());
    }

    @Test
    public void testUpdateOneStudentBadId() throws IOException {

        //This is the Spring REST mechanism to create a parameterized type
        ParameterizedTypeReference<RestResultWrapper<Void>>
                ptr = new ParameterizedTypeReference<RestResultWrapper<Void>>() {
        };

        Student newStudent = postOneStudent();
        String newPhoneNumber = "888 777-333456";
        newStudent.setPhoneNumber(newPhoneNumber);
        newStudent.setId(9999);

        ResponseEntity<RestResultWrapper<Void>> response = restClient.put()
                .uri(rootUrl)
                .body(newStudent)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
                    //Do Nothing
                })
                .toEntity(ptr);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
