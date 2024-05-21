package expeditors.backend.controller;


import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {

   @Autowired
   private StudentService studentService;

   @Autowired
   private UriCreator uriCreator;

//   @GetMapping
//   public List<Student> getAllStudents() {
//      List<Student> students = studentService.getStudents();
//      return students;
//   }

   @GetMapping
   public List<Student> getAllStudents(@RequestParam Map<String, String> queryStrings) {
      List<Student> students = null;
      if(queryStrings.isEmpty()){
         students = studentService.getStudents();
      }else {
        students = studentService.getByQueryParams(queryStrings);
      }
      return students;
   }

   @GetMapping("/{abc}")
   public ResponseEntity<?> getStudent(@PathVariable("abc") int id) {
      Student student = studentService.getStudent(id);
      if (student == null) {
         return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No student with id: " + id);
      }
      return ResponseEntity.ok(student);
   }

   @PostMapping
   public ResponseEntity<?> addStudent(@RequestBody @Valid Student student) {
      Student newStudent = studentService.createStudent(student);

      //http://localhost:8080/student/3

      URI newResource = uriCreator.getURI(newStudent.getId());
//      URI newResource = ServletUriComponentsBuilder
//            .fromCurrentRequest()
//            .path("/{id}")
//            .buildAndExpand(student.getId())
//            .toUri();

      //return ResponseEntity.created(newResource).body(newStudent);
      return ResponseEntity.created(newResource).build();
   }

//   public boolean validateStudent(Student student) {
//      return (student.getDob() != null && student.getName() != null);
//   }

   @DeleteMapping("/{id}")
   public ResponseEntity<?> deleteStudent(@PathVariable("id") int id) {
      boolean result = studentService.deleteStudent(id);
      if(!result) {
         return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("No student with id: " + id);
      }

      return ResponseEntity.noContent().build();
   }

   @PutMapping
   public ResponseEntity<?> updateStudent(@RequestBody Student student) {
     boolean result = studentService.updateStudent(student);
      if(!result) {
         return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT)
               .body("No student with id: " + student.getId());
      }

      return ResponseEntity.noContent().build();
   }

//   /**
//    * Handle validation errors for automatic validation, i.e with the @Valid annotation.
//    * For this to be invoked, you have to have a controller argument of object type
//    * to which you have attached the @Valid annotation.
//    * Look at the end of StudentRestController for an example, which may be commented out
//    * by default.
//    * @param ex the exception thrown
//    * @param request the incoming request
//    * @return a bad request + restresult that contains the errors
//    */
//   @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//   public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                            WebRequest request) {
//      var errors = ex.getFieldErrors();
//      List<String> errMsgs = errors.stream()
//            .map(error -> "@Valid error:" + error.getField() + ": " + error.getDefaultMessage()
//                  + ", supplied Value: " + error.getRejectedValue())
//            .collect(toList());
//
//      return ResponseEntity.badRequest().body(errMsgs);
//
//   }

}

//REpresentational State Transfer
