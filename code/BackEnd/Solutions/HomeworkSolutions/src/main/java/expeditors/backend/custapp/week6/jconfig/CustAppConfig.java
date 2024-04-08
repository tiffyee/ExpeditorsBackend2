package expeditors.backend.custapp.week6.jconfig;

import expeditors.backend.custapp.week6.dao.CustomerDAO;
import expeditors.backend.custapp.week6.dao.InMemoryCustomerDAO;
import expeditors.backend.custapp.week6.dao.JPACustomerDAO;
import expeditors.backend.custapp.week6.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author whynot
 */
@Configuration
public class CustAppConfig {

    @Bean
    @Profile("dev")
    public CustomerDAO customerDAO() {
       CustomerDAO dao = new InMemoryCustomerDAO();
       return dao;
    }

    @Bean(name = "customerDAO")
    @Profile("prod")
    public CustomerDAO jpaCustomerDAO() {
        CustomerDAO dao = new JPACustomerDAO();
        return dao;
    }

    @Bean
    public CustomerService customerService() {
        CustomerService service = new CustomerService();

        CustomerDAO dao = customerDAO();
        service.setCustomerDAO(dao);

        return service;
    }
}
