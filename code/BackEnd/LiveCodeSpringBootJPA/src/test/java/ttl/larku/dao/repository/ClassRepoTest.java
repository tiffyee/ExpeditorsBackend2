package ttl.larku.dao.repository;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.larku.domain.ClassWithCodeDTO;
import ttl.larku.domain.ScheduledClass;

import static java.lang.System.out;

@SpringBootTest
public class ClassRepoTest {

   @Autowired
   private ClassRepo classRepo;

   @Autowired
   private CourseRepo courseRepo;

   @Test
   public void testFindAll() {
      List<ScheduledClass>  result = classRepo.findAll();

      out.println("result: " + result.size());
      result.forEach(out::println);
   }

   @Test
   public void testFindAllWithCourse() {
      List<ScheduledClass>  result = classRepo.findAllWithCourse();

      out.println("result: " + result.size());
      result.forEach(out::println);
   }

   @Test
   public void testFindAllWithCourseNatively() {
      List<ScheduledClass>  result = classRepo.findAllWithCourseNatively();

      out.println("result: " + result.size());
      result.forEach(out::println);
   }

   @Test
   public void testFindWithObjArray() {
      List<Object[]>  result = classRepo.findWithObjArray();

      out.println("result: " + result.size());
      result.forEach(out::println);
   }

   @Test
   public void testFindWithDatesAndCode() {
      List<ClassWithCodeDTO>  result = classRepo.findWithDatesAndCode();

      out.println("result: " + result.size());
      result.forEach(out::println);
   }

   @Test
   public void testAddScheduledClass() {
//      Optional<Course> opt = courseRepo.findById(2).orElse(null);
      courseRepo.findById(2).ifPresent(course ->  {
         ScheduledClass sc = new ScheduledClass(course, LocalDate.of(2024, 6, 1),
               LocalDate.of(2024, 9, 1));

         sc = classRepo.save(sc);

         int stop = 0;
      });
   }
}
