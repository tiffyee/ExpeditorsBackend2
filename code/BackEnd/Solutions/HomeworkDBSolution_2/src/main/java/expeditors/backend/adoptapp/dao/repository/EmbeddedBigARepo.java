package expeditors.backend.adoptapp.dao.repository;

import expeditors.backend.adoptapp.domain.embedded.BigAdopterEmbedded;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmbeddedBigARepo extends JpaRepository<BigAdopterEmbedded, Integer> {
}
