package expeditors.backend.adoptapp.search;

import expeditors.backend.adoptapp.dao.repository.AdopterRepo;
import expeditors.backend.adoptapp.domain.Adopter;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/petreposervice")
public class SearchRestController {

   @Autowired
   private JPASearchSpecService<Adopter, Integer, AdopterRepo> searchService;

//   @Autowired
//   private JPASearchSpecServicePlusPlus<Adopter, Integer, AdopterRepo> searchServicePlusPlus;

   @Autowired
   private AdopterRepo targetRepository;

   @GetMapping("/search")
   public ResultWithPageData<?> getAllTracks(@RequestParam Map<String, Object> queryStrings) {

      var result = searchService.doSearch(queryStrings, targetRepository);

//      searchServicePlusPlus.findAllSimple = targetRepository::findAllWithPets;
//      searchServicePlusPlus.findAllWithPageable = targetRepository::findAllForPaging;
//      var result = searchServicePlusPlus.doSearch(queryStrings, targetRepository);

      return result;
   }

//   public List<Student> postProcess(List<Student> result) {
//      return result;
//   }
}
