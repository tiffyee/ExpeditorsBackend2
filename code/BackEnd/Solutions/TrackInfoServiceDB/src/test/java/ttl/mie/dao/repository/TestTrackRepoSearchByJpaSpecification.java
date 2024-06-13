package ttl.mie.dao.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.search.JpaTrackSearchSpec;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ttl.mie.search.JpaSearchSpecSupport.SearchType;

@SpringBootTest
public class TestTrackRepoSearchByJpaSpecification {

   @PersistenceContext
   private EntityManager em;

   @Autowired
   private TrackRepo trackRepo;

   @Test
   public void testSearchWithSearchSpecification() {
      JpaTrackSearchSpec spec = new JpaTrackSearchSpec(SearchType.ContainsString,
            "album", "Alone Together");

      List<TrackEntity> result = trackRepo.findAll(spec);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(1, result.size());
   }

   @Test
   public void testSearchWithMultipleSearchSpecifications() {
      JpaTrackSearchSpec albumSpec = new JpaTrackSearchSpec(SearchType.ContainsString,
            "album", "Alone Together");

      JpaTrackSearchSpec titleSpec = new JpaTrackSearchSpec(SearchType.ContainsString,
            "title", "My Funny Valentine");

      List<TrackEntity> result = trackRepo.findAll(albumSpec.or(titleSpec));
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(2, result.size());
   }

   @Test
   public void testSearchForCollectionAttributeForArtists() {
      JpaTrackSearchSpec artistSpec = new JpaTrackSearchSpec(SearchType.Custom,
            "artist", "Herb Ellis",
            (builder, root, propName, value) -> {
               return builder.like(builder.lower(root.get("artists").get("name")), "%"
                     + value.toString().toLowerCase() + "%");

            });

      List<TrackEntity> result = trackRepo.findAll(artistSpec);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(2, result.size());
   }

   @Test
   public void testSearchWithNestPropertiesForArtists() {
      JpaTrackSearchSpec artistSpec = new JpaTrackSearchSpec(SearchType.Equal,
            "artists.name", "Herb Ellis");

      List<TrackEntity> result = trackRepo.findAll(artistSpec);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(2, result.size());
   }

   @Test
   public void testSearchForDurationGreaterThan() {
      JpaTrackSearchSpec artistSpec = new JpaTrackSearchSpec(SearchType.Greater,
            "duration", Duration.ofMinutes(5));

      List<TrackEntity> result = trackRepo.findAll(artistSpec);
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(3, result.size());
   }

   @Test
   public void testSearchWithCustomPredicate() {
      JpaTrackSearchSpec discogsIdIsNotNull = new JpaTrackSearchSpec(SearchType.Custom,
            "artists.discogsId", "",
            (builder, root, propName, value) -> {
               return builder.isNotNull(builder.lower(root.get("artists").get("discogsId")));
            });

      List<TrackEntity> result = trackRepo.findAll(discogsIdIsNotNull);
      out.println("result: " + result.size());
      result.forEach(out::println);

   }

   @Test
   public void testForSize() {
      JpaTrackSearchSpec artistsSizeIs2 = new JpaTrackSearchSpec(SearchType.Size,
            "artists", "6");

      List<TrackEntity> result = trackRepo.findAll(artistsSizeIs2);
      out.println("result: " + result.size());
      result.forEach(out::println);
   }

   @Test
   public void testForNull() {
      JpaTrackSearchSpec releaseYearIsNull = new JpaTrackSearchSpec(SearchType.Null,
            "releaseYear", "");

      List<TrackEntity> result = trackRepo.findAll(releaseYearIsNull);
//      result.forEach(out::println);
      out.println("result: " + result.size());
   }
}
