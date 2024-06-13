package ttl.mie.dao.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@SpringBootTest
public class TestTrackRepoSearchByExample {

   @PersistenceContext
   private EntityManager em;

   @Autowired
   private TrackRepo trackRepo;

   /**
    * Test Query by Example
    * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#reference
    */
   @Test
   public void testQueryByExample() {
      //This is the 'probe', i.e. your example entity object
//      TrackEntity probe = TrackEntity.artist("Big John Patton").build();

      TrackEntity probe = TrackEntity.album("Moonlight in Vermont").build();


      //Here we can set up rules on how to do the matching
      ExampleMatcher matcher = ExampleMatcher.matchingAny()
            .withMatcher("artist", contains())
            .withMatcher("album", contains())
            .withIgnorePaths("trackId", "artistId")
            .withIgnoreCase()
            .withIgnoreNullValues();

      //Now we make our Example from the probe and the matcher
      Example<TrackEntity> example = Example.of(probe, matcher);
      List<TrackEntity> results = trackRepo.findAll(example);

      //_Note that this search is limited to a findAll().  There
      //is now way to do a Join.  So if your entity has a collection
      //relationship, as we do here with our Track and Artists, then
      //this printout will throw a LazyInstantiationException.
      //So this Query By Example mechanism is sort of limited.
//      results.forEach(out::println);
      assertEquals(1, results.size());
   }
}
