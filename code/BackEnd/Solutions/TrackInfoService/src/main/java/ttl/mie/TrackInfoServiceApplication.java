package ttl.mie;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ttl.mie.app.JsonFileToDB;
import ttl.mie.dao.TrackDAO;
import ttl.mie.domain.track.Format;
import ttl.mie.domain.track.dto.TrackDTO;

@SpringBootApplication
@PropertySource("classpath:/trackinfoservice.properties")
public class TrackInfoServiceApplication {

   public static void main(String[] args) {
      SpringApplication.run(TrackInfoServiceApplication.class, args);
   }
}

@Component
@Profile("filedata")
class DBFromJsonFileInitializer implements CommandLineRunner {

   @Autowired
   private JsonFileToDB jsonToDB;

   @Override
   public void run(String... args) throws Exception {
      File trackFile = new File("tracks.json");

      jsonToDB.readTracksFromJsonFile(trackFile);
   }
}

@Component
@Profile("testdata")
class TestDataInitializer implements CommandLineRunner {

   @Autowired
   private TrackDAO trackDAO;

   @Override
   public void run(String... args) throws Exception {
      var tracks = List.of(
            TrackDTO.builder().title("Test-The Shadow Of Your Smile").album("Let them Roll")
                  .length(Duration.ofSeconds(375)).format(Format.CD).year("1965")
                  .artist("Big John Patton").build(),

            TrackDTO.builder().title("Test-I'll Remember April").album("Alone Together")
                  .length(Duration.ofSeconds(354)).format(Format.FLAC).year("1972")
                  .artist("Jim Hall And Ron Carter").build(),
            TrackDTO.builder().title("Test-Leave It to Me").album("Three Guitars in Bossa Nova Time")
                  .length(Duration.ofSeconds(193)).format(Format.CD).year("1963")
                  .artist("Herb Ellis").build(),
            TrackDTO.builder().title("Test-Have you met Miss Jones").album("Pioneers of the Electric Guitar")
                  .length(Duration.ofSeconds(138)).format(Format.MP3).year("2013")
                  .artist("George Van Eps").build(),
            TrackDTO.builder().title("Test-My Funny Valentine").album("Moonlight in Vermont")
                  .length(Duration.ofSeconds(500)).format(Format.OGG).year("1956")
                  .artist("Johhny Smity").build(),
            TrackDTO.builder().title("Test-What's New").album("Ballads")
                  .length(Duration.ofSeconds(247)).format(Format.MP3).year("1945")
                  .artist("John Coltrane").artist("Herb Ellis").build()
      );

      tracks.forEach(trackDAO::insert);

      System.out.println("TestInitializer add tracks: " + tracks.size());
   }
}
