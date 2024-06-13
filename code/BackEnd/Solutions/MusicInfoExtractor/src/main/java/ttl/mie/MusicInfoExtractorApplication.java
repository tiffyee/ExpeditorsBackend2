package ttl.mie;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ttl.mie.app.JsonFileToDB;

@SpringBootApplication
@PropertySource("classpath:/trackinfoservice.properties")
public class MusicInfoExtractorApplication {

   public static void main(String[] args) {
      new SpringApplicationBuilder(MusicInfoExtractorApplication.class)
            .run(args);
   }
}

@Component
class DBFromJsonFileInitializer implements CommandLineRunner {

   @Autowired
   private JsonFileToDB jsonToDB;

   @Override
   public void run(String... args) throws Exception {
//      File trackFile = new File("/tmp/tracks.json");
      //File trackFile = new File("/tmp/testTracks.json");
      File trackFile = new File("/tmp/lotsOfTracks.json");

//      int count = jsonToDB.addTracksToDB("tracks.json");

      int count = jsonToDB.addTracksToDB(trackFile);
      System.out.println("Added " + count + " tracks");
   }
}
