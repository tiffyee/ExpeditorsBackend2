package expeditors.backend.custapp.week4.dao;

import expeditors.backend.custapp.week4.domain.Customer;

import java.util.List;

/**
 * @author whynot
 */
public interface CustomerDAO {
    Customer insert(Customer c);

    boolean delete(int id);

    boolean update(Customer c);

    Customer get(int id);

    List<Customer> getAll();
}
