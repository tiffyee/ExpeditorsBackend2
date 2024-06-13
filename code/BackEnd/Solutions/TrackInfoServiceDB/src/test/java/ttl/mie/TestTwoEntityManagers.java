package ttl.mie;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ttl.mie.domain.sakila.Customer;

@SpringBootTest
@Disabled
public class TestTwoEntityManagers {

    @PersistenceUnit(unitName = "sakilaEntityManagerFactory")
    private EntityManagerFactory sakilaEmf;

    @PersistenceContext(unitName = "sakilaEntityManagerFactory")
    private EntityManager sakilaEntityManager;

    @Test
    public void testAllCustomersSakila() {
        String jpql = "select c from Customer c";

        TypedQuery<Customer> query = sakilaEntityManager.createQuery(jpql, Customer.class);
        List<Customer> result = query.getResultList();

        result.forEach(System.out::println);
    }
}
