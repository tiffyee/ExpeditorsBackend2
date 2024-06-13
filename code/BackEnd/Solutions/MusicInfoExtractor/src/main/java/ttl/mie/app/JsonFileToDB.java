package ttl.mie.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ttl.mie.db.DBUtils;
import ttl.mie.domain.track.dto.TrackDTO;

@Component
@PropertySource("classpath:/application.properties")
@PropertySource("classpath:/application-postgres.properties")
@PropertySource("classpath:/trackinfoservice.properties")
public class JsonFileToDB {

   private final ObjectMapper objectMapper;
   private DBUtils dbUtils;

   public JsonFileToDB(DBUtils dbUtils, ObjectMapper objectMapper) {
      this.dbUtils = dbUtils;
      this.objectMapper = objectMapper;
   }


   public int addTracksToDB(File trackFile) throws Exception {
      try (InputStream inputStream = new FileInputStream(trackFile)) {
         List<TrackDTO> tracks = readTracksFromJsonFile(inputStream);
//         List<TrackDTO> tracks = readTracksFromJsonFile(trackFile);

         int addedTracks = dbUtils.addTracksToDB(tracks);

         //return tracks.size();
         return addedTracks;
      }
   }

   public int addTracksToDB(String classPathResource) {
      try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(classPathResource);) {
         List<TrackDTO> tracks = readTracksFromJsonFile(inputStream);
//         List<TrackDTO> tracks = readTracksFromJsonFile(trackFile);

         int addedTracks = dbUtils.addTracksToDB(tracks);

         //return tracks.size();
         return addedTracks;
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   public List<TrackDTO> readTracksFromJsonFile(InputStream inputStream) {
      try {
         TypeReference<List<TrackDTO>> tr = new TypeReference<>() {
         };
         var result = objectMapper.readValue(inputStream, tr);
         return result;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public List<TrackDTO> readTracksFromJsonFile(File file) {
      try {
         TypeReference<List<TrackDTO>> tr = new TypeReference<>() {
         };
         var result = objectMapper.readValue(file, tr);
         return result;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public static void main() throws Exception {
      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
      context.getEnvironment().setActiveProfiles("postgres");
      context.scan("ttl.mie");
      context.refresh();

      JsonFileToDB jftb = context.getBean("jsonFileToDB", JsonFileToDB.class);
      //File trackFile = new File("/tmp/tracks.json");
      File trackFile = new File("/tmp/testTracks.json");
      int numTracks = jftb.addTracksToDB(trackFile);

      System.out.println("numTracks added: " + numTracks);
   }
}
