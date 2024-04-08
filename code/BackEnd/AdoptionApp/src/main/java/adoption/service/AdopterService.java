package adoption.service;

import adoption.domain.Adopter;
import adoption.dao.AdopterDAO;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface AdopterService {
    Adopter addAdopter(Adopter adopter);

    boolean deleteAdopter(int id);

    boolean updateAdopter(Adopter adopter);

    Adopter findById(int id);

    List<Adopter> getAllAdopters();

    AdopterDAO getAdopterDAO();

    void setAdopterDAO(AdopterDAO adopterDAO);

    List<Adopter> sortBy(Comparator<Adopter> adopterComparator);

    List<Adopter> findBy(Predicate<Adopter> adopterPredicate);
}
