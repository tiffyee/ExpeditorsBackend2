package ttl.mie.service;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.domain.track.entity.TrackEntity;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class TrackRepoServiceQueriesTest {

   @Autowired
   private TrackRepoService trackService;

   @Value("${pricing.track.upper_limit}")
   private double upperLimit;

   @Test
   public void testSimpleQueryTitle() {
      var propMap = Map.of("title", "Shadow");

      List<TrackEntity> tracks = trackService.getTracksBy(propMap);

      assertEquals(1, tracks.size());
   }

   @Test
   public void testQuerybySearchSpec() {
      Map<String, Object> propMap = Map.of("c.title", "Remember");

      List<TrackEntity> tracks = trackService.getTracksByRequestParams(propMap);

      tracks.forEach(out::println);

      assertEquals(1, tracks.size());
   }

   @Test
   public void testMultipleproperties() {
      Map<String, Object> propMap = Map.of("c.title", "Remember",
            "c.artists.name", "Herb");

      List<TrackEntity> tracks = trackService.getTracksByRequestParams(propMap);

      out.println("tracks: " + tracks.size());
      tracks.forEach(out::println);

      assertEquals(3, tracks.size());
   }

   @Test
   public void testUrlContains() {
      Map<String, Object> propMap = Map.of("c.artists.url", "http");

      List<TrackEntity> tracks = trackService.getTracksByRequestParams(propMap);

      out.println("tracks: " + tracks.size());
      tracks.forEach(t -> {
         out.println(t.getTitle() + ": ");
         t.getArtists().forEach(a -> out.println("   " + a.getName() + ", url: " + a.getUrl()));
      });

//      assertEquals(94, tracks.size());
   }
}
