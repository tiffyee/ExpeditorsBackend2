package ttl.mie.app;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.stereotype.Component;
import ttl.mie.db.DBUtils;
import ttl.mie.domain.track.dto.TrackDTO;

@Component
public class JsonFileToDB {

   private final ObjectMapper objectMapper;
   private DBUtils dbUtils;

   public JsonFileToDB(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }


   public List<TrackDTO> readTracksFromJsonFile(File file) {
      try (InputStream is = getInputStream(file)) {
         TypeReference<List<TrackDTO>> tr = new TypeReference<>() {
         };

         var result = objectMapper.readValue(is, tr);
         return result;
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
   }

   public InputStream getInputStream(File file) {
      InputStream is = this.getClass().getClassLoader().getResourceAsStream(file.getName());

      return is;
   }

//   public static void main() throws Exception {
//      AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//      context.getEnvironment().setActiveProfiles("postgres");
//      context.scan("ttl.mie");
//      context.refresh();
//
//      JsonFileToDB jftb = context.getBean("jsonFileToDB", JsonFileToDB.class);
//      File trackFile = new File("tracks.json");
//      jftb.addTracksToDB(trackFile);
//   }
}
