package expeditors.backend.custapp.week3;

import expeditors.backend.custapp.week3.domain.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author whynot
 */
public class TestCustomer {

    @Test
    public void testAllArgsConstructor() {
        Customer customer = new Customer(1, "Joey",LocalDate.of(1947, 10, 10), Customer.Status.RESTRICTED);
        assertEquals(Customer.Status.RESTRICTED, customer.getStatus());

        Customer c2 = new Customer(2, "Zimita", LocalDate.of(1999, 8, 15));
        assertEquals(Customer.Status.NORMAL, c2.getStatus());

    }
}
