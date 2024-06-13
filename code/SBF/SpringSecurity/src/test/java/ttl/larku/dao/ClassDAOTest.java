package ttl.larku.dao;

import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.dao.jpahibernate.JPAClassDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;

import static org.junit.jupiter.api.Assertions.assertEquals;


//If we want to use WebEnvironment.NONE, we have to set a property to have the
// SecurityConfig classes not come into play.  Look at SecurityWithCustomUser.
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE, properties = {"ttl.larku.security=false"})
//But we are going with MOCK, to take advantage of Context Caching.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class ClassDAOTest {

    private String name1 = "Bloke";
    private String name2 = "Blokess";
    private String newName = "Different Bloke";

    private String code1 = "BOT-101";
    private String code2 = "BOT-202";
    private String title1 = "Intro To Botany";
    private String title2 = "Advanced Basket Weaving";

    private LocalDate startDate1 = LocalDate.parse("2012-10-10");
    private LocalDate startDate2 = LocalDate.parse("2013-10-10");
    private LocalDate endDate1 = LocalDate.parse("2013-05-10");
    private LocalDate endDate2 = LocalDate.parse("2014-05-10");

    private Course course1;
    private Course course2;
    private ScheduledClass class1;
    private ScheduledClass class2;

    //private BaseDAO<ScheduledClass> dao;
    @Autowired
    @Qualifier("jpaClassDAO")
    private JPAClassDAO dao;

    @Resource(name = "courseDAO")
    private BaseDAO<Course> courseDAO;

    @BeforeEach
    public void setup() {
        course1 = new Course(code1, title1);
        course1 = courseDAO.create(course1);
        course2 = new Course(code2, title2);
        course2 = courseDAO.create(course2);

        class1 = new ScheduledClass(course1, startDate1, endDate1);
        class2 = new ScheduledClass(course2, startDate2, endDate2);
    }

    @Test
    public void testGetAll() {
        List<ScheduledClass> classes = dao.getAll();
        classes.forEach(System.out::println);
        assertEquals(3, classes.size());
    }

    @Test
    public void testGetByCourseCodeAndStartDate() {
        List<ScheduledClass> classes = dao.getByCourseCodeAndStartDate("BOT-202",
                LocalDate.parse("2022-10-10"));
        classes.forEach(System.out::println);
        assertEquals(1, classes.size());
    }

    @Test
    public void testGetByCourseCodeAndStartDateforStudents() {
        List<ScheduledClass> classes = dao.getByCourseCodeAndStartDateForStudents(
                "BOT-202", LocalDate.parse("2022-10-10"));
        classes.forEach(System.out::println);
        assertEquals(1, classes.size());
    }

    @Test
    public void testGetByCourseCode() {
        List<ScheduledClass> classes = dao.getByCourseCode("BOT-202");
        classes.forEach(System.out::println);
        assertEquals(1, classes.size());
    }

    @Test
    public void testCreate() {

        int newId = dao.create(class1).getId();

        ScheduledClass result = dao.get(newId);

        assertEquals(newId, result.getId());
    }

    @Test
    public void testUpdate() {
        int newId = dao.create(class1).getId();

        ScheduledClass result = dao.get(newId);

        assertEquals(newId, result.getId());

        result.setCourse(course2);
        dao.update(result);

        result = dao.get(newId);
        assertEquals(title2, result.getCourse().getTitle());
    }

    @Test
    public void testDelete() {
        int id1 = dao.create(class1).getId();

        ScheduledClass resultClass = dao.get(id1);
        assertEquals(resultClass.getId(), id1);

        dao.delete(resultClass);

        resultClass = dao.get(id1);

        assertEquals(null, resultClass);
    }
}
