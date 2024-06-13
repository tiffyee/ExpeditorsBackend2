package ttl.larku.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.dao.jpahibernate.JPAStudentDAO;
import ttl.larku.domain.Student;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
public class StudentDAOTest {

	private String name1 = "Bloke";
	private String name2 = "Blokess";
	private String newName = "Different Bloke";
	private Student student1;
	private Student student2;

	@Autowired
	private JPAStudentDAO studentDAO;

	@Autowired
	private ApplicationContext context;

	@BeforeEach
	public void setup() {
		student1 = new Student(name1, "383 939 9393", LocalDate.of(1956, 4, 6), Student.Status.FULL_TIME);
		student2 = new Student(name2, "383 939 54784394", LocalDate.of(1996, 3, 6), Student.Status.PART_TIME);
//        for(String name: context.getBeanDefinitionNames()) {
//            System.out.println(name);
//        }
//        System.out.println(context.getBeanDefinitionCount() + " beans");
	}

	@Test
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testGetAll() {
		List<Student> students = studentDAO.getAll();
		System.out.println(students);
		assertEquals(4, students.size());
	}

	@Test
	//@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void testGetAllWithClasses() {
		List<Student> students = studentDAO.getAllWithClasses();
		System.out.println(students);
		assertEquals(4, students.size());
	}

	@Test
	public void testCreate() {

		int newId = studentDAO.create(student1).getId();

		Student resultStudent = studentDAO.get(newId);

		System.out.println(resultStudent);

		assertEquals(newId, resultStudent.getId());
	}

	@Test
	public void testUpdate() {
		int newId = studentDAO.create(student1).getId();

		Student resultStudent = studentDAO.get(newId);

		assertEquals(newId, resultStudent.getId());

		resultStudent.setName(newName);
		studentDAO.update(resultStudent);

		resultStudent = studentDAO.get(resultStudent.getId());
		assertEquals(newName, resultStudent.getName());
	}

	@Test
	public void testDelete() {
		int id1 = studentDAO.create(student1).getId();

		Student resultStudent = studentDAO.get(id1);
		assertEquals(resultStudent.getId(), id1);

		studentDAO.delete(resultStudent);

		resultStudent = studentDAO.get(id1);

		assertEquals(null, resultStudent);
	}

	@Test
	public void testGetByName() {
		List<Student> students = studentDAO.getByName("Manoj");
		System.out.println("students.size: " + students.size());
		students.forEach(System.out::println);

		assertEquals(1, students.size());
	}
}
