package expeditors.backend.custapp.dao.jpa;

import expeditors.backend.custapp.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
@SpringBootTest
@EnabledIf(expression = "#{environment.matchesProfiles('prod')}", loadContext = true)
public class TestJpaCustomerDAO {

    @Autowired
    private JPACustomerDAO customerDAO;

    @BeforeEach
    public void init() {
//        customerDAO = new JPACustomerDAO();
    }

    @Test
    public void testGetAll() {
        List<Customer> customers = customerDAO.findAll();
        int oldSize = customers.size();

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.insert(customer);

        customers = customerDAO.findAll();
        assertEquals(oldSize + 1, customers.size());
    }

    @Test
    public void testGetCustomerWithExistingId() {

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        assertEquals(0, customer.getId());
        customerDAO.insert(customer);

        customer = customerDAO.find(1);
        assertNotNull(customer);
    }

    @Test
    public void testGetCustomerWithNonExistingId() {

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        assertEquals(0, customer.getId());
        customerDAO.insert(customer);

        customer = customerDAO.find(1000);
        assertNull(customer);
    }

    @Test
    public void testInsert() {
        long count = customerDAO.findAll()
                .stream()
                .count();

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        Customer insertedCustomer = customerDAO.insert(customer);

        long count2 = customerDAO.findAll()
                .stream()
                .count();

        assertEquals(count + 1, count2);
    }

    @Test
    public void testDelete() {
        int testId = 1;
        Customer customer1 = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.insert(customer1);

        Customer c = customerDAO.find(testId);
        assertNotNull(c);

        boolean result = customerDAO.delete(testId);
        assertTrue(result);

        c = customerDAO.find(testId);
        assertNull(c);
    }

    @Test
    public void testDeleteNonExistingCustomer() {
        int testId = 1000;
        Customer c = customerDAO.find(testId);
        assertNull(c);

        boolean result = customerDAO.delete(testId);
        assertFalse(result);
    }

    @Test
    public void testUpdateExistingCustomer() {
        Customer customer1 = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        Customer insertedCustomer = customerDAO.insert(customer1);

        Customer c = customerDAO.find(insertedCustomer.getId());
        assertNotNull(c);

        String newName = "Martha";
        c.setName(newName);

        boolean result = customerDAO.update(c);
        assertTrue(result);

        c = customerDAO.find(insertedCustomer.getId());
        assertEquals(newName, c.getName());
    }

    @Test
    public void testUpdateNonExistingCustomer() {
        int testId = 1;
        Customer customer1 = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.insert(customer1);

        Customer c = customerDAO.find(testId);
        assertNotNull(c);

        String newName = "Martha";
        c.setName(newName);
        c.setId(1000);

        boolean result = customerDAO.update(c);
        assertFalse(result);
    }

//    @Test
//    @DirtiesContext
//    public void testFindByExample() {
////        List<Customer> customers = List.of(
////                new Customer("Example1", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL),
////                new Customer("Example2", LocalDate.of(1980, 6, 9), Customer.Status.PRIVILEGED),
////                new Customer("Example3", LocalDate.of(2000, 7, 9), Customer.Status.NORMAL),
////                new Customer("Example4", LocalDate.of(1990, 6, 9), Customer.Status.RESTRICTED)
////        );
////        customers.forEach(c -> customerDAO.insert(c));
//
//        Customer example = new Customer("Manoj",
//                LocalDate.of(1994, 10, 7),
//                Customer.Status.RESTRICTED);
//
//        List<Customer> foundCustomers = customerDAO.findByExample(example);
//
//        assertEquals(3, foundCustomers.size());
//
//        System.out.println(foundCustomers);
//    }
}
