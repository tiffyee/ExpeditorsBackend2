package ttl.mie.dao.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.dao.repository.track.ArtistRepo;
import ttl.mie.domain.track.entity.ArtistEntity;

@SpringBootTest
public class TestArtistRepoEntity {

   @Autowired
   private ArtistRepo artistRepo;
   @Test
   public void testGetAll() {
      List<ArtistEntity> artists = artistRepo.findAll();

      System.out.println("artists: " + artists.size());
      artists.forEach(System.out::println);
   }
}
