package expeditors.backend.custapp.week3;

import expeditors.backend.custapp.week3.domain.Customer;

import java.time.LocalDate;

/**
 * @author whynot
 */
public class Application {

    public static void main(String[] args) {

        Customer c1 = new Customer(1, "Joey", LocalDate.of(1947, 10, 10), Customer.Status.RESTRICTED);
        Customer c2 = new Customer(2, "Zimita", LocalDate.of(1999, 8, 15));

        System.out.println("c1 id: " + c1.getId() + ", name: " + c1.getName() + ", dob: "
                + c1.getDob() + ", status: " + c1.getStatus());
        System.out.println("c2 id: " + c2.getId() + ", name: " + c2.getName() + ", dob: "
                + c2.getDob() + ", status: " + c2.getStatus());
    }
}
