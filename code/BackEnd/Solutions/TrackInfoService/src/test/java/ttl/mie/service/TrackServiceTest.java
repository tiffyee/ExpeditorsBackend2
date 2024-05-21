package ttl.mie.service;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TrackServiceTest {

   @Autowired
   private TrackService trackService;

   @Value("${pricing.track.upper_limit}")
   private double upperLimit;

   @Test
   public void testGetAll() {
      List<TrackDTO> tracks = trackService.getAllTracks();

      System.out.println("tracks: " + tracks);
      assertEquals(6, tracks.size());
      assertNotNull(tracks.getFirst().price());
   }


   @Test
   public void testGetExistingTrack() {
      int id = 2;
      TrackDTO track = trackService.getById(id)
                  .orElse(null);

      assertNotNull(track);
      assertNotNull(track.price());
   }

   @Test
   public void testGetNonExistingTrack() {
      int id = 20000;
      TrackDTO track = trackService.getById(id)
            .orElse(null);

      assertNull(track);
   }

   @Test
   public void testDeleteExistingTrack() {
      int id = 2;
      TrackDTO track = trackService.getById(id)
            .orElse(null);
      assertNotNull(track);
      boolean result = trackService.deleteTrack(track.trackId());
      assertTrue(result);

      //Look for the track again
      var track2 = trackService.getById(track.trackId());
      assertTrue(track2.isEmpty());
   }

   @Test
   public void testDeleteNonExistingTrack() {
      int id = 20000;
      TrackDTO track = trackService.getById(id)
            .orElse(null);
      assertNull(track);
      boolean result = trackService.deleteTrack(id);
      assertFalse(result);
   }

   @Test
   public void testUpdateExistingTrack() {
      int id = 2;
      TrackDTO track = trackService.getById(id)
            .orElse(null);
      assertNotNull(track);
      var newTitle = "You'll Remember April";
      var newDTO = TrackDTO.copyWithTitle(newTitle, track);

      boolean result = trackService.updateTrack(newDTO);
      assertTrue(result);

      track = trackService.getById(id).orElse(null);
      assertNotNull(track);

      assertEquals(newTitle, track.title());
   }

   @Test
   public void testUpdateNonExistingTrack() {
      int id = 2000;
      TrackDTO track = TrackDTO.builder()
            .id(id)
            .title("Like A MoonBean")
            .artist("Moondog")
            .build();

      boolean result = trackService.updateTrack(track);
      assertFalse(result);

   }

   @Test
   public void testAddTrack() {
      TrackDTO track = TrackDTO.builder()
            .title("Like A MoonBean")
            .album("Starry Night")
            .artist("Moondog")
            .build();
      assertEquals(0, track.trackId());

      TrackDTO newTrack = trackService.addTrack(track);
      assertTrue(newTrack.trackId() > 0);
   }

   @Test
   public void testAddPriceToTrack() {
      int id = 2;
      TrackDTO track = trackService.getById(id)
            .orElse(null);
      assertNotNull(track);

      System.out.println("track: " + track);
      assertTrue(track.price().doubleValue() >= 0 && track.price().doubleValue() <= upperLimit);
   }

   @Test
   public void testAddArtistToTrack() {
      int id = 2;
      TrackDTO track = trackService.getById(id)
            .orElse(null);
      assertNotNull(track);
      int numArtists = track.artists().size();
      System.out.println("track: " + track);

      var newArtist = new ArtistDTO("Charlie Byrd");
      track.addArtist(newArtist);
      track.addArtist(newArtist);

      int newNum = track.artists().size();
      assertEquals(numArtists + 1, newNum);

      trackService.updateTrack(track);

      var newTrack = trackService.getById(track.trackId()).orElse(null);
      assertNotNull(newTrack);

      assertEquals(numArtists + 1, newTrack.artists().size());

      assertTrue(track.price().doubleValue() >= 0 && track.price().doubleValue() <= upperLimit);
   }
}
