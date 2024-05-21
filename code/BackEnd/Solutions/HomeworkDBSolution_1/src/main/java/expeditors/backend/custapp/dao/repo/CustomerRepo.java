package expeditors.backend.custapp.dao.repo;

import expeditors.backend.custapp.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author whynot
 */
@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
