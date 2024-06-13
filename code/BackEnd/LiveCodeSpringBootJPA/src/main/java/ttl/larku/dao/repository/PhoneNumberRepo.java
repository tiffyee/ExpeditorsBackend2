package ttl.larku.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttl.larku.domain.PhoneNumber;

@Repository
public interface PhoneNumberRepo extends JpaRepository<PhoneNumber, Integer> {
}
