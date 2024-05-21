package adoption.dao.inmem;

import adoption.dao.BaseDAO;
import adoption.domain.Pet;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("devPet")
public class InMemoryPetDAO implements BaseDAO<Pet> {

    private Map<Integer,Pet> pets = new HashMap<>();
    private int nextId = 1;

    @Override
    public Pet insert(Pet pet){
        int id = nextId++;
        pet.setPetId(id);
        pet.setName(pet.getName());

        pets.put(pet.getPetId(),pet);
        return pet;
    }

    @Override
    public boolean delete(int id){
        return pets.remove(id) != null;
    }

    @Override
    public boolean update(Pet pet){
        return pets.replace(pet.getPetId(), pet) != null;
    }

    @Override
    public Pet findById(int id){
        return pets.get(id);
    }

    @Override
    public List<Pet> findAll(){
        return new ArrayList<>(pets.values());
    }
}
