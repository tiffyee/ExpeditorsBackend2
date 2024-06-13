package ttl.larku.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;

import static java.lang.System.out;

public class JPADemo {


   private EntityManagerFactory emf;

   @BeforeEach
   public void beforeEach() {
      String pw = System.getenv("DB_PASSWORD");

      var props = Map.of(
            //"jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/larku",
            "jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5433/larku",
            "jakarta.persistence.jdbc.user", "larku",
            "jakarta.persistence.jdbc.password", pw,
            "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",

            "jakarta.persistence.spi.PersistenceProvider", "org.hibernate.jpa.HibernatePersistenceProvider"
      );

      //emf = Persistence.createEntityManagerFactory("LarkUPU_SE");
      emf = Persistence.createEntityManagerFactory("LarkUPU_SE", props);
   }

   @Test
   public void dumpAllCourses() {
      try (EntityManager manager = emf.createEntityManager();) {

         TypedQuery<Course> query = manager.createQuery("select c from Course c", Course.class);

         List<Course> courses = query.getResultList();

         out.println("num courses: " + courses.size());
         courses.forEach(out::println);
      }
   }

   @Test
   public void addCourse() {
      try (EntityManager manager = emf.createEntityManager();) {
         Course course = new Course("ASTRO-505", "Master Astronomy", 4.0F);

         manager.getTransaction().begin();

         manager.persist(course);

         manager.getTransaction().commit();

         dumpAllCourses();

      }
   }

   @Test
   public void updateCourse() {
      Course course = null;
      try (EntityManager manager = emf.createEntityManager();) {
         course = manager.find(Course.class, 1);

         manager.getTransaction().begin();


         course.setCode("ZZZZZ-303");

         manager.getTransaction().commit();

         dumpAllCourses();
      }
   }

   @Test
   public void dumpAllClasses() {
      try (EntityManager manager = emf.createEntityManager();) {

//         TypedQuery<ScheduledClass> query = manager.createQuery("select c from ScheduledClass c", ScheduledClass.class);
         TypedQuery<ScheduledClass> query =
               manager.createQuery("select sc from ScheduledClass sc join fetch sc.course", ScheduledClass.class);

         List<ScheduledClass> courses = query.getResultList();

         out.println("num courses: " + courses.size());
         courses.forEach(out::println);
      }
   }

   @Test
   public void insertScheduledClass() {
      try (EntityManager manager = emf.createEntityManager();) {
         TypedQuery<Course> bot202Query = manager.createQuery("select c from Course c where c.code = :code", Course.class);
         bot202Query.setParameter("code", "BOT-202");

         Course course = bot202Query.getSingleResult();

         ScheduledClass sc = new ScheduledClass(course, LocalDate.of(2024, 10, 10), LocalDate.of(2025, 05, 10));

         manager.getTransaction().begin();

         manager.persist(sc);

         manager.getTransaction().commit();

      }
   }

   @Test
   public void testNativeQueriesForScheduledClass() {
      try (EntityManager manager = emf.createEntityManager();) {
         String sql = "select sc.* from scheduledclass sc join course c on sc.course_id = c.id";

         Query query = manager.createNativeQuery(sql, ScheduledClass.class);
         List<ScheduledClass> result = query.getResultList();

         out.println(result);
      }
   }
}
