package ttl.mie.db;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import javax.sound.midi.Track;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.dao.inmemory.InMemoryTrackDTODao;
import ttl.mie.dao.repository.track.ArtistRepo;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.domain.track.entity.ArtistEntity;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.extractor.AudioInfoExtractor;
import ttl.mie.extractor.DiscogRestHandler;

@Component
public class DBUtils {
   @Autowired
   private DiscogRestHandler discogRestHandler;

   @Autowired
   private InMemoryTrackDTODao audioInfoDao;

   @Autowired
   private ArtistRepo artistRepo;

   @Autowired
   private TrackRepo trackRepo;

   @Autowired
   private AudioInfoExtractor atst;

   @Transactional
   public int addTracksToDB(List<TrackDTO> tracks) throws Exception {
      tracks.forEach(audioInfoDao::insert);
      int numTracks = convertToEntityAndAdd(tracks);
      return numTracks;
   }
   
   public int convertToEntityAndAdd(List<TrackDTO> tracks) {
      int numTracks = 0;
      for(TrackDTO ati: tracks) {
         List<ArtistDTO> artists = ati.artists();
         List<ArtistEntity> artistEntities = artists.stream()
               .map(artist -> {
                  var discogsId = artist.discogsId();
                  ArtistEntity newArtist = artistRepo.findByDiscogsId(discogsId)
                        .orElseGet(() -> {
                           var url = !artist.urls().isEmpty() ? artist.urls().getFirst() : "";
                           var tmp = new ArtistEntity(artist.discogsId(),
                                 artist.name(),
                                 artist.realName(),
                                 url,
                                 artist.profile());

                           tmp = artistRepo.save(tmp);

                           return tmp;
                        });

                  return newArtist;

               }).collect(Collectors.toList());

         TrackEntity newTrack =
               TrackEntity.title(ati.title())
                     .title(ati.title())
                     .format(ati.format())
                     .genre(ati.genre())
                     .year(Strings.isNotBlank(ati.year()) ? ati.year() : null)
                     .group(ati.group())
                     .imageUrl(ati.imageUrl())
                     .artists(artistEntities)
                     .album(Strings.isNotBlank(ati.album()) ? ati.album() : null)
                     .duration(ati.length())
                     .build();

         newTrack = trackRepo.save(newTrack);
         numTracks++;
      };

      return numTracks;
   }
}
