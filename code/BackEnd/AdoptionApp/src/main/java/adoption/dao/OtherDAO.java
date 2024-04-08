package adoption.dao;

import adoption.domain.Adopter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OtherDAO implements AdopterDAO {

    private Map<Integer, Adopter> adopters = new HashMap<>();
    private int nextId = 1;

    public Adopter insert(Adopter adopter){
        int id = nextId++;
        adopter.setId(id);
        adopter.setName("OtherDAO: " + adopter.getName());

        adopters.put(adopter.getId(),adopter);
        return adopter;
    }

    public boolean delete(int id){
        return adopters.remove(id) != null;
    }

    public boolean update(Adopter adopter){
        return adopters.replace(adopter.getId(), adopter) != null;
    }

    public Adopter findByID(int id){
        return adopters.get(id);
    }

    public List<Adopter> findAll(){
        return new ArrayList<>(adopters.values());
    }
}
