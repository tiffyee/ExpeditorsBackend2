package expeditors.backend.controller;


import expeditors.backend.domain.Student;
import expeditors.backend.service.StudentService;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class StudentControllerUnitTest {

   @Mock
   private StudentService studentService;

   @Mock
   private UriCreator uriCreator;

   @InjectMocks
   private StudentController controller;

   List<Student> students = List.of(
         new Student("Frank", LocalDate.parse("2000-10-10")),
         new Student("Rachna", LocalDate.parse("1960-11-02"))
   );

   @Test
   public void testGetAll() {

      Mockito.when(studentService.getStudents()).thenReturn(students);

      List<Student> result = controller.getAllStudents(Map.of());
      assertEquals(2, result.size());

      Mockito.verify(studentService).getStudents();
   }

   @Test
   public void testAddStudent() throws URISyntaxException {
      String expectedLocHeader = "http://localhost:8080/student/0";
      URI expecedURI = new URI(expectedLocHeader);

      Mockito.when(studentService.createStudent(students.getFirst())).thenReturn(students.getFirst());
      Mockito.when(uriCreator.getURI(students.getFirst().getId())).thenReturn(expecedURI);

      ResponseEntity<?> response = controller.addStudent(students.getFirst());

      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      String actualLocHeader = response.getHeaders().getFirst("Location");

      assertEquals(expectedLocHeader, actualLocHeader);

      Mockito.verify(studentService).createStudent(students.getFirst());
      Mockito.verify(uriCreator).getURI(students.getFirst().getId());
   }
}
