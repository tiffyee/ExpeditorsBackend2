package ttl.larku.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/adminrest/student")
public class StudentRestController {

    private final BeanHelpers beanHelpers;
    private StudentService studentService;
	//Constructor injection.  Also helps with testing.
	public StudentRestController(StudentService studentService, BeanHelpers beanHelpers) {
		this.studentService = studentService;
		this.beanHelpers = beanHelpers;
	}

	@GetMapping("/whoiscalling")
    public ResponseEntity<?> whoIsCalling() {
	    SecurityContext context = beanHelpers.getSecurityContext();
        return ResponseEntity.ok(RestResultWrapper.ofValue(context.toString()));
    }

	@GetMapping
	public ResponseEntity<?> getAllStudents() {
		List<Student> students = studentService.getAllStudents();
		students.forEach(System.out::println);
		return ResponseEntity.ok(RestResultWrapper.ofValue(students));
	}

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody Student s) {
        s = studentService.createStudent(s);

        URI newResource = beanHelpers.getUriFor(s.getId());

//        URI newResource = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(s.getId())
//                .toUri();

		return ResponseEntity.created(newResource).body(RestResultWrapper.ofValue(s));
	}

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
        Student s = studentService.getStudent(id);
        if (s == null) {
            return ResponseEntity.badRequest().body(RestResultWrapper.ofError("Student with id: " + id + " not found"));
        }
        return ResponseEntity.ok(RestResultWrapper.ofValue(s));
    }

    @GetMapping("/byname/{name}")
    public ResponseEntity<?> getStudentByName(@PathVariable("name") String name) {
        List<Student> result = studentService.getByName(name);
        return ResponseEntity.ok(RestResultWrapper.ofValue(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
        Student s = studentService.getStudent(id);
        if (s == null) {
            RestResultWrapper<Student> rr = RestResultWrapper.ofError("Student with id " + id + " not found");
            return ResponseEntity.badRequest().body(rr);
        }
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateStudent(@RequestBody Student student, Authentication authenticaton) {
        int id = student.getId();
        Student s = studentService.getStudent(id);
        if (s == null) {
            RestResultWrapper<Student> rr = RestResultWrapper.ofError("Student with id " + id + " not found");
            return ResponseEntity.badRequest().body(rr);
        }
        studentService.updateStudent(student);
        return ResponseEntity.noContent().build();
    }
}