package adoption.dao.repository;

import adoption.domain.Adopter;
import adoption.domain.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdopterRepo extends JpaRepository<Adopter,Integer> {

    @Query("select a from Adopter a left join fetch a.pets")
    List<Adopter> findAllWithPets();

    @Query("select a from Adopter a left join fetch a.pets where a.id = :id")
    Adopter findByIdWithPets(@Param("id") int id);

    @Query("select a from Adopter a left join a.pets p where p.type = :petType")
    List<Adopter> findByPetType(@Param("petType") Pet.PetType petType);

    @Query("select a.name, p.name from Adopter a join a.pets p")
    List<Object[]> findAdopterNameAndPetName();

}

