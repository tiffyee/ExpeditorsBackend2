package expeditors.backend.adoptapp.week6.service;

import expeditors.backend.adoptapp.week6.dao.AdopterDAO;
import expeditors.backend.adoptapp.week6.domain.Adopter;
import expeditors.backend.adoptapp.week6.domain.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterService {

    @Autowired
    private AdopterDAO adopterDAO;

    public Adopter addAdopter(Adopter adopter) {
        return adopterDAO.insert(adopter);
    }

    public Adopter getAdopter(int id) {
        return adopterDAO.findById(id);
    }

    public List<Adopter> getAllAdopters() {
        return adopterDAO.findAll();
    }

    public boolean deleteAdopter(int id) {
        return adopterDAO.delete(id);
    }

    public boolean updateAdopter(Adopter adopter) {
        return adopterDAO.update(adopter);
    }

    public void setAdopterDAO(AdopterDAO adopterDAO) {
        this.adopterDAO = adopterDAO;
    }

    public List<Adopter> getAdoptersByPetType(Pet.PetType type) {
        List<Adopter> result = adopterDAO
                .findAll().stream()
                .filter(a -> a.getPet().getType() == type)
                .toList();
        return result;
    }
}
