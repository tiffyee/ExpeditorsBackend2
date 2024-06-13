package ttl.mie.dao.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.app.JsonFileToDB;
import ttl.mie.dao.repository.track.ArtistRepo;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.db.DBUtils;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.extractor.AudioInfoExtractor;
import ttl.mie.extractor.DiscogRestHandler;

@SpringBootTest
@Disabled
public class TestAddDataFromJsonFile {

   @Autowired
   private AudioInfoExtractor atst;

   @Autowired
   private DiscogRestHandler discogRestHandler;

   @Autowired
   private TrackRepo trackRepo;

   @Autowired
   private ArtistRepo artistRepo;

   @Autowired
   private JsonFileToDB jftdb;

   @Autowired
   private DBUtils dbUtils;

   @Autowired
   private ObjectMapper mapper;

   @Test
   @Transactional
   @Rollback(false)
   public void testExtractAndInitDBFromJsonFile() throws Exception {
      File root = new File("/sidecar/Music/Music");
      int limit = 100;
      List<TrackDTO> result = atst.searchTrackStream(root, limit, (af) -> {
         Tag tag = af.getTag();
         return Strings.isNotBlank(tag.getFirst(FieldKey.TITLE));
      });

      discogRestHandler.getArtistInfoFromDiscogs(result);

      File trackFile = new File("/tmp/tracks.json");
      mapper.writerWithDefaultPrettyPrinter().writeValue(trackFile, result);

      List<TrackDTO> tracks = jftdb.readTracksFromJsonFile(trackFile);

      dbUtils.addTracksToDB(tracks);

      System.out.println("Added Tracks: " + tracks.size());
   }

   @Test
   @Transactional
   @Rollback(false)
   public void testInitDBFromJsonFile() throws Exception {

      File trackFile = new File("/tmp/tracks.json");

      List<TrackDTO> tracks = jftdb.readTracksFromJsonFile(trackFile);

      dbUtils.addTracksToDB(tracks);

      System.out.println("Added Tracks: " + tracks.size());
   }
}
