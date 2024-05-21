package expeditors.backend.custapp.dao.repo;

import expeditors.backend.custapp.dao.jpa.JPACustomerDAO;
import expeditors.backend.custapp.dao.repo.CustomerRepo;
import expeditors.backend.custapp.domain.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
@SpringBootTest
@Transactional
public class TestCustomerRepo {

    @Autowired
    private CustomerRepo customerDAO;

    @PersistenceUnit
    private EntityManagerFactory emf;

    @BeforeEach
    public void init() {
//        customerDAO = new JPACustomerDAO();
    }

    @Test
    public void testGetAll() {
        List<Customer> customers = customerDAO.findAll();
        int oldSize = customers.size();

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.save(customer);

        customers = customerDAO.findAll();
        assertEquals(oldSize + 1, customers.size());
    }

    @Test
    public void testGetCustomerWithExistingId() {

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9),
                Customer.Status.NORMAL);
        assertEquals(0, customer.getId());
        customerDAO.save(customer);

        customer = customerDAO.findById(1).orElse(null);
        assertNotNull(customer);
    }

    @Test
    public void testGetCustomerWithNonExistingId() {

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        assertEquals(0, customer.getId());
        customerDAO.save(customer);

        customer = customerDAO.findById(1000).orElse(null);
        assertNull(customer);
    }

    @Test
    public void testInsert() {
        int maxId = customerDAO.findAll()
                .stream()
                .mapToInt(Customer::getId)
                .max().orElse(0);

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        Customer insertedCustomer = customerDAO.save(customer);

        assertTrue(insertedCustomer.getId() >= maxId + 1) ;
    }

    @Test
    public void testDelete() {
        int testId = 1;
        Customer customer1 = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.save(customer1);

        Customer c = customerDAO.findById(customer1.getId()).orElse(null);
        assertNotNull(c);

        customerDAO.delete(c);

        c = customerDAO.findById(customer1.getId()).orElse(null);
        assertNull(c);
    }

    @Test
    public void testUpdateExistingCustomer() {
        Customer customer1 = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        Customer insertedCustomer = customerDAO.save(customer1);

        Customer c = customerDAO.findById(insertedCustomer.getId()).orElse(null);
        assertNotNull(c);

        String newName = "Martha";
        c.setName(newName);

        customerDAO.save(c);

        c = customerDAO.findById(insertedCustomer.getId()).orElse(null);
        assertEquals(newName, c.getName());
    }

//    @Test
//    public void testFindByExample() {
////        List<Customer> customers = List.of(
////                new Customer("Example1", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL),
////                new Customer("Example2", LocalDate.of(1980, 6, 9), Customer.Status.PRIVILEGED),
////                new Customer("Example3", LocalDate.of(2000, 7, 9), Customer.Status.NORMAL),
////                new Customer("Example4", LocalDate.of(1990, 6, 9), Customer.Status.RESTRICTED)
////        );
////        customers.forEach(c -> customerDAO.insert(c));
//
//        Customer example = new Customer("Joey", LocalDate.of(1990, 6, 9), Customer.Status.PRIVILEGED);
//
//        List<Customer> foundCustomers = customerDAO.findByExample(example);
//
//        assertEquals(3, foundCustomers.size());
//
//        System.out.println(foundCustomers);
//    }

}
