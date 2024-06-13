package expeditors.backend.adoptapp.search;

import expeditors.backend.adoptapp.dao.repository.AdopterRepo;
import expeditors.backend.adoptapp.domain.Adopter;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestJpaSearchSpecService {

   @Autowired
   private JPASearchSpecService<Adopter, Integer, AdopterRepo> searchService;

   @Autowired
   private JPASearchSpecServicePlusPlus<Adopter, Integer, AdopterRepo> searchServicePlusPlus;

   @Autowired
   private AdopterRepo targetRepository;

   @Test
   public void testReturnOnlyAdopterDTOWithNoQueryStrings() {
      Map<String, Object> noQueryStrings = new HashMap<>();


      var result = searchServicePlusPlus.doSearch(noQueryStrings, targetRepository);

      System.out.println("result: " + result);
   }

   @Test
   public void testReturnOnlyAdopterDTOWithOnlyPageData() {
      Map<String, Object> pageQueryStrings = new HashMap<>(Map.of("page", 2,
            "pageSize", 10));

      searchServicePlusPlus.findAllWithPageable = targetRepository::findAllForPaging;
      searchServicePlusPlus.findAllSimple = targetRepository::findAllWithPets;

      var result = searchServicePlusPlus.doSearch(pageQueryStrings, targetRepository);

      System.out.println("result: " + result);
   }

   @Test
   public void testReturnOnlyAdopterDTOWithSearchSpec() {
      Map<String, Object> queryStrings = new HashMap<>(Map.of("s.pets", "3"));

      searchServicePlusPlus.findAllWithPageable = targetRepository::findAllForPaging;
      searchServicePlusPlus.findAllSimple = targetRepository::findAllWithPets;

      var result = searchServicePlusPlus.doSearch(queryStrings, targetRepository);

      System.out.println("result: " + result.result().size());
      result.result().forEach(System.out::println);
      System.out.println(result.extraProps());
   }
}
