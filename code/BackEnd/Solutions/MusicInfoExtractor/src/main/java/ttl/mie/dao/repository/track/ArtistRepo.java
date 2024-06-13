package ttl.mie.dao.repository.track;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.track.dto.ArtistWithAlbum;
import ttl.mie.domain.track.entity.ArtistEntity;

@Repository
public interface ArtistRepo extends JpaRepository<ArtistEntity, Integer> {

   Optional<ArtistEntity> findByDiscogsId(String discogsId);

   @Query(nativeQuery = true, value = "select a.artist_id, a.discogs_id, a.name, t.album " +
         "from track t join track_artist ta on t.track_id = ta.track_id join artist a on ta.artist_id = a.artist_id " +
         "where t.album like :album")
   List<ArtistWithAlbum> findArtistsWithAlbum(String album);
}
