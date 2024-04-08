package expeditors.backend.custapp.week2;


import expeditors.backend.custapp.week2.domain.Customer;

import java.time.LocalDate;

/**
 * @author whynot
 */
public class Application {
    /*
    Create a class called Application where you create 2 instances
    of your class, populate them with appropriate values, and then
    print them out.  Notice how you have to interact with the
    class.  Is it cumbersome to use?
     */
    public static void main(String[] args) {
        Customer c1 = new Customer();
        c1.setId(1);
        c1.setName("Joey");
        c1.setDob(LocalDate.of(1947, 10, 10));
        c1.setStatus(Customer.Status.RESTRICTED);

        Customer c2 = new Customer();
        c2.setId(2);
        c2.setName("Zimta");
        c2.setDob(LocalDate.of(1999, 8, 15));
        c2.setStatus(Customer.Status.PRIVILEGED);

        System.out.println("c1 id: " + c1.getId() + ", name: " + c1.getName() + ", dob: " + c1.getDob() + ", status: " + c1.getStatus());
        System.out.println("c2 id: " + c2.getId() + ", name: " + c2.getName() + ", dob: " + c2.getDob() + ", status: " + c2.getStatus());
    }
}
