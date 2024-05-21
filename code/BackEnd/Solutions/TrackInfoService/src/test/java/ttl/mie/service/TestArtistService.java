package ttl.mie.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.TrackDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestArtistService {

   @Autowired
   private ArtistService artistService;

   @Test
   public void testGetTracksForArtistByName() {
      List<TrackDTO> result = artistService.getTracksForArtistByName("Herb Ellis");

      System.out.println("result: " + result.size());
      result.forEach(System.out::println);

      assertEquals(2, result.size());
   }
}
