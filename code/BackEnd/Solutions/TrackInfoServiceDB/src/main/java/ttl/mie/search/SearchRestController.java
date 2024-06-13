package ttl.mie.search;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;

@RestController
@RequestMapping("/trackrest")
public class SearchRestController {

   @Autowired
   private JPASearchSpecService<TrackEntity, Integer, TrackRepo> searchService;

   @Autowired
   private TrackRepo targetRepository;

   @GetMapping("/search")
   public ResultWithPageData<?> getAllTracks(@RequestParam Map<String, Object> queryStrings) {

      var result = searchService.doSearch(queryStrings, targetRepository);

      return result;
   }

//   public List<Student> postProcess(List<Student> result) {
//      return result;
//   }
}
