package ttl.mie.dao.repository.track;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.track.entity.TrackEntity;

@Repository
public interface TrackRepo extends JpaRepository<TrackEntity, Integer>,
      CustomTrackRepo, JpaSpecificationExecutor<TrackEntity> {

   @Query("select distinct t from TrackEntity t left join fetch t.artists a where t.trackId = :id")
   Optional<TrackEntity> findTrackWithArtistsById(int id);

   //@Query("select t.id, t.title, t.format, t.releaseYear, t.duration, a.artistId, a.discogsId, a.name, a.realName from TrackEntity t left join t.artists a")
   @Query("select distinct t from TrackEntity t left join fetch t.artists a order by t.trackId")
   List<TrackEntity> findTracksWithArtists();

   @Query("select distinct t from TrackEntity t left join fetch t.artists a order by t.trackId")
   @Override
   List<TrackEntity> findAll();

   @Query("select distinct t from TrackEntity t left join fetch t.artists a order by t.trackId")
   @Override
   Page<TrackEntity> findAll(Pageable page);

   List<TrackEntity> findByDurationLessThanEqual(Duration duration);
}
