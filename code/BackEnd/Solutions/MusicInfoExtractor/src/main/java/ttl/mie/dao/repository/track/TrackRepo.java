package ttl.mie.dao.repository.track;

import java.time.Duration;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.track.entity.TrackEntity;

@Repository
public interface TrackRepo extends JpaRepository<TrackEntity, Integer> {

   //@Query("select t.id, t.title, t.format, t.releaseYear, t.duration, a.artistId, a.discogsId, a.name, a.realName from TrackEntity t left join t.artists a")
   @Query("select t, a from TrackEntity t left join fetch t.artists a")
   List<TrackEntity> findTracksWithArtists();

   List<TrackEntity> findByDurationLessThanEqual(Duration duration);
}
