package ttl.larku.service.reg.integration;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Student;
import ttl.larku.domain.Student.Status;
import ttl.larku.service.StudentJdbcService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Tag("integration")
@Transactional
public class StudentJdbcServiceTest {

    private String name1 = "Johnson";
    private String name2 = "Sarla";
    private String newName = "Karl Jung";
    private String phoneNumber1 = "290 298 4790";
    private String phoneNumber2 = "3838 939 93939";

    @Autowired
    private StudentJdbcService studentService;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void setup() {
        studentService.clear();
    }

    @Test
    public void testCreateStudent() {
        int oldSize = studentService.getAllStudents().size();
        Student newStudent = studentService.createStudent(name1, phoneNumber1, Status.FULL_TIME);

        Student result = studentService.getStudent(newStudent.getId());

        System.out.println("result: " + result);

        assertTrue(result.getName().contains(name1));
        assertEquals(oldSize + 1, studentService.getAllStudents().size());
    }

    @Test
    public void testDeleteStudent() {
        Student student1 = studentService.createStudent(name1, phoneNumber1, Status.FULL_TIME);
        Student student2 = new Student(name1, phoneNumber1, Status.FULL_TIME);
        student2 = studentService.createStudent(student2);

        int oldSize = studentService.getAllStudents().size();

        studentService.deleteStudent(student1.getId());

        assertEquals(oldSize - 1, studentService.getAllStudents().size());
    }

    @Test
    public void testDeleteNonExistentStudent() {

        Student student1 = studentService.createStudent(name1, phoneNumber1, Status.FULL_TIME);
        Student student2 = new Student(name1, phoneNumber1, Status.FULL_TIME);
        student2 = studentService.createStudent(student2);

        int oldSize = studentService.getAllStudents().size();

        //Non existent Id
        studentService.deleteStudent(9999);

        assertEquals(oldSize, studentService.getAllStudents().size());
    }

    @Test
    public void testUpdateStudent() {
        Student student1 = studentService.createStudent(name1, phoneNumber1, Status.FULL_TIME);
        int oldSize = studentService.getAllStudents().size();

        student1.setName(name2);
        studentService.updateStudent(student1);

        assertEquals(oldSize, studentService.getAllStudents().size());
    }

    @Test
    public void testGetByName() {
        Student student1 = studentService.createStudent(name1, phoneNumber1, Status.FULL_TIME);
        Student student2 = studentService.createStudent(name2, phoneNumber2, Status.FULL_TIME);
        int oldSize = studentService.getAllStudents().size();

        String searchName = name1;
        List<Student> blokes = studentService.getByName(searchName);

        assertTrue(!blokes.isEmpty());
    }
}
