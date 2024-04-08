package expeditors.backend.custapp.week4.dao;

import expeditors.backend.custapp.week4.domain.Customer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author whynot
 */
public class InMemoryCustomerDAO implements CustomerDAO {

    private Map<Integer, Customer> customers = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Customer insert(Customer c) {
        int newId = nextId.getAndIncrement();
        c.setId(newId);
        customers.put(newId, c);

        return c;
    }

    @Override
    public boolean delete(int id) {
       return customers.remove(id) != null;
    }

    @Override
    public boolean update(Customer c) {
       return customers.replace(c.getId(), c) != null;
    }

    @Override
    public Customer get(int id) {
       return customers.get(id);
    }

    @Override
    public List<Customer> getAll() {
       return List.copyOf(customers.values());
    }
}
