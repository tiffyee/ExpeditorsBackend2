//package ttl.larku.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.support.BasicAuthenticationInterceptor;
//import org.springframework.web.client.RestTemplate;
//import ttl.larku.controllers.rest.RestResultGeneric;
//import ttl.larku.domain.Student;
//
//import javax.annotation.Resource;
//import java.io.IOException;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Disabled
//public class RestClientSSLSpringTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Resource(name = "fakessltemplate")
//    private RestTemplate fakeSslTemplate;
//
//    @Resource(name = "realssltemplate")
//    private RestTemplate realSslTemplate;
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    // GET with url parameters
//    private String rootUrl;
//    private String studentRootUrl;
//    private String courseUrl;
//    private String classUrl;
//    private String oneStudentUrl;
//    BasicAuthenticationInterceptor roleAdminUser = new BasicAuthenticationInterceptor("bobby", "password");
//    BasicAuthenticationInterceptor roleUserUser = new BasicAuthenticationInterceptor("alice", "password");
//
//    @BeforeEach
//    public void setup() {
//        rootUrl = "https://localhost:" + port + "/adminrest";
//        studentRootUrl = rootUrl + "/student";
//        classUrl = rootUrl + "/class";
//        courseUrl = rootUrl + "/course";
//
//        oneStudentUrl = studentRootUrl + "/{id}";
//
//        fakeSslTemplate.getInterceptors().add(roleAdminUser);
//        realSslTemplate.getInterceptors().add(roleAdminUser);
//    }
//
//    @Test
//    public void testGetOneStudentUsingAutoUnmarshalling() throws IOException {
//        // This is the Spring REST mechanism to create a paramterized type
//        ParameterizedTypeReference<RestResultGeneric<Student>> ptr = new ParameterizedTypeReference<RestResultGeneric<Student>>() {
//        };
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<RestResultGeneric<Student>> response = fakeSslTemplate.exchange(oneStudentUrl,
//                HttpMethod.GET, entity, ptr, 2);
//        assertEquals(200, response.getStatusCodeValue());
//
//        RestResultGeneric<Student> rr = response.getBody();
//        RestResultGeneric.Status status = rr.getStatus();
//        assertTrue(status == RestResultGeneric.Status.Ok);
//
//        //Still need the mapper to convert the entity Object
//        //which should be represented by a map of student properties
//        Student s = mapper.convertValue(rr.getEntity(), Student.class);
//        System.out.println("Student is " + s);
//        //assertEquals("Ana", s.getName());
//        assertTrue(s.getName().contains("Ana"));
//    }
//
//    @Test
//    public void testGetOneStudentWithManualJson() throws IOException {
////		ResponseEntity<String> response = rt.getForEntity(oneStudentUrl, String.class, 2);
//        ResponseEntity<String> response = realSslTemplate.getForEntity(oneStudentUrl, String.class, 2);
//        assertEquals(200, response.getStatusCodeValue());
//
//        String raw = response.getBody();
//        JsonNode root = mapper.readTree(raw);
//        RestResult.Status status = RestResult.Status.valueOf(root.path("status").asText());
//        assertTrue(status == RestResult.Status.Ok);
//
//        JsonNode entity = root.path("entity");
//        Student s = mapper.treeToValue(entity, Student.class);
//        System.out.println("Student is " + s);
//        //assertEquals("Ana", s.getName());
//        assertTrue(s.getName().contains("Ana"));
//    }
//
//    @Test
//    public void testGetOneStudentBadId() throws IOException {
//        ResponseEntity<String> response = fakeSslTemplate.getForEntity(oneStudentUrl, String.class, 10000);
//        assertEquals(404, response.getStatusCodeValue());
//
//        String raw = response.getBody();
//        JsonNode root = mapper.readTree(raw);
//        RestResult.Status status = RestResult.Status.valueOf(root.path("status").asText());
//        assertTrue(status == RestResult.Status.Error);
//
//        JsonNode errors = root.path("errors");
//        assertTrue(errors != null);
//
//        StringBuffer sb = new StringBuffer(100);
//        errors.forEach(node -> {
//            sb.append(node.asText());
//        });
//        String reo = sb.toString();
//        System.out.println("Error is " + reo);
//        assertTrue(reo.contains("not found"));
//    }
//
//    @Test
//    public void testGetAllUsingAutoUnmarshalling() throws IOException {
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<RestResult> response = fakeSslTemplate.exchange(studentRootUrl,
//                HttpMethod.GET, entity, RestResult.class);
//
//        assertEquals(200, response.getStatusCodeValue());
//
//        RestResult rr = response.getBody();
//        RestResult.Status status = rr.getStatus();
//        assertTrue(status == RestResult.Status.Ok);
//
//        //Jackson mechanism to represent a Generic Type List<Student>
//        CollectionType listType = mapper.getTypeFactory()
//                .constructCollectionType(List.class, Student.class);
//        List<Student> students = mapper.convertValue(rr.getEntity(), listType);
//        System.out.println("l2 is " + students);
//
//        assertEquals(4, students.size());
//    }
//
//    /**
//     * Here we test getting the response as Json and then
//     * picking our way through it using the ObjectMapper
//     * We use RestResultGeneric here
//     *
//     * @throws IOException
//     */
//    @Test
//    public void testGetAllWithJsonUsingRestResultGeneric() throws IOException {
//        ResponseEntity<String> response = fakeSslTemplate.getForEntity(studentRootUrl, String.class);
//        assertEquals(200, response.getStatusCodeValue());
//        String raw = response.getBody();
//        JsonNode root = mapper.readTree(raw);
//
//        RestResult.Status status = RestResult.Status.valueOf(root.path("status").asText());
//        assertTrue(status == RestResult.Status.Ok);
//
//        //Have to make this complicated mapping to get
//        //ResutResultGeneric<List<Student>>
//        CollectionType listType = mapper.getTypeFactory()
//                .constructCollectionType(List.class, Student.class);
//        JavaType type = mapper.getTypeFactory()
//                .constructParametricType(RestResultGeneric.class, listType);
//
//        //We could unmarshal the whole entity
//        RestResultGeneric<List<Student>> rr = mapper.readerFor(type).readValue(root);
//        System.out.println("List is " + rr.getEntity());
//
//        List<Student> l1 = rr.getEntity();
//
//        // Create the collection type (since it is a collection of Authors)
//
//        //Or we could step through the json to the entity and just unmarshal that
//        JsonNode entity = root.path("entity");
//        List<Student> l2 = mapper.readerFor(listType).readValue(entity);
//        System.out.println("l2 is " + l2);
//
//        assertEquals(4, l2.size());
//
//    }
//
//    /**
//     * Here we are using RestResultGeneric having Jackson
//     * do all the unmarshalling and give us the correct object
//     *
//     * @throws IOException
//     */
//    @Test
//    public void testGetAllUsingRestResultGeneric() throws IOException {
//        //This is the Spring REST mechanism to create a paramterized type
//        ParameterizedTypeReference<RestResultGeneric<List<Student>>>
//                ptr = new ParameterizedTypeReference<RestResultGeneric<List<Student>>>() {
//        };
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//
//        ResponseEntity<RestResultGeneric<List<Student>>> response = fakeSslTemplate.exchange(studentRootUrl,
//                HttpMethod.GET, entity, ptr);
//
//        assertEquals(200, response.getStatusCodeValue());
//        RestResultGeneric<List<Student>> rr = response.getBody();
//
//        RestResultGeneric.Status status = rr.getStatus();
//        assertTrue(status == RestResultGeneric.Status.Ok);
//
//        List<Student> l1 = rr.getEntity();
//        assertEquals(4, l1.size());
//    }
//
//    @Test
//    public void testPostOneStudentWithManualJson() throws IOException {
////		ResponseEntity<String> response = rt.getForEntity(oneStudentUrl, String.class, 2);
//        Student newStudent = new Student("Alice", "383 939 9393", Student.Status.HIBERNATING);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> httpEntity = new HttpEntity<>(newStudent, headers);
//
//        ResponseEntity<String> response = realSslTemplate
//                .exchange(studentRootUrl, HttpMethod.POST, httpEntity, String.class);
//
//        assertEquals(201, response.getStatusCodeValue());
//
//        String raw = response.getBody();
//        JsonNode root = mapper.readTree(raw);
//        RestResultGeneric.Status status = RestResultGeneric.Status.valueOf(root.path("status").asText());
//        assertTrue(status == RestResultGeneric.Status.Ok);
//
//        JsonNode entity = root.path("entity");
//        Student s = mapper.treeToValue(entity, Student.class);
//        System.out.println("Student is " + s);
//        //assertEquals("Ana", s.getName());
//        assertTrue(s.getName().contains("Alice"));
//    }
//
//    /**
//     * Here we are testing Authorization.  We switch the User to be the
//     * on in ROLE_USER.  This should make the POST call to add a class
//     * fail with a 403 error
//     *
//     * @throws IOException
//     */
//    @Test
//    public void testPostToAddOneClassWithWrongUser() throws IOException {
//        //Switch the default User to be in ROLE_USER
//        realSslTemplate.getInterceptors().remove(roleAdminUser);
//        realSslTemplate.getInterceptors().add(roleUserUser);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(classUrl)
//                // Add query parameter
//                .queryParam("courseCode", "BKTW-101")
//                .queryParam("startDate", "10/10/2020")
//                .queryParam("endDate", "10/10/2021");
//
//        ResponseEntity<String> response = realSslTemplate
//                .exchange(builder.build().toUri(), HttpMethod.POST, httpEntity, String.class);
//
//        assertEquals(403, response.getStatusCodeValue());
//    }
//
//    @Test
//    public void testPostToAddOneClassWithCorrectUser() throws IOException {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<?> httpEntity = new HttpEntity<>(headers);
//
//        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(classUrl)
//                // Add query parameter
//                .queryParam("courseCode", "BKTW-101")
//                .queryParam("startDate", "10/10/2020")
//                .queryParam("endDate", "10/10/2021");
//
//        ResponseEntity<String> response = realSslTemplate
//                .exchange(builder.build().toUri(), HttpMethod.POST, httpEntity, String.class);
//
//        assertEquals(201, response.getStatusCodeValue());
//
//        String raw = response.getBody();
//        JsonNode root = mapper.readTree(raw);
//        RestResultGeneric.Status status = RestResultGeneric.Status.valueOf(root.path("status").asText());
//        assertTrue(status == RestResultGeneric.Status.Ok);
//
//        JsonNode entity = root.path("entity");
//        ScheduledClass s = mapper.treeToValue(entity, ScheduledClass.class);
//        System.out.println("ScheduledClass is " + s);
//        //assertEquals("Ana", s.getName());
//        assertTrue(s.getCourse().getCode().contains("BKTW-101"));
//    }
//}
