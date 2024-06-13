package ttl.mie.domain.track;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TrackDTOTest {

   @Test
   public void testAddArtistIgnoresDuplicates() {
      TrackDTO track = TrackDTO.builder()
            .title("Like A MoonBean")
            .album("Starry Night")
            .artist("Moondog")
            .build();
      assertNotNull(track);
      int numArtists = track.artists().size();
      System.out.println("track: " + track);

      var newArtist = new ArtistDTO("Charlie Byrd");
      track.addArtist(newArtist);
      track.addArtist(newArtist);

      int newNum = track.artists().size();
      assertEquals(numArtists + 1, newNum);
   }
}
