package expeditors.backend.custapp.week4.service;

import expeditors.backend.custapp.week4.dao.CustomerDAO;
import expeditors.backend.custapp.week4.dao.DAOFactory;
import expeditors.backend.custapp.week4.dao.InMemoryCustomerDAO;
import expeditors.backend.custapp.week4.domain.Customer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author whynot
 */
public class CustomerService {

    private final int MIN_AGE = 18;

    private final CustomerDAO customerDAO = DAOFactory.customerDAO(); //new InMemoryCustomerDAO();

    public Customer addCustomer(Customer c) {
        //Maybe do some validation on customer
        //Must be older than 18
        long age = c.getDob().until(LocalDate.now(), ChronoUnit.YEARS);
        if(age < 18) {
            throw new RuntimeException("Customer age must be at least " + MIN_AGE);
        }
        return customerDAO.insert(c);

    }

    public boolean deleteCustomer(int id) {
        return customerDAO.delete(id);
        //delete customer with id
    }

    public boolean updateCustomer(Customer c) {
        return customerDAO.update(c);

    }

    public Customer getCustomer(int id) {
        return customerDAO.get(id);
    }

    public List<Customer> getCustomers() {
        return customerDAO.getAll();
    }
}
