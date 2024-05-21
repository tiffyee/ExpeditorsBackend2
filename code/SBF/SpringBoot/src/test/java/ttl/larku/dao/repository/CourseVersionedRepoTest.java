package ttl.larku.dao.repository;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import ttl.larku.domain.CourseVersioned;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CourseVersionedRepoTest {

   @Autowired
   private CourseVersionedRepo courseVersionedRepo;

   @Test
   public void testGetAll() {
      List<CourseVersioned> result = courseVersionedRepo.findAll();

      result.forEach(out::println);
   }

   @Test
   public void testUpdateExistingCourseChangesVersion() {
      assertThrows(ObjectOptimisticLockingFailureException.class, () -> {

         CountDownLatch startGate = new CountDownLatch(1);
         CountDownLatch endGate = new CountDownLatch(1);

         Runnable runnable = () -> {
            try {
               startGate.await();
            } catch (InterruptedException e) {
               throw new RuntimeException(e);
            }
            CourseVersioned course = courseVersionedRepo.findById(1).orElse(null);
            course.setCode("Beat You To It");

            courseVersionedRepo.save(course);
            endGate.countDown();
         };

         Thread th = new Thread(runnable);
         th.start();

         CourseVersioned course = courseVersionedRepo.findById(1).orElse(null);

         course.setCode("UUUUU");

         //Release the other Thread from its latch
         //And then wait for it to finish.
         startGate.countDown();

         try {
            endGate.await();
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }

         courseVersionedRepo.save(course);
      });


   }

   @Test
   public void testInsertingNewCourseDoesNotSetVersion() {
      CourseVersioned course = new CourseVersioned("XXX-333", "Very easy Course", 1.0F);

      CourseVersioned savedCourse = courseVersionedRepo.save(course);

      out.println(savedCourse);

      assertEquals(savedCourse.getVersion(), 1);
   }
}
