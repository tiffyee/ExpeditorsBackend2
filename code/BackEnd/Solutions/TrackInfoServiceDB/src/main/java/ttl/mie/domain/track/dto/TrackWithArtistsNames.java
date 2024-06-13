package ttl.mie.domain.track.dto;

import java.util.List;

public record TrackWithArtistsNames(String title, List<String> artistNames) {
}
