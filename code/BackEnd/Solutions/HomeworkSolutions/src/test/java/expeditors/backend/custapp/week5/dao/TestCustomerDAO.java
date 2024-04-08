package expeditors.backend.custapp.week5.dao;

import expeditors.backend.custapp.week5.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author whynot
 */
public class TestCustomerDAO {

    private InMemoryCustomerDAO customerDAO;

    @BeforeEach
    private void init() {
        customerDAO = new InMemoryCustomerDAO();
    }

    @Test
    public void testGetAll() {
        List<Customer> customers = customerDAO.findAll();
        assertEquals(0, customers.size());

        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.insert(customer);

        customers = customerDAO.findAll();
        assertEquals(1, customers.size());
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
        Customer customer = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        Customer insertedCustomer = customerDAO.insert(customer);

        assertEquals(1, insertedCustomer.getId());
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
        int testId = 1;
        Customer customer1 = new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL);
        customerDAO.insert(customer1);

        Customer c = customerDAO.find(testId);
        assertNotNull(c);

        String newName = "Martha";
        c.setName(newName);

        boolean result = customerDAO.update(c);
        assertTrue(result);

        c = customerDAO.find(testId);
        assertEquals(newName, c.getName());
    }

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

}
