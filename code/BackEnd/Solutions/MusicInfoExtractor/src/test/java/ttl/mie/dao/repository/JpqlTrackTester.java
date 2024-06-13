package ttl.mie.dao.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.time.Duration;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.track.dto.ShortResult;
import ttl.mie.domain.track.entity.TrackEntity;

@SpringBootTest
public class JpqlTrackTester {

   @PersistenceContext
   private EntityManager em;

   @Test
   public void testGetAllTracksWithArtist() {
      String jpql = "select t from TrackEntity t join fetch t.artists a where a.name like :name";
      TypedQuery<TrackEntity> query = em.createQuery(jpql, TrackEntity.class);
      query.setParameter("name", "%Ellis%");

      List<TrackEntity> result = query.getResultList();

      System.out.println("result: " + result.size());
      result.forEach(System.out::println);

   }

   @Test
   public void testGetAllTracksWithShortResult() {
      String jpql = "select new ttl.mie.domain.track.dto.ShortResult(t.title, t.album, t.duration, " +
            "t.genre, t.format, t.releaseYear, a.name, a.realName) from TrackEntity t join t.artists a where a.name like :name " +
            "and t.duration > :duration";
      TypedQuery<ShortResult> query = em.createQuery(jpql, ShortResult.class);
      query.setParameter("name", "%Ellis%");
      query.setParameter("duration", Duration.ofMinutes(5));

      List<ShortResult> result = query.getResultList();

      System.out.println("result: " + result.size());
      result.forEach(System.out::println);
   }

}
