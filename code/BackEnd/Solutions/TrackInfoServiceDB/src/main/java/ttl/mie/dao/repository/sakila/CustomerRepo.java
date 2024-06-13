package ttl.mie.dao.repository.sakila;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttl.mie.domain.sakila.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {
}
