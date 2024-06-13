package ttl.larku.service;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author whynot
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class StudentServiceSecurityTest {
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
    //Security Test.
    //We are using a custom UserDetails object which we want to
    //plug in here.  @WithUserDetails will use our MyUserDetailsService
    //class and work with our MyUserDetails object.
    //The value is the username.  So "bobby" will work because
    //our user "bobby" has role ADMIN.  "roberta" will give us an
    //AccessDeniedException 403, unless she is updating her own record.
    //"roberta" will be allowed to update only if the record has her id
    //and her real name in the principal (real name taken from the Student table)
    //Providing an unknown user will give an exception when creating
    //the SecurityContext.
    //Look in ttl.larku.service.StudentService for security annotations.
    //They are all commented out by default.  Uncomment the annotations
    //for updateStudent to make this test meaningful.
    @Test
    @WithUserDetails(value = "roberta")
    public void testUpdateStudentGoodSecurity() {
        //Use student with id == 3, i.e. "Roberta-h2"
        //Roberta is updating her own record, so the update
        //should be allowed.
        Student student1 = studentService.getStudent(3);

        assertEquals(4, studentService.getAllStudents().size());
        String newPhoneNumber = "848 949 94949";
        student1.setPhoneNumber(newPhoneNumber);
//        student1.setName("alice");

        studentService.updateStudent(student1);

        assertEquals(4, studentService.getAllStudents().size());
        assertEquals(newPhoneNumber, studentService.getStudent(student1.getId()).getPhoneNumber());
    }

    @Test
    @WithUserDetails(value = "roberta")
    public void testUpdateStudentBadSecurity() {
        //User is trying to change a recored which does
        //not belong to them.  Which should not be allowed
        assertThrows(AccessDeniedException.class, () -> {
            Student student1 = studentService.getStudent(2);


            assertEquals(4, studentService.getAllStudents().size());
            String newPhoneNumber = "848 949 94949";
            student1.setPhoneNumber(newPhoneNumber);

            studentService.updateStudent(student1);

            assertEquals(4, studentService.getAllStudents().size());
            assertEquals(newPhoneNumber, studentService.getStudent(student1.getId()).getPhoneNumber());
        });
    }
}
