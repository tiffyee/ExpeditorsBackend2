package ttl.mie.extractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;

//@Component
public class DiscogRestHandler {

   private final String discogsSearchUrl = "https://api.discogs.com/database/search";
   private final String discogsArtistUrl = "https://api.discogs.com/artists/";
   private final String discogsMastersUrl = "https://api.discogs.com/masters/";
   private final String discogsReleasesUrl = "https://api.discogs.com/releases/";


   private ObjectMapper mapper = new ObjectMapper();
   private RestClient restClient;
   private ResponseEntity<?> lastResponse = null;

   private long discogRateLimitDelay = 1000;   //One per second max

   private final String authStr;

   public DiscogRestHandler(@Value("${DT}") String authStr) {
      this.authStr = authStr;
      restClient = RestClient.builder()
            .defaultHeader("Authorization", authStr)
            .defaultHeader("User-Agent", "TheThirdLane/1.0 +https://thethirdlane.com")
            .build();
   }

   private int numCalls = 0;
   private int limit = 500;
   private Map<String, List<ArtistDTO>> alreadySeenArtists = new HashMap<>();

   public void getArtistInfoFromDiscogs(List<TrackDTO> tracks) {
      alreadySeenArtists.clear();

      limit = Math.min(limit, tracks.size());
      for (int i = 0; i < limit; i++) {
         var currTrack = tracks.get(i);

         //First check if we have the artist cached
         if (!currTrack.artists().isEmpty() &&
               !currTrack.artists().getFirst().name().isBlank()) {
            //Key is artist name '#' album name
            var key = currTrack.artists().getFirst().name() + '#' + currTrack.album();

            if (alreadySeenArtists.containsKey(key)) {
               var adtos = alreadySeenArtists.get(key);
               currTrack.artists().clear();
               currTrack.artists().addAll(adtos);
               System.err.println(STR."Already seen for: \{key}: \{adtos}");
               continue;
            }

            var uri = discogsSearchUrl + "?q=" + currTrack.artists().getFirst().name() +
                  "&release_title=" + currTrack.album();
            System.out.println("Calling: " + uri);
            var response = makeCall(uri, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
               try {
                  var jsonResult = response.getBody();
                  JsonNode nameNode = mapper.readTree(jsonResult);
                  var arr = nameNode.get("results");
                  if (arr.size() > 0) {
                     //First look for a "master_id"
                     var id = nameNode.get("results").get(0).get("master_id").asText();
                     uri = discogsMastersUrl + id;
                     //TODO - need to also put in a check here for type.
                     // If it is 'release' then we have to do this release
                     // flow.  It is it 'artist', then the artistId points to an
                     // artist and we want to do the artist flow instead
                     if (id == null || id.equals("0") || id.equals("null")) {
                        //If no master_id, then look for an attribute called "trackId"
                        id = nameNode.get("results").get(0).get("id").asText();

                        //One more wrinkle.  If the type is artist then we do
                        // a "members" flow.  Look at the artistsToMembersFlow
                        var type = nameNode.get("results").get(0).get("type").asText();
                        if (type.equals("artist")) {
                           if (artistsToMembersFlow(key, id, currTrack)) {
                              continue;
                           }
                        }
                        //If we are here, then the type is not artist and we
                        //are back to a "release" flow.
                        uri = discogsReleasesUrl + id;
                     }
                     //If we are still here, then it was not type 'artist', so we
                     //do a lookup at the "release" uri;
                     System.out.println("Calling For Release: " + uri);
                     var releaseResponse = makeCall(uri, String.class);

                     if (releaseResponse.getStatusCode() == HttpStatus.OK) {
                        var releaseJson = releaseResponse.getBody();
                        var releaseNode = mapper.readTree(releaseJson);
                        var artistsArr = releaseNode.get("artists");
                        if (artistsArr.size() > 1) {
                           System.err.println(STR."More than in artist for: \{uri}");
                        }

                        var artistId = releaseNode.get("artists").get(0).get("id").asText();
                        //Again, this could be a "members" flow, so we
                        // check and act on that if true.
                        boolean done = artistsToMembersFlow(key, artistId, currTrack);

                        //If we are *still* still here, then we grab whatever info we can
                        //from the artist trackId in the release.
                        if (!done) {
                           uri = discogsArtistUrl + artistId;
                           System.out.println("Calling For Artist: " + uri);
                           var artistResponse = makeCall(uri, ArtistDTO.class);

                           if (artistResponse.getStatusCode() == HttpStatus.OK) {
                              var artistDTO = artistResponse.getBody();
                              //Add the artist to our artists
                              currTrack.artists().set(0, artistDTO);
                              //alreadySeenArtists.computeIfAbsent(key, (k) -> new ArrayList<>()).add(artistDTO);
                              alreadySeenArtists.put(key, List.of(artistDTO));
                              System.out.println("Artist response: " + artistDTO);
                           }
                        }
                     }
                  } else {
                     System.err.println("Nothing found for: " + uri);
                     alreadySeenArtists.put(key, List.of(currTrack.artists().getFirst()));
//                     alreadySeenArtists.computeIfAbsent(key, (k) -> new ArrayList<>()).add(currTrack.artists().getFirst());
                  }
               } catch (JsonProcessingException e) {
                  e.printStackTrace();
               }
            }
            if (lastResponse.getStatusCode() != HttpStatus.OK) {
               System.err.println("Status NOT OK: " + lastResponse.getStatusCode());
            }
         }
      }
   }


   public boolean artistsToMembersFlow(String key, String inputId, TrackDTO t) throws JsonProcessingException {

      var uri = discogsArtistUrl + inputId;
      System.out.println("Calling For Artist: " + uri);
      var artistResponse = makeCall(uri, String.class);

      if (artistResponse.getStatusCode() == HttpStatus.OK) {
         var jsonResult = artistResponse.getBody();
         JsonNode nameNode = mapper.readTree(jsonResult);
         var arr = nameNode.get("members");
         if (arr != null && !arr.isEmpty()) {
            for (int i = 0; i < arr.size(); i++) {
               var id = arr.get(i).get("id");
               //This should be the trackId of an artist
               uri = discogsArtistUrl + id;
               var realArtistReponse = makeCall(uri, ArtistDTO.class);
               var artistDTO = realArtistReponse.getBody();
               //Add the artist to our artists
               if (i == 0) {
                  t.artists().set(0, artistDTO);
               } else {
                  t.addArtist(artistDTO);
               }
               System.out.println("Member Flow Artists response: " + artistDTO);
            }
            alreadySeenArtists.put(key, t.artists());
            return true;
         }
      }
      return false;
   }

   public void rateLimitSleep() {
      try {
         System.out.println(STR."numCalls: \{numCalls}, Sleeping for \{discogRateLimitDelay * numCalls} ms");
         Thread.sleep(discogRateLimitDelay * numCalls);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      numCalls = 0;
   }

   public <T> ResponseEntity<T> makeCall(String uri, Class<T> clazz) {
      var response = restClient
            .get()
            .uri(uri)
            .header("Authorization", authStr)
            .header("User-Agent", "TheThirdLane/1.0 +https://thethirdlane.com")
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, resp) -> {
               //Do Nothing because we handle it in the code
            })
            .toEntity(clazz);
      lastResponse = response;
      numCalls++;

      rateLimitSleep();

      return response;
   }
}
