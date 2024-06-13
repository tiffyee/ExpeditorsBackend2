package ttl.mie.service;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ttl.mie.domain.track.dto.TrackDTO;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TrackServiceQueriesTest {

   @Autowired
   private TrackService trackService;

   @Value("${pricing.track.upper_limit}")
   private double upperLimit;

   @Test
   public void testSimpleQueryTitle() {
      var propMap = Map.of("title", "Shadow");

      List<TrackDTO> tracks = trackService.getTracksBy(propMap);

      assertEquals(1, tracks.size());
   }

   @Test
   public void testSimpleQueryArtist() {
      var propMap = Map.of("artist", "John");

      List<TrackDTO> tracks = trackService.getTracksBy(propMap);

      assertEquals(2, tracks.size());
   }

   @Test
   public void testQuerybySearchSpec() {
      var propMap = Map.of("title", "Remember");

      List<TrackDTO> tracks = trackService.getTracksBySearchSpec(propMap);

      tracks.forEach(out::println);

      assertEquals(1, tracks.size());
   }

   @Test
   public void testMultipleproperties() {
      var propMap = Map.of("title", "Remember",
            "artist", "Herb");

      List<TrackDTO> tracks = trackService.getTracksBySearchSpec(propMap);

      out.println("tracks: " + tracks.size());
      tracks.forEach(out::println);

      assertEquals(3, tracks.size());
   }
}
