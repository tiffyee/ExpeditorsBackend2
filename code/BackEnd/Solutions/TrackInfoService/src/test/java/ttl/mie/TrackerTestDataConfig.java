package ttl.mie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ttl.mie.dao.TrackDAO;
import ttl.mie.dao.inmemory.InMemoryTrackDAO;
import ttl.mie.domain.track.Format;
import ttl.mie.domain.track.dto.TrackDTO;

@Configuration
@Profile("development")
public class TrackerTestDataConfig {

//    @Bean
//    public TrackDTO track1() {
//        TrackDTO track = TrackDTO.builder().title("The Shadow Of Your Smile").artist("Big John Patton")
//                .album("Let 'em Roll").duration("06:15").date("1965-01-02").format("CD").build();
//        return track;
//    }
//
//    @Bean
//    public TrackDTO track2() {
//        TrackDTO track = TrackDTO.builder().title("I'll Remember April").artist("Jim Hall and Ron Carter")
//                .album("Alone Together").duration("05:54").date("1972-01-02").format("OGG").build();
//        return track;
//    }
//
//    @Bean
//    public TrackDTO track3() {
//        TrackDTO track = TrackDTO.builder().title("What's New").artist("John Coltrane")
//                .album("Ballads").length(03:47").build();
//        return track;
//    }


    /**
     * These tracks do NOT have id's yet
     *
     * @return
     */
    @Bean
    public List<TrackDTO> tracks() {
        List<TrackDTO> tracks = new ArrayList<>();
        tracks.add(TrackDTO.builder().title("The Shadow Of Your Smile").artist("Big John Patton")
                .album("Let 'em Roll").length(375).year("1965").format("CD").build());
        tracks.add(TrackDTO.builder().title("I'll Remember April").artist("Jim Hall and Ron Carter")
                .album("Alone Together").length(354).year("1972").format("CD").build());
        tracks.add(TrackDTO.builder().title("What's New").artist("John Coltrane")
                .album("Ballads").length(227).build());
        tracks.add(TrackDTO.builder().title("Leave It to Me").artist("Herb Ellis")
                .album("Three Guitars in Bossa Nova Time")
              .length(203).year("1963").format(Format.OGG).build());

        tracks.add(TrackDTO.builder().title("Have you met Miss Jones").artist("George Van Eps")
                .album("Pioneers of the Electric Guitar").length(138).year("2013")
              .format("MP3").build());

        tracks.add(TrackDTO.builder().title("My Funny Valentine").artist("Johnny Smith")
                .album("Moonlight in Vermont").length(168).build());

        return tracks;
    }

    private Date convertToDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);

        return cal.getTime();
    }

    public TrackDAO trackDAOWithInitData() {
        InMemoryTrackDAO dao = new InMemoryTrackDAO();

        //tracks().forEach(t -> dao.create(t));
        tracks().forEach(dao::insert);

        return dao;
    }
}
