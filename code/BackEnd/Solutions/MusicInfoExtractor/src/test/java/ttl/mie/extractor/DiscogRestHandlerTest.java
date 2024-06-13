package ttl.mie.extractor;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.SearchResults;
import ttl.mie.domain.track.dto.TrackDTO;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DiscogRestHandlerTest {

   @Autowired
   private DiscogRestHandler discogRestHandler;

   @Test
   public void testGetHenryThreadgillArtistInfo() {
      String key = "Henry Threadgill's Zooid#Tomorrow Sunny / The Revelry, Spp";


      SearchResults searchResults = discogRestHandler.findArtistsForKey(key);
      List<ArtistDTO> artists = searchResults.artists();

      artists.forEach(out::println);

      assertEquals(7, artists.size());
   }

   @Test
   public void testGetMidgeWilliamsShouldFindMasterId() {
      String key = "MIDGE WILLIAMS AND HER JAZZ JESTERS#";


      SearchResults searchResults = discogRestHandler.findArtistsForKey(key);
      List<ArtistDTO> artists = searchResults.artists();

      artists.forEach(out::println);

      assertEquals(1, artists.size());
   }

   @Test
   public void testGetInfoAlsoGetsImageUrlsIfAvailable() {

      TrackDTO trackDTO = TrackDTO.builder()
            .group("Henry Threadgill's Zooid")
            .artist("Henry Threadgill's Zooid")
            .album("Tomorrow Sunny / The Revelry, Spp")
            .build();

      String key = trackDTO.group() + "#" + trackDTO.album();


      SearchResults searchResults = discogRestHandler.findArtistsForKey(key);
      List<ArtistDTO> artists = searchResults.artists();

      artists.forEach(out::println);

      assertEquals(7, artists.size());

      String imageUrl = (String)searchResults.otherProps().get("imageUrl");

      out.println("imageUrl: " + imageUrl);
      assertNotNull(imageUrl);
   }

   @Test
   public void testGetInfoReturnsTracksWithImageUrlAdded() {

      List<TrackDTO> tracks = new ArrayList<TrackDTO>();
      TrackDTO trackDTO = TrackDTO.builder()
            .group("Henry Threadgill's Zooid")
            .artist("Henry Threadgill's Zooid")
            .album("Tomorrow Sunny / The Revelry, Spp")
            .build();
      tracks.add(trackDTO);

      assertEquals("", tracks.get(0).imageUrl());

      discogRestHandler.getArtistInfoFromDiscogs(tracks);

      var returnedTrack = tracks.get(0);
      out.println(returnedTrack);

      String imageUrl = (String)returnedTrack.imageUrl();

      out.println("imageUrl: " + imageUrl);

      assertNotEquals("", tracks.get(0).imageUrl());
   }
}
