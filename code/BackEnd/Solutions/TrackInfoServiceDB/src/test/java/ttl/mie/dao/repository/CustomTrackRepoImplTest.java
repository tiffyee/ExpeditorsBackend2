package ttl.mie.dao.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.dto.TrackWithArtistsNames;

import static java.lang.System.out;

@SpringBootTest
public class CustomTrackRepoImplTest {

   @Autowired
   private TrackRepo trackRepo;

   @Test
   public void testFindTackWithArtists() {
      List<TrackWithArtistsNames> result = trackRepo.findTracksWithArtistNames();

      out.println("result: " + result.size());
      result.forEach(out::println);
   }

}
