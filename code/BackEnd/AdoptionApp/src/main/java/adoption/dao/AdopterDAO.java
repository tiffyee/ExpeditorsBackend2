package adoption.dao;

import adoption.domain.Adopter;

import java.util.List;

public interface AdopterDAO {
    Adopter insert(Adopter adopter);

    boolean delete(int id);

    boolean update(Adopter adopter);

    Adopter findByID(int id);

    List<Adopter> findAll();
}
