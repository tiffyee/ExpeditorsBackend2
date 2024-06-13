package ttl.mie.extractor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.SearchResults;
import ttl.mie.domain.track.dto.TrackDTO;

/**
 * The class extracts artist information from Discogs.
 * <p>
 * It needs a Discogs authorization token to be set in the
 * environment variable 'DT'.
 * <p>
 * Discogs API documentation:
 * https://www.discogs.com/developers
 * https://www.discogs.com/developers#page:database,header:database-release
 */
@Component
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
   //private Map<String, List<ArtistDTO>> alreadySeenArtists = new HashMap<>();
   private Map<String, SearchResults> alreadySeenArtists = new HashMap<>();

   public void getArtistInfoFromDiscogs(List<TrackDTO> tracks) {

      alreadySeenArtists.clear();

      TrackDTO[] currTrack = new TrackDTO[1];

      limit = Math.min(limit, tracks.size());
      for (int currTrackIndex = 0; currTrackIndex < limit; currTrackIndex++) {
         currTrack[0] = tracks.get(currTrackIndex);

         int artistsForCurrTrack = 0;

         //First check if we have the artist cached
         if (!currTrack[0].group().isBlank()) {

            //Key is artist_name '#' album_name
            var key = currTrack[0].group() + '#' + currTrack[0].album();
            System.out.println("New Key: " + key);
            //See if we have this artist album combination in the cache.
            if (alreadySeenArtists.containsKey(key)) {
               var searchResults = alreadySeenArtists.get(key);
               var adtos = searchResults.artists();
               if(!searchResults.otherProps().isEmpty()) {
                  currTrack[0] = maybeReplaceTrack(tracks, currTrackIndex, currTrack[0], searchResults.otherProps());
               }
               currTrack[0].artists().clear();
               currTrack[0].artists().addAll(adtos);

               tracks.set(currTrackIndex, currTrack[0]);

               //System.out.println(STR."Already seen for: \{key}: \{adtos}");
               System.out.println(STR."Already seen for: \{key}:");
               continue;
            }

            SearchResults searchResults = findArtistsForKey(key);
            List<ArtistDTO> adtos = searchResults.artists();
            if (!adtos.isEmpty()) {
               addArtistsToTrack(adtos, currTrack[0]);
               //Cache this result for future tracks with the same key

               alreadySeenArtists.computeIfAbsent(key, k -> searchResults);
            } else {
               alreadySeenArtists.computeIfAbsent(key, k -> new SearchResults(currTrack[0].artists()));
            }

            //Add other info to track
            //Note that we would have to create a new Record for
            //any non collection extra properties we are going to
            //add to the track.  And then make that new Record the
            //current element in the List.  Strange, but officially okay to
            // change the current element while iterating, since we are
            // NOT adding or deleting elements.
            currTrack[0] = maybeReplaceTrack(tracks, currTrackIndex, currTrack[0], searchResults.otherProps());
            tracks.set(currTrackIndex, currTrack[0]);
//            String url = (String)searchResults.otherProps().get("imageUrl");
//            if(url != null) {
//               var newTrack = currTrack.copyWithImageUrl(url);
//               tracks.set(currTrackIndex, newTrack);
//            }


            if (lastResponse.getStatusCode() != HttpStatus.OK) {
               System.err.println("Status NOT OK: " + lastResponse.getStatusCode());
            }
         }
         System.out.println(STR."processed track \{currTrackIndex}, artistsForTrack: \{artistsForCurrTrack}");
      }
   }

   private TrackDTO maybeReplaceTrack(List<TrackDTO> tracks,
                                  int currTrackIndex,
                                  TrackDTO currTrack,
                                  Map<String, Object> otherProps) {
      //Add other info to track
      //Note that we would have to create a new Record for
      //any non collection extra properties we are going to
      //add to the track.  And then make that new Record the
      //current element in the List.  Strange, but officially okay to
      // change the current element while iterating, since we are
      // NOT adding or deleting elements.
      String url = (String)otherProps.get("imageUrl");
      if(url != null) {
         currTrack = currTrack.copyWithImageUrl(url);
//         tracks.set(currTrackIndex, newTrack);
      }
      return currTrack;
   }

   public SearchResults findArtistsForKey(String key) {
      //TODO - This is very brittle.  Should have a better way to
      //'deconstruct' the key.
      String [] deconstructedKey = key.split("#");

      var searchUri = discogsSearchUrl + "?q=" + deconstructedKey[0] + "&release_title=" +
            (deconstructedKey.length > 1 ? deconstructedKey[1] : "");
      System.out.println("Calling: " + searchUri);
      var response = makeCall(searchUri, String.class);

      Map<String, Object> extraProps = new HashMap<>();
      List<ArtistDTO> adtos = new ArrayList<>();
      if (response.getStatusCode() == HttpStatus.OK) {
         try {
            var jsonResult = response.getBody();
            JsonNode nameNode = mapper.readTree(jsonResult);
            var arr = (ArrayNode)nameNode.get("results");
            if (!arr.isEmpty()) {
               //First look for a "master_id"
               var master_id = findMasterId(arr);
//               var master_id = arr.get(0).get("master_id").asText();
               if(master_id != null) {
                  var masterUri = discogsMastersUrl + master_id;
//               if (master_id == null || master_id.equals("0") || master_id.equals("null")) {
//                  //If no master_id, then look for an attribute called "id"
//                  var justId = nameNode.get("results").get(0).get("id").asText();
//
//                  var type = nameNode.get("results").get(0).get("type").asText();
//
//                  //One more wrinkle.  If the type is artist then we do
//                  // a "members" flow.  Look at the artistsToMembersFlow
//                  if (type.equals("artist")) {
//                     var tmp = artistsToMembersFlow(key, justId);
//                     adtos.addAll(tmp);
//
//                     if (!adtos.isEmpty()) {
//                        return new SearchResults(adtos);
//                     }
//                  }
//                  //If we are here, then the type is not artist, and we
//                  //are back to a "release" flow below.
//                  masterUri = discogsReleasesUrl + justId;
//               }
                  //If we are still here, then it was not type 'artist', so we
                  //do a lookup.  If we had a master_id, the lookup will
                  //be for masters, else it will be for releases
                  System.out.println("Calling For Master/Release: " + masterUri);
                  var releaseResponse = makeCall(masterUri, String.class);

                  if (releaseResponse.getStatusCode() == HttpStatus.OK) {
                     var releaseJson = releaseResponse.getBody();
                     var releaseNode = mapper.readTree(releaseJson);

                     var imagesNode = (ArrayNode) releaseNode.get("images");

                     String imageUrl = null;
                     if (!imagesNode.isEmpty()) {
                        imageUrl = imagesNode.get(0).get("uri").asText();
                        extraProps.put("imageUrl", imageUrl);
                        System.out.println("Found imageUrl in Master: " + imageUrl);
                     }

                     var genres = unwrapJacksonArrayNode((ArrayNode) releaseNode.get("genres"));
                     extraProps.put("genres", genres);
                     var styles = unwrapJacksonArrayNode((ArrayNode) releaseNode.get("styles"));
                     extraProps.put("styles", styles);
                     var year = releaseNode.get("year").asText();
                     extraProps.put("year", year);

                     var artistsArr = releaseNode.get("artists");
                     if (artistsArr.size() > 1) {
                        System.err.println(STR."More than one artist for: \{masterUri}, size: \{artistsArr.size()}");
                     }
                     for (int a = 0; a < artistsArr.size(); a++) {

                        var artistId = artistsArr.get(a).get("id").asText();
//                           var artistId = artistsArr.get(0).get("id").asText();
                        //Again, this could be a "members" flow, so we
                        // check and act on that if true.
                        adtos.addAll(artistsToMembersFlow(key, artistId));
                        if (!adtos.isEmpty()) {
//                        return new SearchResults(adtos, Map.of(
//                              "genres", genres,
//                              "styles", styles,
//                              "year", year,
//                              "imageUrl", imageUrl
//                        ));
                        } else {

                           //If we are *still* still here, then we grab whatever info we can
                           //from the artist id in the release.
                           var artistUri = discogsArtistUrl + artistId;
                           System.out.println("Calling Last Ditch For Artist: " + artistUri);
                           var artistResponse = makeCall(artistUri, ArtistDTO.class);

                           if (artistResponse.getStatusCode() == HttpStatus.OK) {
                              var artistDTO = artistResponse.getBody();
                              System.out.println("Artist response: " + artistDTO);
                              adtos.add(artistDTO);
//                           return new SearchResults(List.of(artistDTO));
                           }
                        }
                     }
                  }
               }
               else {
                  System.err.println("No master_id found for " + searchUri + ", key: " + key);
                  //If no master_id, then look for an attribute called "id"
                  var justId = nameNode.get("results").get(0).get("id").asText();

                  var type = nameNode.get("results").get(0).get("type").asText();

                  //One more wrinkle.  If the type is artist then we do
                  // a "members" flow.  Look at the artistsToMembersFlow
                  if (type.equals("artist")) {
                     var tmp = artistsToMembersFlow(key, justId);
                     adtos.addAll(tmp);

//                     if (!adtos.isEmpty()) {
//                        SearchResults(adtos);
//                     }
                  }else {
                     //If we are here, then the type is not artist, and we
                     //are back to a "release" flow below.
                     var masterUri = discogsReleasesUrl + justId;
                     goGetIt(masterUri, adtos, extraProps, key);
                  }
               }
            } else {
               System.err.println("Nothing found for searchUri: " + searchUri);
            }
         } catch (JsonProcessingException e) {
            e.printStackTrace();
         }
      }
      return new SearchResults(adtos, extraProps);
   }

   public void addArtistsToTrack(List<ArtistDTO> adtos, TrackDTO currTrack) {
      for (int i = 0; i < adtos.size(); i++) {
         if (i == 0) {
            //If it is the first one, then replace the one already
            //there, which will be the default one we had from our
            //media metadata.
            currTrack.artists().set(0, adtos.get(i));
         } else {
            currTrack.addArtist(adtos.get(i));
         }
      }
   }


   public List<ArtistDTO> artistsToMembersFlow(String key, String inputId) throws JsonProcessingException {

      List<ArtistDTO> result = new ArrayList<>();

      var artistsUri = discogsArtistUrl + inputId;
      System.out.println("Calling For Artists: " + artistsUri);
      var artistResponse = makeCall(artistsUri, String.class);

      int numArtists = 0;

      if (artistResponse.getStatusCode() == HttpStatus.OK) {
         var jsonResult = artistResponse.getBody();
         JsonNode nameNode = mapper.readTree(jsonResult);
         var arr = nameNode.get("members");
         if (arr != null && !arr.isEmpty()) {
            int count = arr.size();
            for (int i = 0; i < count; i++) {
               var id = arr.get(i).get("id");
               //This should be the id of an artist
               var innerArtistsUri = discogsArtistUrl + id;
               var realArtistReponse = makeCall(innerArtistsUri, ArtistDTO.class);
               if (realArtistReponse.getStatusCode() == HttpStatus.OK) {
                  //And here we finally get to our artist.
                  var artistDTO = realArtistReponse.getBody();
                  //Add the artist to our artists
                  result.add(artistDTO);

                  numArtists++;
                  System.out.println("Member Flow Artist: " + i + " of " + count + ", response: " + artistDTO);
               }
            }
         }
      }
      return result;
   }

   private void rateLimitSleep() {
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

   public String [] unwrapJacksonArrayNode(ArrayNode arrayNode) {
      if(arrayNode == null) return null;
      String [] result = new String[arrayNode.size()];
      for(int i = 0; i < arrayNode.size(); i++) {
         result[i] = arrayNode.get(i).asText();
      }
      return result;
   }

   public String findMasterId(ArrayNode arr) {
      String master_id = null;
      for(int i = 0; i < arr.size(); i++) {
         var tmp = arr.get(i).get("master_id").asText();
         if (tmp != null && !tmp.equals("0") && !tmp.equals("null")) {
            master_id = tmp;
            break;
         }
      }
      return master_id;
   }

   public void goGetIt(String masterUri, List<ArtistDTO> adtos,
                       Map<String, Object> extraProps,
                       String key) throws JsonProcessingException {

      System.out.println("Calling For Master/Release: " + masterUri);
      var releaseResponse = makeCall(masterUri, String.class);

      if (releaseResponse.getStatusCode() == HttpStatus.OK) {
         var releaseJson = releaseResponse.getBody();
         var releaseNode = mapper.readTree(releaseJson);

         var imagesNode = (ArrayNode) releaseNode.get("images");

         String imageUrl = null;
         if (!imagesNode.isEmpty()) {
            imageUrl = imagesNode.get(0).get("uri").asText();
            extraProps.put("imageUrl", imageUrl);
            System.out.println("Found imageUrl in Master: " + imageUrl);
         }

         maybeAddToExtraProps(releaseNode, "genres", true, extraProps);
         maybeAddToExtraProps(releaseNode, "styles", true, extraProps);
         maybeAddToExtraProps(releaseNode, "year", false, extraProps);

         var artistsArr = releaseNode.get("artists");
         if (artistsArr != null && artistsArr.size() > 1) {
            System.err.println(STR."More than one artist for: \{masterUri}, size: \{artistsArr.size()}");
         }
         for (int a = 0; a < artistsArr.size(); a++) {

            var artistId = artistsArr.get(a).get("id").asText();
//                           var artistId = artistsArr.get(0).get("id").asText();
            //Again, this could be a "members" flow, so we
            // check and act on that if true.
            adtos.addAll(artistsToMembersFlow(key, artistId));
            if (!adtos.isEmpty()) {
//                        return new SearchResults(adtos, Map.of(
//                              "genres", genres,
//                              "styles", styles,
//                              "year", year,
//                              "imageUrl", imageUrl
//                        ));
            } else {

               //If we are *still* still here, then we grab whatever info we can
               //from the artist id in the release.
               var artistUri = discogsArtistUrl + artistId;
               System.out.println("Calling Last Ditch For Artist: " + artistUri);
               var artistResponse = makeCall(artistUri, ArtistDTO.class);

               if (artistResponse.getStatusCode() == HttpStatus.OK) {
                  var artistDTO = artistResponse.getBody();
                  System.out.println("Artist response: " + artistDTO);
                  adtos.add(artistDTO);
//                           return new SearchResults(List.of(artistDTO));
               }
            }
         }
      }
   }

   public void maybeAddToExtraProps(JsonNode jsonNode,
                                    String prop,
                                    boolean toCollection,
                                    Map<String, Object> extraProps) {
      if(toCollection) {
         var arr = unwrapJacksonArrayNode((ArrayNode) jsonNode.get("genres"));
         if(arr != null) {
            extraProps.put(prop, arr);
         }
      } else {
         var value = jsonNode.get(prop).asText();
         extraProps.put("year", value);
      }
   }
}