package adoption.service;

import adoption.dao.AdopterDAO;
import adoption.dao.DAOFactory;
import adoption.domain.Adopter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

@Service
public class AdopterServiceImpl implements AdopterService {
    @Autowired
    private AdopterDAO adopterDAO;

    public AdopterServiceImpl(){
        //adopterDAO = new InMemoryAdopterDAO();
        //adopterDAO = new OtherDAO();
        adopterDAO = DAOFactory.adopterDAO();
    }

    @Override
    public Adopter addAdopter(Adopter adopter){
        return adopterDAO.insert(adopter);
    }

    @Override
    public boolean deleteAdopter(int id){
        return adopterDAO.delete(id);
    }

    @Override
    public boolean updateAdopter(Adopter adopter) {
        return adopterDAO.update(adopter);
    }

    @Override
    public Adopter findById(int id){
        return adopterDAO.findByID(id);
    }

    @Override
    public List<Adopter> getAllAdopters(){
        return adopterDAO.findAll();
    }

    @Override
    public AdopterDAO getAdopterDAO() {
        return adopterDAO;
    }

    @Override
    public void setAdopterDAO(AdopterDAO adopterDAO) {
        this.adopterDAO = adopterDAO;
    }

    @Override
    public List<Adopter> sortBy(Comparator<Adopter> adopterComparator){
        return adopterDAO.findAll().stream().sorted(adopterComparator).toList();
    }
    @Override
    public List<Adopter> findBy(Predicate<Adopter> adopterPredicate){
        return adopterDAO.findAll().stream().filter(adopterPredicate).toList();
    }
}



