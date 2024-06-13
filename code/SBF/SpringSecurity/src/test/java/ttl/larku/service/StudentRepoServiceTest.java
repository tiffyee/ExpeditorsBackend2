package ttl.larku.service;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
//Populate your DB.  From Most Expensive to least expensive

//This will make recreate the context after every test.
//In conjunction with appropriate 'schema[-XXX].sql' and 'data[-XXX].sql' files
//it will also drop and recreate the DB before each test.
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

//Or you can just re-run the sql files before each test method
//@Sql(scripts = { "/ttl/larku/db/createDB-h2.sql", "/ttl/larku/db/populateDB-h2.sql" }, executionPhase=ExecutionPhase.BEFORE_TEST_METHOD)

//This next one will roll back the transaction after
//each test, so the database will actually stay the
//same for the next test.
@Transactional
public class StudentRepoServiceTest extends SqlScriptBase {

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
    @Qualifier("studentRepoService")
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
//    @Transactional
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
    @WithUserDetails("roberta")
    public void testCreateStudentNotADMINShouldThrowException() {
        assertThrows(AccessDeniedException.class, () -> {
            Student newStudent = studentService.createStudent(name1);

            Student result = studentService.getStudent(newStudent.getId());

            assertEquals(name1, result.getName());
            assertEquals(5, studentService.getAllStudents().size());
        });
    }

    @Test
    @WithUserDetails("bobby")
    public void testDeleteStudent() {
        Student student1 = studentService.createStudent(name1);
        Student student2 = studentService.createStudent(name2);

        assertEquals(6, studentService.getAllStudents().size());

        studentService.deleteStudent(student1.getId());

        assertEquals(5, studentService.getAllStudents().size());
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

    //We are using a custom UserDetails object which we want to 
    //plug in here.  @WithUserDetails will use our MyUserDetailsService
    //class and work with our MyUserDetails object.  
    //The value is the username.  So "bobby" will work because
    //our user "bobby" has role ADMIN.  All other users will throw
    //AccessDeniedException 403 unless they are updating their own record.
    //Providing an unknown user will give an exception when creating
    //the SecurityContext.
    //Look in ttl.larku.service.StudentService for security annotations.
    //They are all commented out by default.  Uncomment the annotations
    //for updateStudent to make this test meaningful.
    @Test
    @WithUserDetails(value = "roberta")
    public void testUpdateStudentShouldThrowAccessDeniedException() {
        assertThrows(AccessDeniedException.class, () -> {
            //Trying to change record which does not belong to you.
            int badId = 2;
            Student student1 = studentService.getStudent(badId);


            assertEquals(4, studentService.getAllStudents().size());
            String newPhoneNumber = "848 949 94949";
            student1.setPhoneNumber(newPhoneNumber);

            studentService.updateStudent(student1);

            assertEquals(4, studentService.getAllStudents().size());
            assertEquals(newPhoneNumber, studentService.getStudent(student1.getId()).getPhoneNumber());
        });
    }

    @Test
    @WithUserDetails(value = "roberta")
    public void testUpdateStudentOwnIdNoException() {
        //This should NOT throw an AccessDenied exception because,
        //even though alice is not an Admin,
        //she is trying to change a student whose id matches her id(1)
        int goodId = 3;
        Student student1 = studentService.getStudent(goodId);


        assertEquals(4, studentService.getAllStudents().size());
        String newPhoneNumber = "848 949 94949";
        student1.setPhoneNumber(newPhoneNumber);

        studentService.updateStudent(student1);

        assertEquals(4, studentService.getAllStudents().size());
        assertEquals(newPhoneNumber, studentService.getStudent(student1.getId()).getPhoneNumber());
    }

    @Test
    @WithUserDetails(value = "bobby")
    public void testUpdateStudentWithAdmin() {
        //This should NOT throw an AccessDenied exception because,
        //bobby has Role Admin, and so can update any student.
        //(bobby's id is 5)
        Student student1 = studentService.getStudent(1);


        assertEquals(4, studentService.getAllStudents().size());
        String newPhoneNumber = "848 949 94949";
        student1.setPhoneNumber(newPhoneNumber);
        student1.setName("alice");

        studentService.updateStudent(student1);

        assertEquals(4, studentService.getAllStudents().size());
        assertEquals(newPhoneNumber, studentService.getStudent(student1.getId()).getPhoneNumber());
    }
}