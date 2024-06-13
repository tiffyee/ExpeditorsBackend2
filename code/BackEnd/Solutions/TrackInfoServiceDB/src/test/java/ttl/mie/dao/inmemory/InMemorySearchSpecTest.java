package ttl.mie.dao.inmemory;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.TrackDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InMemorySearchSpecTest {

   List<TrackDTO> tracks = List.of(
         TrackDTO.builder().title("Like A MoonBean").album("Starry Night")
               .artist("Moondog").length(200).build(),
         TrackDTO.builder().title("It's Funny That Way").album("This is It")
               .artist("Ella").length(2000).build(),
         TrackDTO.builder().title("Shiny Like your eyes").album("Rippled Starlight")
               .artist("Josie Cat").length(500).build()
   );
   private BeanWrapper bw = new BeanWrapperImpl(TrackDTO.builder().build());

   TrackDTO example = TrackDTO.builder().title("Like A MoonBean").album("Starry Night")
         .artist("Moondog").build();

   @Test
   public void testSearchSpecEquals() {

      InMemorySearchSpec<TrackDTO> searchSpec = new InMemorySearchSpec<>(InMemorySearchSpec.SearchType.Equal,
            "title", "Like A MoonBean");

      Predicate<TrackDTO> pred = searchSpec.predicate;

      List<TrackDTO> result = tracks.stream()
            .filter(pred)
            .toList();

      System.out.println("result: " + result);
      assertEquals(1, result.size());
   }

   @Test
   public void testSearchSpecNotEquals() {

      InMemorySearchSpec<TrackDTO> searchSpec = new InMemorySearchSpec<>(InMemorySearchSpec.SearchType.NotEqual,
            "title", "Like A MoonBean");

      Predicate<TrackDTO> pred = searchSpec.predicate;

      List<TrackDTO> result = tracks.stream()
            .filter(pred)
            .toList();

      System.out.println("result: " + result);
      assertEquals(2, result.size());
   }

   @Test
   public void testSearchSpecGreater() {

      InMemorySearchSpec<TrackDTO> searchSpec = new InMemorySearchSpec<>(InMemorySearchSpec.SearchType.Greater,
            "length", Duration.ofSeconds(500));

      Predicate<TrackDTO> pred = searchSpec.predicate;

      List<TrackDTO> result = tracks.stream()
            .filter(pred)
            .toList();

      result.forEach(System.out::println);
      assertEquals(1, result.size());
   }

   @Test
   public void testSearchSpecContainsIC() {

      InMemorySearchSpec<TrackDTO> searchSpec = new InMemorySearchSpec<>(
            InMemorySearchSpec.SearchType.ContainsStringIC,
            "artists", "oon");

      Predicate<TrackDTO> pred = searchSpec.predicate;

      String propName = "artists";
      String propValue = "oon";

      searchSpec.predicate = (t) ->
            t.artists().stream().anyMatch(a -> {
               var result = a.name().toUpperCase()
                     .contains(((String)searchSpec.propValue).toUpperCase());
               return result;
            });

      List<TrackDTO> result = tracks.stream()
            .filter(searchSpec.predicate)
            .toList();

      result.forEach(System.out::println);
      assertEquals(1, result.size());
      assertEquals("Moondog", result.getFirst().artists().getFirst().name());
   }

}
