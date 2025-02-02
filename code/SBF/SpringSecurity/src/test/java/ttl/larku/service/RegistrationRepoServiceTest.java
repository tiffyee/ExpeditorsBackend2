package ttl.larku.service;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.sql.SqlScriptBase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//Populate your DB.  From Most Expensive to least expensive

//This will make recreate the context after every test.
//In conjunction with appropriate 'schema[-XXX].sql' and 'data[-XXX].sql' files
//it will also drop and recreate the DB before each test.
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)

//Or you can just re-run the sql files before each test method
//@Sql(scripts = { "/ttl/larku/db/createDB-h2.sql", "/ttl/larku/db/populateDB-h2.sql" }, executionPhase= ExecutionPhase.BEFORE_TEST_METHOD)

//This next one will roll back the transaction after
//each test, so the database will actually stay the
//same for the next test.
@Transactional
@Tag("integration")
public class RegistrationRepoServiceTest extends SqlScriptBase {

    private String manoj = "Manoj";
    private String blokess = "Blokess";
    private String differentBloke = "Different Bloke";

    private String math101 = "MATH-101";
    private String bot202 = "BOT-202";
    private String bktw101 = "BKTW-101";
    private String badCode = "BADNESS";
    private String title1 = "Intro To Botany";
    private String title2 = "Outtro To Botany";

    private LocalDate startDate1 = LocalDate.parse("2022-10-10");
    private LocalDate startDate2 = LocalDate.parse("2023-10-10");
    private LocalDate endDate1 = LocalDate.parse("2023-05-10");
    private LocalDate endDate2 = LocalDate.parse("2024-05-10");

    private LocalDate badStartDate = LocalDate.parse("2099-12-12");
    private Course course1;
    private Course course2;
    private ScheduledClass class1;
    private ScheduledClass class2;
    private Student student1;
    private Student student2;
	
	/*
	private ClassService classService;
	private CourseService courseService;
	private StudentService studentService;
	*/

    @Autowired
    @Qualifier("registrationRepoService")
    private RegistrationService regService;

    @Autowired
    private ApplicationContext appContext;

    @BeforeEach
    public void setup() {
        student1 = new Student(manoj);
        student2 = new Student(blokess);
        course1 = new Course(math101, title1);
        course2 = new Course(bot202, title2);

        class1 = new ScheduledClass(course1, startDate1, endDate1);
        class2 = new ScheduledClass(course2, startDate2, endDate2);

    }

    @Test
    @WithUserDetails("bobby")
    public void testAddClassAndStudent() {
        var sd = LocalDate.of(2024, 2, 26);
        var ed = LocalDate.of(2024, 6, 7);
        ScheduledClass sClass = regService.addNewClassToSchedule(math101, sd, ed);
        Student student1 = regService.getStudentService().createStudent("Josephine");


        regService.registerStudentForClass(student1.getId(), math101, sd);

        List<Student> students = regService.getStudentsForClass(math101, sd);

        assertEquals(1, students.size());

        assertEquals(1, regService.getStudentService().getStudent(student1.getId()).getClasses().size());
    }

    @Test
    public void testDropStudent() {
        var sd = LocalDate.of(2024, 2, 26);
        var ed = LocalDate.of(2024, 6, 7);
        ScheduledClass sClass = regService.addNewClassToSchedule(math101, sd, ed);
        Student student1 = regService.getStudentService().getStudent(1);
        Student student2 = regService.getStudentService().getStudent(2);

        regService.registerStudentForClass(student1.getId(), math101, sd);
        regService.registerStudentForClass(student2.getId(), math101, sd);

        List<Student> students = regService.getStudentsForClass(math101, sd);

        assertEquals(2, students.size());
        assertTrue(regService.getStudentService().getStudent(student1.getId()).getName().contains(manoj));

        regService.dropStudentFromClass(student1.getId(), math101, sd);

        students = regService.getStudentsForClass(math101, sd);

        assertEquals(1, students.size());
        assertEquals(student2.getName(), students.get(0).getName());
    }

    @Test
    public void testRegisterForNonExistentClass() {
        ScheduledClass sClass = regService.addNewClassToSchedule(math101, startDate1, endDate1);

        regService.registerStudentForClass(student1.getId(), math101, badStartDate);

        List<Student> students = regService.getStudentsForClass(math101, startDate1);

        assertEquals(2, students.size());
    }

//    private String code1 = "MATH-101";
//    private String code2 = "BOT-202";
    @Test
    public void testDropStudentFromNonExistentClass() {
        var sd = LocalDate.of(2024, 2, 26);
        var ed = LocalDate.of(2024, 6, 7);
        ScheduledClass sClass = regService.addNewClassToSchedule(math101, sd, ed);
        Student student1 = regService.getStudentService().getStudent(1);
        regService.registerStudentForClass(student1.getId(), math101, sd);
//        regService.registerStudentForClass(student2.getId(), code1, startDate1);

        List<Student> students = regService.getStudentsForClass(math101, sd);

        assertEquals(1, students.size());
        regService.dropStudentFromClass(student1.getId(), math101, badStartDate);

        students = regService.getStudentsForClass(math101, sd);

        assertEquals(1, students.size());
    }

    @Test
    public void testGetStudentsFromNonExistentClass() {

        List<Student> students = regService.getStudentsForClass(math101, badStartDate);

        assertEquals(0, students.size());
    }

    @Test
    public void testGetAllScheduledClasses() {
        ScheduledClass sClass = regService.addNewClassToSchedule(math101, startDate1, endDate1);

        List<ScheduledClass> classes = regService.getScheduledClasses();

        assertEquals(4, classes.size());

        assertEquals(sClass.getStartDate(), regService.getClassService().getScheduledClass(sClass.getId()).getStartDate());
    }
}
