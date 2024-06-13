package ttl.larku.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import ttl.larku.domain.Student;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface StudentService {
    //These annotations are redundant because we have the same things
    //in our SecurityConfig.  (And we also have a 'denyAll' in the
    // config for anything we don't explicitly).  But it never
    //hurts to say it again.
    @PreAuthorize("hasRole('ADMIN')")
    Student createStudent(String name);

    @PreAuthorize("hasRole('ADMIN')")
    Student createStudent(String name, String phoneNumber, LocalDate dob, Student.Status status);

    @PreAuthorize("hasRole('ADMIN')")
    Student createStudent(Student student);

    @PreAuthorize("hasRole('ADMIN')")
    boolean deleteStudent(int id);

    //Here, we are only going to allow an update if the incoming student
    // either has Role 'ADMIN', *OR* has Role 'USER' and their id is the
    // same the principal's id *and* their student name is the same as
    // the principle's real name.  This means that the caller
    //can't change their id or their student name.  Have to be 'Admin' to do that.
    //The id part requires that we are using our MyUserDetailsXXX classes.
    //Also, IMPORTANT that the argument name 'student' is the same in implementing
    //classes.  Else some wierdness seems to happen with reflection.
    @PreAuthorize("hasRole('ADMIN') or (#student.id == authentication.principal.id and #student.name == authentication.principal.realName)")
    boolean updateStudent(Student student);

    Student getStudent(int id);

    List<Student> getAllStudents();

    List<Student> getByName(String name);

    void clear();
    
}
