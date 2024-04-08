package expeditors.backend.adoptapp.week6.dao;

import expeditors.backend.adoptapp.week6.domain.Adopter;

import java.util.List;

public interface AdopterDAO {
    Adopter insert(Adopter newAdopter);

    boolean delete(int id);

    boolean update(Adopter adopter);

    Adopter findById(int id);

    List<Adopter> findAll();
}
