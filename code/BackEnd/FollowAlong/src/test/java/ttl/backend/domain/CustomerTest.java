package ttl.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
    @Test
    public void testCustomerConstructor(){
        int id = 10;
        String name = "Boris";
        String address = "222 Here st";
        String taxIId = "YY-333";

        Customer customer = new Customer(10, "Boris","222 Here st", "YY-333");

        assertEquals(id, customer.getCustomerID());
        assertEquals(name,customer.getName());
        assertEquals(address,customer.getAddress());
        assertEquals(taxIId, customer.getTaxID());


    }
}