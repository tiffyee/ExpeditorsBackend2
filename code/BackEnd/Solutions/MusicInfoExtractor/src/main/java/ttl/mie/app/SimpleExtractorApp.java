package ttl.mie.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.util.Strings;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.extractor.AudioInfoExtractor;
import ttl.mie.extractor.DiscogRestHandler;

/**
 * Application to extract metadata from audio files.
 * <p>
 * It first uses the AudioInfoExtractor to do read the metadata,
 * and then uses the DiscogRestHandler to embellish the artist
 * information with data from Discogs.
 * <p>
 * The result is then put into an output json file.
 */
public class SimpleExtractorApp {
   //   private static String discoggsSearchUrl = "https://api.discogs.com/database/search";
//   private static String discoggsArtisUrl = "https://api.discogs.com/artists/";
//   private static String discoggsMastersUrl = "https://api.discogs.com/masters/";
//   private static String authStr = "Discogs token=NvPVPQhSjNXovODgtIWHvCozAYJPsuDAqfcUtesf";
//   private static RestClient restClient = RestClient.create();

   File input = new File("/sidecar/Music/Music");

   //File output = new File("/tmp/testTracks.json");
   File output = new File("/tmp/lotsOfTracks.json");
   AudioInfoExtractor atst = new AudioInfoExtractor();
   int numTracksToExtract = 100;

   public SimpleExtractorApp() throws FileNotFoundException {
   }

   public void main(String[] args) throws IOException {
      ObjectMapper mapper = new ObjectMapper();
      mapper.findAndRegisterModules();

      var result = getTrackList();

      String authStr = System.getenv("DT");

      DiscogRestHandler drh = new DiscogRestHandler(authStr);
      drh.getArtistInfoFromDiscogs(result);

      long artistCount = result.stream()
            .flatMap(t -> t.artists().stream())
            .distinct()
            .count();

      System.out.println("numTracks: " + result.size() + ", numArtists: " + artistCount);

      mapper.writerWithDefaultPrettyPrinter().writeValue(output, result);

   }

   public List<TrackDTO> getAudioTracks(File root, AudioInfoExtractor atst) throws IOException {
      return getAudioTracks(root, Integer.MAX_VALUE, atst);
   }

   public List<TrackDTO> getAudioTracks(File root, int limit, AudioInfoExtractor atst) throws IOException {

      long start = System.currentTimeMillis();

      List<TrackDTO> r1 = atst.searchTrackStream(root, limit, (af) -> {
         Tag tag = af.getTag();
         int length = af.getAudioHeader().getTrackLength();
         var vetoSet = Set.of("Ike Turner");
         var artist = tag.getFirst(FieldKey.ARTIST);
         var artists = tag.getFirst(FieldKey.ARTISTS);
         var result = Strings.isNotBlank(tag.getFirst(FieldKey.TITLE))
               && !vetoSet.contains(artist)
               && length > 6;
         return result;
      });


      long end = System.currentTimeMillis();

      System.out.println(STR."Single Threaded Search took \{end - start} ms");
      System.out.println(STR."Total Matches: \{r1.size()}, Matching tracks: ");

      return r1;
   }

   private List<TrackDTO> getTrackList() throws IOException {

      List<TrackDTO> extractedTracks = getAudioTracks(input, numTracksToExtract, atst);

//      var extractedTracks = new ArrayList<>( List.of(
//            TrackDTO.builder().title("The Shadow Of Your Smile").album("Let them Roll")
//                  .length(Duration.ofSeconds(375)).format(Format.CD).year("1965")
//                  .artist("Big John Patton").group("Big John Patton").build(),
//            TrackDTO.builder().title("I'll Remember April").album("Alone Together")
//                  .length(Duration.ofSeconds(354)).format(Format.OGG).year("1972")
//                  .artist("Jim Hall And Ron Carter").group("Jim Hall And Ron Carter").build(),
//            TrackDTO.builder().title("Leave It to Me").album("Three Guitars in Bossa Nova Time")
//                  .length(Duration.ofSeconds(1933548749)).format(Format.CD).year("1963")
//                  .artist("Herb Ellis").group("Herb Ellis").build(),
//            TrackDTO.builder().title("Have you met Miss Jones").album("Pioneers of the Electric Guitar")
//                  .length(Duration.ofSeconds(138)).format(Format.MP3).year("2013")
//                  .artist("George Van Eps").group("George Van Eps").build(),
//            TrackDTO.builder().title("My Funny Valentine").album("My Funny Valentine")
//                  .length(Duration.ofSeconds(168)).format(Format.OGG).year("1962")
//                  .artist("Frank Sinatra").group("Frank Sinatra").build(),
//            TrackDTO.builder().title("I Want To Talk About You").album("Soultrain")
//                  .length(Duration.ofSeconds(247)).format(Format.MP3).year("1958")
//                  .artist("John Coltrane").group("John Coltrane with Red Garland").build()
//      ));

      return extractedTracks;
   }
}