package ttl.larku.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
//Use this to conditionally include a test.  Look at MyProfileValueSource.java.
//@ProfileValueSourceConfiguration(MyProfileValueSource.class)
//@IfProfileValue(name = "spring.profiles.active", values = {"development"})

//Populate your DB.  You can either do this or use the schema[-XXX].sql and data[-XXX].sql files
//and @DirtiesContext as below
//@Sql(scripts = { "/ttl/larku/db/createDB-h2.sql", "/ttl/larku/db/populateDB-h2.sql" }, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)

//This will recreate the context after every test.
//In conjunction with appropriate 'schema[-XXX].sql' and 'data[-XXX].sql' files
//it will also drop and recreate the DB before each test.
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//@Sql(scripts = {"/schema-h2production.sql", "/data-h2production.sql"},
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)

@Transactional
public class StudentDaoServiceTest extends SqlScriptBase {

    private String name1 = "Bloke";
    private String name2 = "Blokess";
    private String newName = "Different Bloke";

    private String code1 = "MATH-101";
    private String code2 = "BOT-202";
    private String badCode = "BADNESS";
    private String title1 = "Intro To Botany";
    private String title2 = "Outtro To Botany";


    private LocalDate startDate1 = LocalDate.parse("2012-10-10");
    private LocalDate startDate2 = LocalDate.parse("2013-10-10");
    private LocalDate endDate1 = LocalDate.parse("2013-05-10");
    private LocalDate endDate2 = LocalDate.parse("2014-05-10");

    private LocalDate badStartDate = LocalDate.parse("2099-12-12");


    private Course course1;
    private Course course2;
    private ScheduledClass class1;
    private ScheduledClass class2;
    private Student student1;
    private Student student2;

    @Autowired
    private StudentService studentService;

    @BeforeEach
    public void setup() {
        student1 = new Student(name1);
        student2 = new Student(name2);
        course1 = new Course(code1, title1);
        course2 = new Course(code2, title2);

        class1 = new ScheduledClass(course1, startDate1, endDate1);
        class2 = new ScheduledClass(course2, startDate2, endDate2);

    }

    @Test
    public void testGetAll() {
        List<Student> students = studentService.getAllStudents();

        students.forEach(System.out::println);

        assertEquals(4, students.size());
    }

    @Test
    @WithUserDetails("bobby")
    public void testCreateStudent() {
        Student newStudent = studentService.createStudent(name1);

        Student result = studentService.getStudent(newStudent.getId());

        assertEquals(name1, result.getName());
        assertEquals(5, studentService.getAllStudents().size());
    }

    @Test
    public void testCreateStudentWithoutUserShouldThrowAuthenticationException() {
        assertThrows(AuthenticationException.class, () -> {
            Student newStudent = studentService.createStudent(name1);

            Student result = studentService.getStudent(newStudent.getId());

            assertEquals(name1, result.getName());
            assertEquals(5, studentService.getAllStudents().size());
        });
    }

    @Test
    @WithUserDetails("bobby")
    public void testDeleteStudentWithRoleAdminShouldSucceed() {
        Student student1 = studentService.createStudent(name1);
        Student student2 = studentService.createStudent(name2);

        assertEquals(6, studentService.getAllStudents().size());

        studentService.deleteStudent(student1.getId());

        assertEquals(5, studentService.getAllStudents().size());
    }

    @Test
    @WithUserDetails("roberta")
    public void testDeleteWithoutAdminRoleShouldThrowAccessDeniedAcception() {
        assertThrows(AccessDeniedException.class, () -> {
            Student student1 = studentService.createStudent(name1);
            Student student2 = studentService.createStudent(name2);

            assertEquals(6, studentService.getAllStudents().size());

            studentService.deleteStudent(student1.getId());

            assertEquals(5, studentService.getAllStudents().size());
        });
    }

    @Test
    @WithUserDetails("bobby")
    public void testDeleteNonExistentStudent() {
        Student student1 = studentService.createStudent(name1);
        Student student2 = studentService.createStudent(name2);

        assertEquals(6, studentService.getAllStudents().size());

        //Non existent Id
        studentService.deleteStudent(9999);

        assertEquals(6, studentService.getAllStudents().size());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateStudent() {
        Student student1 = studentService.createStudent(name1);

        assertEquals(5, studentService.getAllStudents().size());

        student1.setName(name2);
        studentService.updateStudent(student1);

        assertEquals(5, studentService.getAllStudents().size());
        assertEquals(name2, studentService.getStudent(student1.getId()).getName());
    }

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    public void testDeleteClassFromUnderneath() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Student student1 = studentService.getStudent(1);

        List<ScheduledClass> classes = student1.getClasses();
        classes.get(0).setStartDate(LocalDate.parse("2222-10-10"));
        String name = student1.getName();
        student1.setName("What will happen");
        em.getTransaction().commit();

    }
}
