package ttl.mie.db;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.dao.TrackDAO;
import ttl.mie.dao.repository.track.ArtistRepo;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.dto.ArtistDTO;
import ttl.mie.domain.track.dto.TrackDTO;
import ttl.mie.domain.track.entity.ArtistEntity;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.extractor.DiscogRestHandler;

@Component
@Transactional
public class DBUtils {
   @Autowired
   private DiscogRestHandler discogRestHandler;

   @Autowired
   private TrackDAO audioInfoDao;

   @Autowired
   private ArtistRepo artistRepo;

   @Autowired
   private TrackRepo trackRepo;

   public int addTracksToDB(List<TrackDTO> tracks) throws Exception {
      tracks.forEach(audioInfoDao::insert);
      convertToEntityAndAdd(tracks);
      return tracks.size();
   }

   public void convertToEntityAndAdd(List<TrackDTO> tracks) {
      tracks.forEach(ati -> {
         List<ArtistDTO> artists = ati.artists();
         List<ArtistEntity> artistEntities = artists.stream()
               .map(artist -> {
                  var discogsId = artist.discogsId();
                  if(discogsId == null) {
                    return makeArtistEntity(artist);
                  }
                  var opt = artistRepo.findByDiscogsId(discogsId);
                  ArtistEntity newArtist = opt
                        .orElseGet(() -> {
                           var url = !artist.urls().isEmpty() ? artist.urls().getFirst() : "";
                           var tmp = makeArtistEntity(artist);

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
      });
   }

   private ArtistEntity makeArtistEntity(ArtistDTO artist) {
      var url = !artist.urls().isEmpty() ? artist.urls().getFirst() : "";
      var tmp = new ArtistEntity(artist.discogsId(),
            artist.name(),
            artist.realName(),
            url,
            artist.profile());

      return tmp;
   }
}
