package expeditors.backend.dao;

import expeditors.backend.domain.Student;
import java.util.List;

public interface StudentDAO {
   Student insert(Student student);

   boolean update(Student student);

   boolean delete(int id);

   default boolean delete(Student student) {
      return delete(student.getId());
   }

   Student findById(int id);

   List<Student> findAll();

   public default List<Student> findByName(String name) {
      var result =  findAll().stream()
            .filter(s -> s.getName().contains(name))
            .toList();
      return result;
   }
}
