package ttl.mie.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.jconfig.client.RestClientFactory;

@Service
@Transactional
public class TrackRepoService {

   @Autowired
   private TrackRepo trackRepo;

   private RestClient restPriceClient;

   private String getPriceUrl;

   public TrackRepoService(@Value("${pricing.service.url}") String priceBaseUrl,
                           RestClientFactory restClientFactory) {
      this.getPriceUrl = priceBaseUrl + "/{trackId}";
      this.restPriceClient = restClientFactory.noAuth(getPriceUrl);
   }

   public TrackEntity addTrack(TrackEntity track) {
      return trackRepo.save(track);
   }

   public boolean deleteTrack(int id) {
      if (trackRepo.existsById(id)) {
         trackRepo.deleteById(id);
         return true;
      }
      return false;
   }

   public boolean updateTrack(TrackEntity track) {
      if (trackRepo.existsById(track.getTrackId())) {
         trackRepo.save(track);
         return true;
      }
      return false;
   }

   public Optional<TrackEntity> getById(int id) {
      var track = trackRepo.findById(id)
            .map(this::addPriceToTrack);

      return track;
   }

   public List<TrackEntity> getAllTracks() {
      List<TrackEntity> tracks = trackRepo.findAll();
      tracks.forEach(this::addPriceToTrack);

      return tracks;
   }

//   public List<TrackEntity> getTracksWithDurationLessThan(Duration duration) {
//
//   }

   private TrackEntity addPriceToTrack(TrackEntity track) {

      ResponseEntity<String> response = restPriceClient.get()
            .uri(getPriceUrl, track.getTrackId())
            .retrieve()
            .onStatus(code -> code == HttpStatus.NOT_FOUND, (req, resp) -> {
               //Do Nothing
            })
            .toEntity(String.class);
      if (response.getStatusCode() == HttpStatus.OK) {
         String price = response.getBody();
         if (price != null) {
            track.setPrice(price);
         }
      }
      return track;
   }

}
