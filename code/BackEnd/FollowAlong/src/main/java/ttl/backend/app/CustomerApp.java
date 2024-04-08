package ttl.backend.app;

import ttl.backend.domain.Customer;

public class CustomerApp {

    public static void main(String[] args) {

        Customer customer = new Customer(10,"Rashmi", "222 Main Street, Hereville, MN", "XXX-0038");

        System.out.println("id:" + customer.getCustomerID());

    }
}
