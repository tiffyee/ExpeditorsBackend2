package expeditors.backend.app;

import expeditors.backend.domain.Customer;

import static java.lang.System.out;

public class CustomerApp {

   public static void main(String[] args) {
      Customer customer = new Customer(10, "Rashmi", "222 Main Street, Hereville, NM", "XXX-0038");

      out.println("id: " + customer.getCustomerId());
   }
}
