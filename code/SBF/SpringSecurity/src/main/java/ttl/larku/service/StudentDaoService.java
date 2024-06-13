package ttl.larku.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.controllers.rest.RestResultWrapper;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.jpahibernate.JPAStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.domain.Student.Status;
import ttl.larku.domain.StudentVersioned;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@Primary
public class StudentDaoService implements StudentService {

	@Autowired
	private JPAStudentDAO studentDAO;

	@Override
	public Student createStudent(String name) {
		Student student = new Student(name);
		return createStudent(student);
	}

	@Override
	public Student createStudent(String name, String phoneNumber, LocalDate dob, Status status) {
		return createStudent(new Student(name, phoneNumber, dob, status));
	}

	@Override
	public Student createStudent(Student student) {
		student = studentDAO.create(student);

		return student;
	}

    @Override
    public boolean deleteStudent(int id) {
        Student student = studentDAO.get(id);
        if (student != null) {
            studentDAO.delete(student);
            return true;
        }
        return false;
    }
    public RestResultWrapper<Student> deleteStudentR(int id) {
        Student currStudent = studentDAO.get(id);
        if (currStudent != null) {
            studentDAO.delete(currStudent);
            return RestResultWrapper.ofValue(currStudent);
        }
        return RestResultWrapper.ofError("No Student with id: " + id);
    }

    public RestResultWrapper<Student> updateStudentR(Student student) {
        Student currStudent = studentDAO.get(student.getId());
        if (currStudent != null) {
            studentDAO.update(student);
            return RestResultWrapper.ofValue(student);
        }
        return RestResultWrapper.ofError("No Student with id: " + student.getId());
    }


    //Using our own entityManager only for the next method, just to deal with
    //Versioned Students.
    @Autowired
    private EntityManager entityManager;

    public RestResultWrapper<StudentVersioned> updateStudentVersioned(StudentVersioned student) {
        StudentVersioned currStudent = entityManager.find(StudentVersioned.class, student.getId() , LockModeType.PESSIMISTIC_READ);
        currStudent.setName("Myrtle");
        return RestResultWrapper.ofValue(currStudent);
    }


    @Override
    public boolean updateStudent(Student student) {
        Student oldStudent = studentDAO.get(student.getId());
        if (oldStudent != null) {
            studentDAO.update(student);
            return true;
        }
        return false;
    }

    @Override
    public Student getStudent(int id) {
        return studentDAO.get(id);
    }


    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllWithClasses();
    }

	@Override
	public List<Student> getByName(String name) {
		String lc = name.toLowerCase();
		List<Student> result = studentDAO.getByName(name);
		return result;
	}

    public BaseDAO<Student> getStudentDAO() {
        return studentDAO;
    }

	public void setStudentDAO(JPAStudentDAO studentDAO) {
		this.studentDAO = studentDAO;
	}

    @Override
    public void clear() {
        studentDAO.deleteStore();
        studentDAO.createStore();
    }

}
