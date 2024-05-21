package expeditors.backend.custapp;

import expeditors.backend.custapp.service.CustomerService;
import expeditors.backend.custapp.domain.Customer;
import expeditors.backend.custapp.jconfig.CustAppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author whynot
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

class OldApplication {

    public static void main(String[] args) {
        OldApplication app = new OldApplication();
        app.go();
    }

    public void go() {
//        ApplicationContext context = new AnnotationConfigApplicationContext(CustAppConfig.class);
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(CustAppConfig.class);
        context.scan("expeditors.backend.custapp.week6");  //redundant in this case
        context.refresh();

        CustomerService service = context.getBean("customerService", CustomerService.class);

        List<Customer> customers = service.getCustomers();

        System.out.println("customers: " + customers.size());
        customers.forEach(System.out::println);

    }
}

@Component
class MyRunner implements CommandLineRunner
{

    @Autowired
    private CustomerService customerservice;

    @Override
    public void run(String... args) throws Exception {
        List<Customer> customers = customerservice.getCustomers();

        System.out.println("customers: " + customers.size());
        customers.forEach(System.out::println);

    }
}
