package ttl.mie.domain.track.dto;

import java.time.Duration;
import ttl.mie.domain.track.Format;

//String jpql = "select t.title, t.album, t.duration, a.name, a.realName from TrackEntity t join fetch t.artists a where a.name like :name";
public record ShortResult(String title, String album, Duration duration, String genre,
                          Format format,
                          String releaseYear, String artistName, String realName) {
}
