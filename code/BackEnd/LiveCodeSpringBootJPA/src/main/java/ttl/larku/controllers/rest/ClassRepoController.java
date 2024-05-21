package ttl.larku.controllers.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.domain.ClassWithCodeDTO;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.service.ClassRepoService;

@RestController
@RequestMapping("/classrepo")
public class ClassRepoController {

   @Autowired
   private ClassRepoService classRepoService;

   @GetMapping
   public List<ScheduledClass> getAll() {
      return classRepoService.getAllClasses();
   }

   @GetMapping("/short")
   public List<ClassWithCodeDTO> getAllShort() {
      return classRepoService.getAllClassesShort();
   }
}
