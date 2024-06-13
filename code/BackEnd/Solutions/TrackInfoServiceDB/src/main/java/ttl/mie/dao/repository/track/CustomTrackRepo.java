package ttl.mie.dao.repository.track;

import java.util.List;
import ttl.mie.domain.track.dto.TrackWithArtistsNames;
import ttl.mie.domain.track.entity.TrackEntity;

/**
 * @author whynot
 */
public interface CustomTrackRepo {
    List<TrackEntity> getByExampleWithCriteria(TrackEntity example);

    List<TrackWithArtistsNames> findTracksWithArtistNames();
}
