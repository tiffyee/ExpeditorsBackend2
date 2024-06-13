package ttl.mie.domain.track.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public record ArtistDTO(int artistId,
                        @JsonAlias("id")
                        String discogsId,
                        String name,
                        @JsonProperty("realname")
                        String realName,
                        String profile,
                        List<String> urls,
                        List<TrackDTO> tracks) {
   public ArtistDTO(int artistId, String discogsId, String name, String realName,
                    String profile, List<String> urls, List<TrackDTO> tracks) {
      this.artistId = artistId;
      this.discogsId = discogsId;
      this.name = name;
      this.realName = realName;
      this.profile = profile;
      this.urls = new ArrayList<>();
      if(urls != null) {
        this.urls.addAll(urls);
      }
      this.tracks = new ArrayList<>();
      if (tracks != null) {
         this.tracks.addAll(tracks);
      }
   }

   public ArtistDTO(String name, String realName, String profile) {
      this(0, null, name, realName, profile, null, null);
   }

   public ArtistDTO(String name) {
      this(0, null, name, null, null, null, null);
   }

   public void addAudioTrackInfo(TrackDTO trackDTO) {
      this.tracks.add(trackDTO);
   }

   public static ArtistDTO copyWithId(int id, ArtistDTO adto) {
      var newAdto = new ArtistDTO(id,
            adto.discogsId(),
            adto.name(),
            adto.realName(),
            adto.profile(),
            adto.urls(),
            adto.tracks());

      return newAdto;
   }

   public static ArtistDTO copyWithDiscogsId(String discoggsId, ArtistDTO adto) {
      var newAdto = new ArtistDTO(adto.artistId(),
            discoggsId,
            adto.name(),
            adto.realName(),
            adto.profile(),
            adto.urls(),
            adto.tracks());

      return newAdto;
   }
}
