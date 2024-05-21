package ttl.larku.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttl.larku.dao.repository.ClassRepo;
import ttl.larku.domain.ClassWithCodeDTO;
import ttl.larku.domain.ScheduledClass;

@Service
public class ClassRepoService {

   @Autowired
   private ClassRepo classRepo;

   public List<ScheduledClass> getAllClasses() {
      return classRepo.findAllWithCourse();
//      return classRepo.findAll();
   }

   public List<ClassWithCodeDTO> getAllClassesShort() {
      return classRepo.findWithDatesAndCode();
   }
}
