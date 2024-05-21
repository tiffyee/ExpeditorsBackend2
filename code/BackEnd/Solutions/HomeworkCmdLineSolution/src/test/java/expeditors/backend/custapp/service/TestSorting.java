package expeditors.backend.custapp.service;

import expeditors.backend.custapp.domain.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author whynot
 */
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TestSorting {

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    public void init() {
//        customerService = new CustomerService();
    }

    @Test
    public void testSortByNaturalOrder() {
        //We need the ArrayList because List.of returns an immutable List
        //but Collections.sort sorts a list "in place" and so requires a
        //mutable List.  We could have also just created an emtpy ArrayList
        //and then added to it.
        List<Customer> customers = new ArrayList<>(List.of(
                new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL),
                new Customer("Francine", LocalDate.of(1980, 6, 9), Customer.Status.PRIVILEGED),
                new Customer("Lata", LocalDate.of(2000, 7, 9), Customer.Status.NORMAL),
                new Customer("Francine", LocalDate.of(1990, 6, 9), Customer.Status.RESTRICTED)
        ));
        customers.forEach(c -> customerService.addCustomer(c));

        Collections.sort(customers);

        assertEquals(1, customers.get(0).getId());
        assertEquals(4, customers.get(3).getId());
    }

    @Test
    public void testSortByOtherOrders() {
        //We need the ArrayList because List.of returns an immutable List
        //but Collections.sort sorts a list "in place" and so requires a
        //mutable List.  We could have also just created an emtpy ArrayList
        //and then added to it.
        List<Customer> customers = new ArrayList<>(List.of(
                new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL),
                new Customer("Francine", LocalDate.of(1980, 6, 9), Customer.Status.PRIVILEGED),
                new Customer("Lata", LocalDate.of(2000, 7, 9), Customer.Status.NORMAL),
                new Customer("Anjini", LocalDate.of(1990, 6, 9), Customer.Status.RESTRICTED)
        ));
        customers.forEach(c -> customerService.addCustomer(c));

        //By Name
        Collections.sort(customers, (c1, c2) -> c1.getName().compareTo(c2.getName()));
        assertTrue(customers.get(0).getName().contains("Anjini"));

        //By Dob
        Collections.sort(customers, (c1, c2) -> c1.getDob().compareTo(c2.getDob()));
        assertTrue(customers.get(0).getName().contains("Joey"));

        //Another way using Comparators.comparing
        Collections.sort(customers, Comparator.comparing(Customer::getDob));
        assertTrue(customers.get(0).getName().contains("Joey"));


    }
}
