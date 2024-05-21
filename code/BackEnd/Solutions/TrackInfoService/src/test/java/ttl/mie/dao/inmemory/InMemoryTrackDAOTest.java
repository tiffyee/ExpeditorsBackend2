package ttl.mie.dao.inmemory;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InMemoryTrackDAOTest {
   @Autowired
   private InMemoryTrackDAO trackDAO;

   private BeanWrapper bw = new BeanWrapperImpl(TrackDTO.builder().build());

   @Test
   public void testGetAllTracks() {
      List<TrackDTO> tracks = trackDAO.findAll();

      out.println("tracks: " + tracks.size());
      tracks.forEach(out::println);
   }

   @Test
   public void testGetAllArtists() {
      List<ArtistDTO> artists =
            trackDAO.findAll().stream()
                  .flatMap(t -> t.artists().stream())
                  .distinct()
                  .toList();

      out.println("artists: " + artists.size());
      artists.forEach(out::println);
   }

   @Test
   public void testFindbySearchSpecSimple() {
      List<InMemorySearchSpec<TrackDTO>> specs = List.of(
            new InMemorySearchSpec<>(InMemorySearchSpec.SearchType.ContainsString,
                  "title", "Remember")
      );

      List<TrackDTO> result = trackDAO.findBySearchSpec(specs);

      result.forEach(out::println);

      assertEquals(1, result.size());
   }

   @Test
   public void testFindbySearchSpecChained() {
      var ss = new InMemorySearchSpec<TrackDTO>(InMemorySearchSpec.SearchType.Custom,
            "artist", "coltrane");
      ss.predicate = (t) ->
            t.artists().stream().anyMatch(a -> {
               var result = a.name().toUpperCase().contains(((String)ss.propValue).toUpperCase());
               return result;
            });

      List<InMemorySearchSpec<TrackDTO>> specs = List.of(
            new InMemorySearchSpec<>(InMemorySearchSpec.SearchType.ContainsString,
                  "title", "Remember"),
            ss
      );

      List<TrackDTO> result = trackDAO.findBySearchSpec(specs);

      result.forEach(out::println);

      assertEquals(2, result.size());
   }
}
