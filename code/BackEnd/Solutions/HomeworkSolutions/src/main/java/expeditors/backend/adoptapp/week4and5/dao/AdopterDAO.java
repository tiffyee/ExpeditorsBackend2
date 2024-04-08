package expeditors.backend.adoptapp.week4and5.dao;

import expeditors.backend.adoptapp.week4and5.domain.Adopter;

import java.util.List;

public interface AdopterDAO {
    Adopter insert(Adopter newAdopter);

    boolean delete(int id);

    boolean update(Adopter adopter);

    Adopter findById(int id);

    List<Adopter> findAll();
}
