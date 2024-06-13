package ttl.mie.dao.repository.track;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.track.entity.ArtistEntity;

@Repository
public interface ArtistRepo extends JpaRepository<ArtistEntity, Integer> {

   Optional<ArtistEntity> findByDiscogsId(String discogsId);
}
