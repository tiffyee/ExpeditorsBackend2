package ttl.mie.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import ttl.mie.dao.inmemory.InMemorySearchSpec;
import ttl.mie.domain.track.dto.TrackDTO;

public interface TrackDAO {
   TrackDTO insert(TrackDTO trackDTO);

   boolean delete(int id);

   boolean update(TrackDTO trackDTO);

   Optional<TrackDTO> findById(int id);

   List<TrackDTO> findAll();

   default List<TrackDTO> findBy(Predicate<TrackDTO> pred) {
      return findAll().stream()
            .filter(pred)
            .toList();
   }

   List<TrackDTO> findByExample(TrackDTO example);

   default List<TrackDTO> findBySearchSpec(List<InMemorySearchSpec<TrackDTO>> searchSpecs) {
     throw new UnsupportedOperationException("Needs Implementing") ;
   }
}
