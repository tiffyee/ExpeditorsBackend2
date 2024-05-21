package ttl.mie.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttl.mie.dao.TrackDAO;
import ttl.mie.domain.track.dto.TrackDTO;

@Service
public class ArtistService {
   @Autowired
   private TrackDAO trackDAO;

   public List<TrackDTO> getTracksForArtistByName(String name) {
      List<TrackDTO> allTracks = trackDAO.findAll();
      List<TrackDTO> result = allTracks.stream()
            .filter(t -> t.artists().stream().anyMatch(a -> a.name().equalsIgnoreCase(name)))
            .toList();

      return result;
   }
}
