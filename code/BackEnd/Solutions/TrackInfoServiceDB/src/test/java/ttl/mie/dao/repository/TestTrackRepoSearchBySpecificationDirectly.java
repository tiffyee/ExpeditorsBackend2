package ttl.mie.dao.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestTrackRepoSearchBySpecificationDirectly {

   @PersistenceContext
   private EntityManager em;

   @Autowired
   private TrackRepo trackRepo;

   @Test
   public void testByAlbumWithSearchSpecification() {
      Specification<TrackEntity> byAlbum = (root, query, builder) -> {
         if (Long.class != query.getResultType() && long.class != query.getResultType()) {
            root.fetch("artists", JoinType.LEFT);
         }
         return builder.equal(root.get("album"), "Alone Together");
      };

      List<TrackEntity> result = trackRepo.findAll(byAlbum);
      out.println("result: " + result);
   }

   @Test
   public void testByTitleithSearchSpecification() {
      Specification<TrackEntity> byTitle = (root, query, builder) -> {
         if (Long.class != query.getResultType() && long.class != query.getResultType()) {
            root.fetch("artists", JoinType.LEFT);
         }
         return builder.like(root.get("title"), "%My Funny Valentine%");
      };

      List<TrackEntity> result = trackRepo.findAll(byTitle);
      out.println("result: " + result);
   }

   @Test
   public void testByArtistsNameithSearchSpecification() {
      Specification<TrackEntity> byTitle = (root, query, builder) -> {
         if (Long.class != query.getResultType() && long.class != query.getResultType()) {
            root.fetch("artists", JoinType.LEFT);
         }
         return builder.like(root.get("artists").get("name"), "%Herb Ellis%");
      };

      List<TrackEntity> result = trackRepo.findAll(byTitle);
      out.println("result: " + result);
   }

   @Test
   public void testSearchWithMultipleSearchSpecifications() {
      Specification<TrackEntity> byAlbum = (root, query, builder) -> {
         if (Long.class != query.getResultType() && long.class != query.getResultType()) {
            root.fetch("artists", JoinType.LEFT);
         }
         return builder.equal(root.get("album"), "Alone Together");
      };

      Specification<TrackEntity> byTitle = (root, query, builder) -> {
         if (Long.class != query.getResultType() && long.class != query.getResultType()) {
            root.fetch("artists", JoinType.LEFT);
         }
         return builder.like(root.get("title"), "%My Funny Valentine%");
      };

//      List<TrackEntity> result = trackRepo.findAll(Specification.where(albumSpec).or(titleSpec));
      List<TrackEntity> result = trackRepo.findAll(byAlbum.or(byTitle));
      out.println("result: " + result.size());
      result.forEach(out::println);

      assertEquals(2, result.size());
   }
//
//   @Test
//   public void testSearchForCollectionAttributeForArtists() {
//      JpaTrackSearchSpec artistSpec = new JpaTrackSearchSpec(SearchType.Custom,
//            "artist", "Herb Ellis",
//            (builder, root, propName, value) -> {
//               return builder.like(builder.lower(root.get("artists").get("name")), "%"
//                     + value.toString().toLowerCase() + "%");
//
//            });
//
//      List<TrackEntity> result = trackRepo.findAll(Specification.where(artistSpec));
//      out.println("result: " + result.size());
//      result.forEach(out::println);
//
//      assertEquals(2, result.size());
//   }
//
//   @Test
//   public void testSearchWithNestPropertiesForArtists() {
//      JpaTrackSearchSpec artistSpec = new JpaTrackSearchSpec(SearchType.Equal,
//            "artists.name", "Herb Ellis");
//
//      List<TrackEntity> result = trackRepo.findAll(Specification.where(artistSpec));
//      out.println("result: " + result.size());
//      result.forEach(out::println);
//
//      assertEquals(2, result.size());
//   }
}
