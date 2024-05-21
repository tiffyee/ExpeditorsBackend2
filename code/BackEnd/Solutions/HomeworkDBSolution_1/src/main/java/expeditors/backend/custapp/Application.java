package expeditors.backend.custapp;

import expeditors.backend.custapp.domain.Customer;
import expeditors.backend.custapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * @author whynot
 */
//@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

@Component
class CustAppInitializer implements CommandLineRunner
{

    @Autowired
    private CustomerService customerservice;

    @Override
    public void run(String... args) throws Exception {
        List.of(
                new Customer("Joey", LocalDate.of(1960, 6, 9), Customer.Status.NORMAL),
                new Customer("Francine", LocalDate.of(1980, 6, 9), Customer.Status.PRIVILEGED),
                new Customer("Lata", LocalDate.of(2000, 7, 9), Customer.Status.NORMAL),
                new Customer("Francine", LocalDate.of(1990, 6, 9), Customer.Status.RESTRICTED)
        ).forEach(customerservice::addCustomer);

        List<Customer> customers = customerservice.getCustomers();

        System.out.println("customers: " + customers.size());
        customers.forEach(System.out::println);
    }
}
