package adoption.service;

import adoption.dao.repository.AdopterRepo;
import adoption.domain.Adopter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterRepoService implements BaseService<Adopter>{

    @Autowired
    private AdopterRepo adopterRepo;

    @Override
    public Adopter add(Adopter newObject) {
        return adopterRepo.save(newObject);
    }

    @Override
    public boolean delete(int id) {
        if(adopterRepo.existsById(id)){
            adopterRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Adopter updateObject) {
        if(adopterRepo.existsById(updateObject.getId())){
            adopterRepo.save(updateObject);
            return true;
        }
        return false;
    }

    @Override
    public Adopter findById(int id) {
       return adopterRepo.findById(id).orElse(null);
    }

    @Override
    public List<Adopter> findAll() {
        return adopterRepo.findAllWithPets();
    }
}
