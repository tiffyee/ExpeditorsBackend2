package ttl.mie.dao.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestTrackRepoEntity {

    @Autowired
    private TrackRepo trackRepo;

    public TestTrackRepoEntity() {
        int stop = 10;
    }

    @Test
    public void testInsertTrack() {
        TrackEntity te = TrackEntity.album("Mortys revenge")
              .artist("Jingle Smith")
              .year("2009")
              .format("CD")
              .build();

        var newTrack = trackRepo.save(te);

        System.out.println("New Track: " + newTrack);
    }

    @Test
    public void testGetAll() {
        List<TrackEntity> tracks = trackRepo.findTracksWithArtists();

        tracks.forEach(System.out::println);
        System.out.println("num tracks: " + tracks.size());
    }

}
