package expeditors.backend.custapp.week6;

import expeditors.backend.custapp.week6.domain.Customer;
import expeditors.backend.custapp.week6.jconfig.CustAppConfig;
import expeditors.backend.custapp.week6.service.CustomerService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

/**
 * @author whynot
 */
public class Application {

    public static void main(String[] args) {
        Application app = new Application();
        app.go();
    }

    public void go() {
        ApplicationContext context = new AnnotationConfigApplicationContext(CustAppConfig.class);

        CustomerService service = context.getBean("customerService", CustomerService.class);

        List<Customer> customers = service.getCustomers();

        System.out.println("customers: " + customers.size());
        customers.forEach(System.out::println);

    }
}
