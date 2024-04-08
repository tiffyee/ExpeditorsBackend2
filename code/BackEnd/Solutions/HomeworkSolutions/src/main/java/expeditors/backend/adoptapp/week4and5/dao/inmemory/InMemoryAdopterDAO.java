package expeditors.backend.adoptapp.week4and5.dao.inmemory;

import expeditors.backend.adoptapp.week4and5.dao.AdopterDAO;
import expeditors.backend.adoptapp.week4and5.domain.Adopter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAdopterDAO implements AdopterDAO {

    private Map<Integer, Adopter> adopters = new ConcurrentHashMap<>();
    private AtomicInteger nextId = new AtomicInteger(1);

    public Adopter insert(Adopter newAdopter) {
        newAdopter.setId(nextId.getAndIncrement());
        newAdopter.setName("InMem: " + newAdopter.getName());
        adopters.put(newAdopter.getId(), newAdopter);

        return newAdopter;
    }

    public boolean delete(int id) {
        return adopters.remove(id) != null;
    }

    public boolean update(Adopter adopter) {
        return adopters.replace(adopter.getId(), adopter) != null;
    }

    public Adopter findById(int id) {
        return adopters.get(id);
    }

    public List<Adopter> findAll() {
        return new ArrayList(adopters.values());
    }
}
