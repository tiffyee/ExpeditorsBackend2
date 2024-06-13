package ttl.larku.controllers.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ttl.larku.dao.repository.StudentRepo;
import ttl.larku.domain.RestResult;
import ttl.larku.domain.StudentPhoneSummary;

import java.util.List;

@RestController
@RequestMapping("/adminrest/student")
public class StudentRepoRestController {

    private final BeanHelpers uriCreator;
    private StudentRepo sRepo;

    public StudentRepoRestController(StudentRepo sRepo, BeanHelpers uriCreator) {
        this.sRepo = sRepo;
        this.uriCreator = uriCreator;
    }

    @GetMapping("phoneSummary")
    public ResponseEntity<?> getPhoneSummaries() {
        List<StudentPhoneSummary> students = sRepo.findAllStudentPhoneSummariesBy();
        return ResponseEntity.ok(new RestResult().entity(students));
    }
}
