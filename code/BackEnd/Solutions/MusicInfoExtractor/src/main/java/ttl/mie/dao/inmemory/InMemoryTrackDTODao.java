package ttl.mie.dao.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.track.dto.TrackDTO;

@Repository
public class InMemoryTrackDTODao {

   private Map<Integer, TrackDTO> tracks = new ConcurrentHashMap<>();
   private AtomicInteger nextId = new AtomicInteger(1);

   public TrackDTO insert(TrackDTO trackDTO) {
      int id = nextId.getAndIncrement();
      var newAti = TrackDTO.copyWithId(id, trackDTO);

      tracks.put(id, newAti);
      return newAti;
   }

   public boolean delete(int id) {
      return tracks.remove(id) != null;
   }

   public boolean update(TrackDTO trackDTO) {
      return tracks.replace(trackDTO.trackId(), trackDTO) != null;
   }

   public Optional<TrackDTO> findById(int id) {
      return Optional.ofNullable(tracks.get(id));
   }

   public List<TrackDTO> findAll() {
      return List.copyOf(tracks.values());
   }
}
