package expeditors.backend.adoptapp.week4and5.service;

import expeditors.backend.adoptapp.week4and5.dao.AdopterDAO;
import expeditors.backend.adoptapp.week4and5.domain.Adopter;
import expeditors.backend.adoptapp.week4and5.domain.Pet;

import java.util.List;

public class AdopterService {

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
