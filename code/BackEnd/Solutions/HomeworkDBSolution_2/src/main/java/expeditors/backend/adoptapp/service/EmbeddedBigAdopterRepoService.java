package expeditors.backend.adoptapp.service;

import expeditors.backend.adoptapp.dao.repository.EmbeddedBigARepo;
import expeditors.backend.adoptapp.domain.BigAdopter;
import expeditors.backend.adoptapp.domain.PetType;
import expeditors.backend.adoptapp.domain.embedded.BigAdopterEmbedded;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmbeddedBigAdopterRepoService {

    @Autowired
    private EmbeddedBigARepo adopterDAO;

    public BigAdopterEmbedded addAdopter(BigAdopterEmbedded adopter) {
        return adopterDAO.save(adopter);
    }

    public BigAdopterEmbedded getAdopter(int id) {
        return adopterDAO.findById(id).orElse(null);
    }

    public List<BigAdopterEmbedded> getAllAdopters() {
        return adopterDAO.findAll();
    }

    public boolean deleteAdopter(int id) {
        adopterDAO.deleteById(id);
        return true;
    }

    public boolean updateAdopter(BigAdopterEmbedded adopter) {
        adopterDAO.save(adopter);
        return true;
    }


    public List<BigAdopterEmbedded> getAdoptersByPetType(PetType type) {
//        List<BigAdopterEmbedded> result = adopterDAO
//                .findAll().stream()
//                .filter(a -> a.getPetType() == type)
//                .toList();
        List<BigAdopterEmbedded> result = List.of();
        return result;
    }
}
