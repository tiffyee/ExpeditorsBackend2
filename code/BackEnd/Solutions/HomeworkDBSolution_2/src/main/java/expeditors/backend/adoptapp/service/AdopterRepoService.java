package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.dao.repository.AdopterRepo;
import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.Pet;
import expeditors.backend.adoptapp.domain.PetType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdopterRepoService {

    @Autowired
    private AdopterRepo adopterDAO;

    public Adopter addAdopter(Adopter adopter) {
        return adopterDAO.save(adopter);
    }

    public Adopter getAdopter(int id) {
        return adopterDAO.findByIdWithPets(id);
    }

    public List<Adopter> getAllAdopters() {
        return adopterDAO.findAllWithPets();
    }

    public boolean deleteAdopter(int id) {
        if(adopterDAO.existsById(id)) {
            adopterDAO.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean updateAdopter(Adopter adopter) {
        if(adopterDAO.existsById(adopter.getId())) {
            adopterDAO.save(adopter);
            return true;
        }
        return false;
    }


    public List<Adopter> getAdoptersByPetType(PetType type) {
//        List<Adopter> result = adopterDAO
//                .findAll().stream()
//                .filter(a -> a.getPets().stream().anyMatch(p -> p.getType()  == type))
//                .toList();
        List<Adopter> result = adopterDAO.findByPetType(type);
        return result;
    }

    public boolean addPetToAdopter(int adopterId, Pet newPet) {
        var adopter = adopterDAO.findByIdWithPets(adopterId);
        if(adopter != null) {
            adopter.addPet(newPet);
            return true;
        }

        return false;
    }

    public boolean removePetFromAdopter(int adopterId, int petId) {
        var adopter = adopterDAO.findByIdWithPets(adopterId);
        if(adopter != null) {
            var pet = adopter.getPets().stream()
                  .filter(p -> p.getPetId() == petId)
                  .findFirst();
            if (pet.isPresent()) {
                adopter.removePet(pet.get());
                return true;
            }
        }
        return false;
    }
}
