package ttl.mie.service;

import java.time.Duration;
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
      Map<String, String> propMap = Map.of("title", "Shadow");

      List<TrackDTO> tracks = trackService.getTracksBy(propMap);

      assertEquals(1, tracks.size());
   }

   @Test
   public void testSimpleQueryArtist() {
      Map<String, String> propMap = Map.of("artist", "John");

      List<TrackDTO> tracks = trackService.getTracksBy(propMap);

      assertEquals(2, tracks.size());
   }

   @Test
   public void testQuerybySearchSpec() {
      Map<String, String> propMap = Map.of("c.title", "Remember");

      List<TrackDTO> tracks = trackService.getTracksBySearchSpec(propMap);

      tracks.forEach(out::println);

      assertEquals(1, tracks.size());
   }

   @Test
   public void testMultipleproperties() {
      Map<String, String> propMap = Map.of("c.title", "Remember"
      , "c.artist", "John");

      List<TrackDTO> tracks = trackService.getTracksBySearchSpec(propMap);
      assertEquals(3, tracks.size());
   }

   @Test
   public void testSearchWithSearchSymbols() {
      Map<String, String> propMap = Map.of("c.title", "Remember");
//      var propMap = Map.of("e.title", "Test-I'll Remember April");

      List<TrackDTO> result = trackService.getTracksBySearchSpec(propMap);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(1, result.size());
   }

   @Test
   public void testSearchWithSearchSymbolsMultipleCriteria() {
      Map<String, String> propMap = Map.of("c.title", "Remember",
            "c.artist", "Herb");

      List<TrackDTO> result = trackService.getTracksBySearchSpec(propMap);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(3, result.size());
   }

   @Test
   public void testSearchForFormat() {
//      Map<String, String> propMap = Map.of("g.length", Duration.ofSeconds(480));
      Map<String, String> propMap = Map.of("e.format", "FLAC");

      List<TrackDTO> result = trackService.getTracksBySearchSpec(propMap);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(1, result.size());
   }

   @Test
   public void testSearchForDurationWithString() {
      var dur = Duration.parse("PT5M");
      //Map<String, String> propMap = Map.of("g.length", Duration.ofSeconds(480));
      Map<String, String> propMap = Map.of("g.length", "PT8M");

      List<TrackDTO> result = trackService.getTracksBySearchSpec(propMap);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(1, result.size());
   }
}
