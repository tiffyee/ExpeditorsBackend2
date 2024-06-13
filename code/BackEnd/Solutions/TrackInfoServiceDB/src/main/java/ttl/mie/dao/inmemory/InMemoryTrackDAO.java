package ttl.mie.dao.inmemory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.track.dto.TrackDTO;

@Repository
public class InMemoryTrackDAO implements ttl.mie.dao.TrackDAO {

   private Map<Integer, TrackDTO> tracks = new ConcurrentHashMap<>();
   private AtomicInteger nextId = new AtomicInteger(1);

   @Override
   public TrackDTO insert(TrackDTO trackDTO) {
      int id = nextId.getAndIncrement();
      var newAti = TrackDTO.copyWithId(id, trackDTO);

      tracks.put(id, newAti);
      return newAti;
   }

   @Override
   public boolean delete(int id) {
      return tracks.remove(id) != null;
   }

   @Override
   public boolean update(TrackDTO trackDTO) {
      return tracks.replace(trackDTO.trackId(), trackDTO) != null;
   }

   @Override
   public Optional<TrackDTO> findById(int id) {
      return Optional.ofNullable(tracks.get(id));
   }

   @Override
   public List<TrackDTO> findAll() {
      return List.copyOf(tracks.values());
   }

   @Override
   public List<TrackDTO> findByExample(TrackDTO example) {
      Predicate<TrackDTO> pred = null;
      //We are going to 'or' all the fields in the example object.
      if (example.title() != null) {
         Predicate<TrackDTO> titlePred = (t) -> t.title() != null
               && t.title().toUpperCase().contains(example.title().toUpperCase());
         pred = pred == null ? titlePred : pred.or(titlePred);
      }

      if (!example.artists().isEmpty()) {
         Predicate<TrackDTO> titlePred = (t) ->
               t.artists().stream().anyMatch(a -> a.name().toUpperCase().contains(
                     example.artists().getFirst().name().toUpperCase()));
         pred = pred == null ? titlePred : pred.or(titlePred);
      }

      if (example.album() != null) {
         Predicate<TrackDTO> titlePred = (t) -> t.album() != null
               && t.album().toUpperCase().contains(example.album().toUpperCase());
         pred = pred == null ? titlePred : pred.or(titlePred);
      }
      //etc. etc.

      //If nothing yet, then return all.
      pred = pred == null ? (t) -> true : pred;

      List<TrackDTO> result = findAll()
            .stream()
            .filter(pred)
            .collect(Collectors.toList());

      return result;
   }

   @Override
   public List<TrackDTO> findBySearchSpec(List<InMemorySearchSpec<TrackDTO>> searchSpecs) {
      Predicate<TrackDTO> pred = null;

      for (InMemorySearchSpec<TrackDTO> spec : searchSpecs) {
//         String propName = spec.propName;
//         String value = ((String)spec.propValue).toUpperCase();
         //Handle artists di
//         if (propName.equals("artist")) {
//            Predicate<TrackDTO> artistPred = (t) ->
//                  t.artists().stream().anyMatch(a -> {
//                     var result = a.name().toUpperCase().contains(value);
//                     return result;
//                  });
//            spec.predicate = artistPred;
//         }

         pred = pred == null ? spec.predicate : pred.or(spec.predicate);
      }

      //etc. etc.

      //If nothing yet, then return all.
      pred = pred == null ? (t) -> true : pred;

      List<TrackDTO> result = findAll()
            .stream()
            .filter(pred)
            .collect(Collectors.toList());

      return result;
   }
}
