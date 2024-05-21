package adoption.service;

import adoption.dao.BaseDAO;
import adoption.domain.Adopter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AdopterService {

    @Autowired
    private BaseDAO<Adopter> adopterDAO;

    public Adopter addAdopter(Adopter adopter){
        return adopterDAO.insert(adopter);
    }

    public boolean deleteAdopter(int id){
        return adopterDAO.delete(id);
    }

    public boolean updateAdopter(Adopter adopter) {
        return adopterDAO.update(adopter);
    }

    public Adopter findById(int id){
        return adopterDAO.findById(id);
    }

    public List<Adopter> getAllAdopters(){
        return adopterDAO.findAll();
    }

    public BaseDAO<Adopter> getAdopterDAO() {
        return adopterDAO;
    }

    public void setAdopterDAO(BaseDAO<Adopter> adopterDAO) {
        this.adopterDAO = adopterDAO;
    }

    public List<Adopter> sortBy(Comparator<Adopter> adopterComparator){
        return adopterDAO.findAll().stream().sorted(adopterComparator).toList();
    }

    public List<Adopter> findBy(Predicate<Adopter> adopterPredicate){
        return adopterDAO.findAll().stream().filter(adopterPredicate).toList();
    }
}



