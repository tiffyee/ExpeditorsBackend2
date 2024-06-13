package ttl.mie.dao.repository;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ttl.mie.dao.repository.track.TrackRepo;
import ttl.mie.domain.track.entity.TrackEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
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
        assertTrue(newTrack.getTrackId() > 0);
    }

    @Test
    public void testGetAll() {
        List<TrackEntity> tracks = trackRepo.findTracksWithArtists();

        tracks.forEach(System.out::println);
        System.out.println("num tracks: " + tracks.size());
        assertEquals(6, tracks.size());
    }


}
