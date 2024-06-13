package ttl.mie.dao.inmemory;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InMemoryTrackDAOTest {
   @Autowired
   private InMemoryTrackDTODao trackDAO;

   @Test
   public void testGetAllTracks() {
      List<TrackDTO> tracks = trackDAO.findAll();

      System.out.println("tracks: " + tracks.size());
      assertEquals(100, tracks.size());
   }

   @Test
   public void testGetAllArtists() {
      List<ArtistDTO> artists =
            trackDAO.findAll().stream()
                  .flatMap(t -> t.artists().stream())
                  .distinct()
                  .toList();

      System.out.println("artists: " + artists.size());
//      artists.forEach(System.out::println);
   }
}
