package adoption.dao;

import adoption.domain.Adopter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryAdopterDAO implements AdopterDAO {

    private Map<Integer,Adopter> adopters = new HashMap<>();
    private int nextId = 1;

    @Override
    public Adopter insert(Adopter adopter){
        int id = nextId++;
        adopter.setId(id);
        adopter.setName(adopter.getName());

        adopters.put(adopter.getId(),adopter);
        return adopter;
    }

    @Override
    public boolean delete(int id){
        return adopters.remove(id) != null;
    }

    @Override
    public boolean update(Adopter adopter){
        return adopters.replace(adopter.getId(), adopter) != null;
    }

    @Override
    public Adopter findByID(int id){
        return adopters.get(id);
    }

    @Override
    public List<Adopter> findAll(){
        return new ArrayList<>(adopters.values());
    }
}
