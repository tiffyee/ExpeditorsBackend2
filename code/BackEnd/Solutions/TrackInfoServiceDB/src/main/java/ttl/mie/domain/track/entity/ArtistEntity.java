package ttl.mie.domain.track.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artist")
public class ArtistEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "artist_id")
   private int artistId;

   @Column(name = "discogs_id")
   private String discogsId;

   private String name;

   @JsonProperty("realname")
   @Column(name = "real_name")
   private String realName;

   private String url;

   @Column(length = 10000)
   private String profile;

   public ArtistEntity() {}

   public ArtistEntity(String discogsId, String name,
                       String realName, String url, String profile) {
      this.discogsId = discogsId;
      this.name = name;
      this.realName = realName;
      this.url = url;
      this.profile = profile;

   }
   public ArtistEntity(String name, String realName, String profile) {
      this(null, name, realName, "", profile);
   }

   public ArtistEntity(String name) {
      this(null, name, "", "", "");
   }

   public int getArtistId() {
      return artistId;
   }

   public void setArtistId(int id) {
      this.artistId = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getRealName() {
      return realName;
   }

   public void setRealName(String realName) {
      this.realName = realName;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getProfile() {
      return profile;
   }

   public void setProfile(String profile) {
      this.profile = profile;
   }

   public String getDiscogsId() {
      return discogsId;
   }

   @Override
   public String toString() {
      return "ArtistEntity{" +
            "artistId=" + artistId +
            ", discogsId='" + discogsId + '\'' +
            ", name='" + name + '\'' +
            ", realName='" + realName + '\'' +
            ", url='" + url + '\'' +
            ", profile='" + profile + '\'' +
            '}';
   }

   public void setDiscogsId(String discogsId) {
      this.discogsId = discogsId;
   }
}
