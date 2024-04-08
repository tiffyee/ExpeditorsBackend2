package expeditors.backend.custapp.week4.dao;

import expeditors.backend.custapp.week4.domain.Customer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whynot
 */
public class JPACustomerDAO implements CustomerDAO {

    private Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    public Customer insert(Customer c) {
        int newId = nextId.getAndIncrement();
        c.setId(newId);
        c.setName(c.getName() + " from JPADAO");
        customers.put(newId, c);

        return c;
    }

    public boolean delete(int id) {
       return customers.remove(id) != null;
    }

    public boolean update(Customer c) {
       return customers.replace(c.getId(), c) != null;
    }

    public Customer get(int id) {
       return customers.get(id);
    }

    public List<Customer> getAll() {
       return List.copyOf(customers.values());
    }
}
