package ttl.mie.domain.track;

import org.junit.jupiter.api.Test;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistDTOTest {

   @Test
   public void testArtistDTOPrimaryConstructor() {
      var adto = new ArtistDTO("Frank Sinatra", "Franky", "Cool guy");
      assertEquals(0, adto.tracks().size());
   }

   @Test
   public void testArtistDTOSmallConstructor() {
      var adto = new ArtistDTO("Frank Sinatra", "Franky", "Cool guy");
      adto.addAudioTrackInfo(TrackDTO.builder().build());
      assertEquals(1, adto.tracks().size());
   }
}
