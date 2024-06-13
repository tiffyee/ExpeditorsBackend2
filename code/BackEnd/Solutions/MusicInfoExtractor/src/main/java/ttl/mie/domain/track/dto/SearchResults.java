package ttl.mie.domain.track.dto;

import java.util.List;
import java.util.Map;

public record SearchResults(List<ArtistDTO> artists, Map<String, Object> otherProps) {

   public SearchResults(List<ArtistDTO> artists) {
      this(artists, Map.of());
   }
}
