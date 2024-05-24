package adoption.service;

import adoption.dao.repository.AdopterRepo;
import adoption.domain.Adopter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterRepoService implements BaseService<Adopter>{

    @Autowired
    private AdopterRepo adopterDAO;

    @Override
    public Adopter add(Adopter newObject) {
        return adopterDAO.save(newObject);
    }

    @Override
    public boolean delete(int id) {
        if(adopterDAO.existsById(id)){
            adopterDAO.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Adopter updateObject) {
        if(adopterDAO.existsById(updateObject.getId())){
            adopterDAO.save(updateObject);
            return true;
        }
        return false;
    }

    @Override
    public Adopter findById(int id) {
       return adopterDAO.findById(id).orElse(null);
    }

    @Override
    public List<Adopter> findAll() {
        return adopterDAO.findAllWithPets();
    }
}
