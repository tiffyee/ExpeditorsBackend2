package expeditors.backend.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {

   @Test
   public void testCustomerConstructor() {
      int id = 10;
      String name = "Boris";
      String address = "222 Here st";
      String taxId = "YY-333";
      Customer customer = new Customer(id, name, address, taxId);

      assertEquals(id, customer.getCustomerId());
      assertEquals(name, customer.getName());
      assertEquals(address, customer.getAddress());
      assertEquals(taxId, customer.getTaxId());
   }
}
