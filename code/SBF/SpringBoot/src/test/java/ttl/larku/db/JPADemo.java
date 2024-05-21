package ttl.larku.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ttl.larku.domain.Course;
import ttl.larku.domain.PhoneNumber;
import ttl.larku.domain.Student;

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

//   public static void main(String[] args) {
//      JPADemo jpaDemo = new JPADemo();
//
////      jpaDemo.createCourse();
////      jpaDemo.dumpAllCourses();
//
//      jpaDemo.addAPhoneNumberToAStudent();
////      jpaDemo.removePhoneNumberFromStudent();
//
//      jpaDemo.dumpAllStudents();
////      jpaDemo.dumpAllPhonenumbers();
//   }

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
   public void createCourse() {
      try (EntityManager manager = emf.createEntityManager();) {
         manager.getTransaction().begin();

         Course course = new Course("Eco-101", "Intro to Economics", 2.5F);

         manager.persist(course);

         manager.getTransaction().commit();
      }
   }

   @Test
   public void dumpAllStudents() {
      List<Student> result;
      try (EntityManager manager = emf.createEntityManager();) {

//         TypedQuery<Student> query = manager.createQuery("select s from Student s", Student.class);
         TypedQuery<Student> query =
               manager.createQuery("select s from Student s left join fetch s.phoneNumbers", Student.class);

         result = query.getResultList();

         out.println("num : " + result.size());
         result.forEach(out::println);
      }
//      out.println("num : " + result.size());
//      result.forEach(out::println);
   }

   public void dumpAllPhonenumbers() {
      try (EntityManager manager = emf.createEntityManager();) {

         TypedQuery<PhoneNumber> query = manager.createQuery("select s from PhoneNumber s", PhoneNumber.class);

         List<PhoneNumber> result = query.getResultList();

         out.println("num : " + result.size());
         result.forEach(out::println);
      }
   }


   @Test
   public void addAPhoneNumberToAStudent() {
      try (EntityManager manager = emf.createEntityManager();) {

         manager.getTransaction().begin();
//         TypedQuery<Student> query = manager.createQuery("select s from Student s", Student.class);
         TypedQuery<Student> query =
               manager.createQuery("select s from Student s left join fetch s.phoneNumbers where s.id = :id", Student.class);

         query.setParameter("id", 2);

         Student result = query.getSingleResult();

         PhoneNumber pn = new PhoneNumber(PhoneNumber.Type.HOME, "99999 38380433403");

         result.addPhoneNumber(pn);
//         pn.setStudent(result);

         manager.getTransaction().commit();

         out.println("result: " + result);
      }
   }

   @Test
   public void removePhoneNumberFromStudent() {
      try (EntityManager manager = emf.createEntityManager();) {

         manager.getTransaction().begin();
//         TypedQuery<Student> query = manager.createQuery("select s from Student s", Student.class);
         TypedQuery<Student> query =
               manager.createQuery("select s from Student s left join fetch s.phoneNumbers where s.id = :id", Student.class);

         query.setParameter("id", 2);

         Student result = query.getSingleResult();

         PhoneNumber phoneNumber = result.getPhoneNumbers()
               .stream().filter(pn -> pn.getType() == PhoneNumber.Type.HOME)
               .findFirst().orElse(null);

//         manager.merge(phoneNumber);

         result.removePhoneNumber(phoneNumber);
//         phoneNumber.setStudent(null);
//         manager.remove(phoneNumber);

         manager.getTransaction().commit();

         out.println("result: " + result);
      }
   }
}
