package ttl.mie.domain.track.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.Duration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import ttl.mie.domain.track.Format;

@Entity
@Table(name = "track")
@NamedQuery(name = "Track.getByTitle", query = "select t from TrackEntity t where t.title like :title")
public class TrackEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "track_id")
	private Integer trackId;

	private String title;
	private String album;

	@Column(name = "band")
	private String group;

	private String imageUrl;

	private String genre;

	private Duration duration;

	@Column(name = "release_year")
	private String releaseYear;

	@Enumerated(EnumType.STRING)
	private Format format;


	@Transient
	private String price;

	@OneToMany(cascade = {CascadeType.ALL})
	@JoinTable(name = "track_artist",
			joinColumns = @JoinColumn(name = "track_id", referencedColumnName = "track_id"),
			inverseJoinColumns = @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
	)
	private Set<ArtistEntity> artists;


	public TrackEntity() {
		super();
	}

	public TrackEntity(String title, String album, String group, String imageUrl, Duration duration,
							 String releaseYear, Format format, String genre, Set<ArtistEntity> artists) {

		this.title = title;
		this.album = album;
		this.group = group;
		this.imageUrl = imageUrl;
		this.releaseYear = releaseYear;
		this.duration = duration;
		this.format = format;
		this.genre = genre;
		this.artists = new HashSet<>();
		if(artists != null) {
			this.artists.addAll(artists);
		}
	}

	public int getTrackId() {
		return trackId;
	}

	public void setTrackId(int id) {
		this.trackId = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<ArtistEntity> getArtists() {
		return artists;
	}

	public void setArtists(Set<ArtistEntity> artists) {
		this.artists.clear();
		this.artists.addAll(artists);
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public String getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(String year) {
		this.releaseYear = year;
	}

	public Format getFormat() {
		return format;
	}

	public void setFormat(Format format) {
		this.format = format;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


	public void addArtist(ArtistEntity artist) {
		artists.add(artist);
	}

	@Override
	public String toString() {
		return "TrackEntity{" +
				"id=" + trackId +
				", title='" + title + '\'' +
				", album='" + album + '\'' +
				", genre='" + genre + '\'' +
				", duration=" + duration +
				", releaseYear='" + releaseYear + '\'' +
				", format=" + format +
				", price='" + price + '\'' +
				", artists=" + artists +
				'}';
	}

	public static Duration hmsToDuration(String hms) {
		String sp [] = hms.split(":");
		Duration duration = Duration.ofHours(Integer.valueOf(sp[0]))
				.plusMinutes(Integer.valueOf(sp[1]));
		if(sp.length == 3) {
			duration = duration.plusSeconds(Integer.valueOf(sp[2]));
		}
		return duration;
	}

	public static Builder id(int arg) {
		return new Builder().id(arg);
	}

	public static Builder title(String arg) {
		return new Builder().title(arg);
	}

	public static Builder album(String arg) {
		return new Builder().album(arg);
	}
	public static Builder artist(String arg) {
		return new Builder().artist(arg);
	}

	public static Builder duration(String arg) {
		return new Builder().duration(arg);
	}

	public static Builder year(String arg) {
		return new Builder().year(arg);
	}

	public static Builder format(Format arg) {
		return new Builder().format(arg);
	}

	public static Builder format(String arg) {
		return new Builder().format(arg);
	}


	/**
	 * Make us a Builder
	 */
	public static class Builder {
		private int id;
		private String title;
		private String album;
		private String group;
		private String imageUrl;
		private String genre;
		private Duration duration;
		private String year;
		private Format format;
		private Set<ArtistEntity> artists = new HashSet<>();

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder artist(String artist) {
			var newArtist = new ArtistEntity(artist);
			return artist(newArtist);
		}

		public Builder artist(ArtistEntity artist) {
			this.artists.add(artist);
			return this;
		}

		public Builder artists(List<ArtistEntity> artists) {
			return artists(new HashSet<>(artists));
		}

		public Builder artists(Set<ArtistEntity> artists) {
			this.artists.clear();
			this.artists.addAll(artists);
			return this;
		}

		public Builder group(String group) {
			this.group = group;
			return this;
		}
		public Builder imageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public Builder album(String album) {
			this.album = album;
			return this;
		}

		public Builder genre(String genre) {
			this.genre = genre;
			return this;
		}

		public Builder duration(String duration) {
			String durString;
			if(!duration.startsWith("P")) {
				var arr = duration.split(":");
				durString = "PT" + arr[0] + "M" + arr[1] + "S";
			} else {
				durString = duration;
			}
			return duration(Duration.parse(durString));
		}

		public Builder duration(Duration duration) {
			this.duration = duration;
			return this;
		}

		public Builder year(String year) {
			this.year = year;
			return this;
		}

		public Builder format(String formatStr) {
			return format(Format.valueOf(formatStr));
		}

		public Builder format(Format format) {
			this.format = format;
			return this;
		}

		public TrackEntity build() {
			TrackEntity t = new TrackEntity(title, album, group, imageUrl, duration, year, format, genre, artists);
			return t;
		}
	}

}
