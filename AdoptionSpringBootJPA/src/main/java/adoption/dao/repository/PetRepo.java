package adoption.dao.repository;

import adoption.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface  PetRepo extends JpaRepository<Pet,Integer> {

    @Query(value = "select * from pet p", nativeQuery = true)
    List<Pet> run();
}
