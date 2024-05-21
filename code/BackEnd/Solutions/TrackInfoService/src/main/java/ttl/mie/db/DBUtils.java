package ttl.mie.db;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.dao.TrackDAO;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.extractor.AudioInfoExtractor;
import ttl.mie.extractor.DiscogRestHandler;

//@Component
@Transactional
public class DBUtils {
   @Autowired
   private DiscogRestHandler discogRestHandler;

   @Autowired
   private TrackDAO audioInfoDao;

   @Autowired
   private AudioInfoExtractor atst;

   public void addTracksToDB(List<TrackDTO> tracks) throws Exception {
      tracks.forEach(audioInfoDao::insert);
   }
}
