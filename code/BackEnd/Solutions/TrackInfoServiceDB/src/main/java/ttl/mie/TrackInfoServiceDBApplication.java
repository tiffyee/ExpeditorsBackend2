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
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.db.DBUtils;
import ttl.mie.domain.track.Format;
import ttl.mie.domain.track.dto.TrackDTO;

@SpringBootApplication
@PropertySource("classpath:/trackinfoservice.properties")
public class TrackInfoServiceDBApplication {

   public static void main(String[] args) {
      SpringApplication.run(TrackInfoServiceDBApplication.class, args);
   }
}

@Component
@Profile("filedata")
class DBFromJsonFileInitializer implements CommandLineRunner {

   @Autowired
   private JsonFileToDB jsonToDB;

   @Override
   public void run(String... args) throws Exception {
//      File trackFile = new File("/tmp/tracks.json");
//      File trackFile = new File("/tmp/testTracks.json");
      File trackFile = new File("/tmp/lotsOfTracks.json");


      int count = jsonToDB.addTracksToDB(trackFile);
//      int count = jsonToDB.addTracksToDB("tracks.json");

      System.out.println("Added " + count + " tracks");
   }
}

@Component
@Profile("testdata")
class TestDataInitializer implements CommandLineRunner {

   @Autowired
   private TrackDAO trackDAO;

   @Autowired
   private TrackRepo trackRepo;

   @Autowired
   private DBUtils dbUtils;

   @Override
   public void run(String... args) throws Exception {
      var tracks = List.of(
            TrackDTO.builder().title("Test-The Shadow Of Your Smile").album("Let them Roll")
                  .length(Duration.ofSeconds(375)).format(Format.CD).year("1965")
                  .artist("Big John Patton").group("Big John Patton").build(),

            TrackDTO.builder().title("Test-I'll Remember April").album("Alone Together")
                  .length(Duration.ofSeconds(354)).format(Format.OGG).year("1972")
                  .artist("Jim Hall And Ron Carter").group("Jim Hall And Ron Carter").build(),
            TrackDTO.builder().title("Test-Leave It to Me").album("Three Guitars in Bossa Nova Time")
                  .length(Duration.ofSeconds(1933548749)).format(Format.CD).year("1963")
                  .artist("Herb Ellis").group("Herb Ellis").build(),
            TrackDTO.builder().title("Test-Have you met Miss Jones").album("Pioneers of the Electric Guitar")
                  .length(Duration.ofSeconds(138)).format(Format.MP3).year("2013")
                  .artist("George Van Eps").group("George Van Eps").build(),
            TrackDTO.builder().title("Test-My Funny Valentine").album("Moonlight in Vermont")
                  .length(Duration.ofSeconds(168)).format(Format.OGG).year("1956")
                  .artist("Johhny Smity").group("Johhny Smity").build(),
            TrackDTO.builder().title("Test-What's New").album("Ballads")
                  .length(Duration.ofSeconds(247)).format(Format.MP3).year("1945")
                  .album("Soultrane")
                  .artist("John Coltrane").artist("Herb Ellis").group("John Coltrane").build()
      );

      tracks.forEach(trackDAO::insert);

      System.out.println("TestInitializer add tracks: " + tracks.size());

      dbUtils.convertToEntityAndAdd(tracks);
   }
}
