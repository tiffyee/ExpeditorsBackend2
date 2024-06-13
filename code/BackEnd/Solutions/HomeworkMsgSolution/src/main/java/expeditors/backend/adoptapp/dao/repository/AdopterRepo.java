package expeditors.backend.adoptapp.dao.repository;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.OnlyAdopterDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdopterRepo extends JpaRepository<Adopter, Integer>,
      JpaSpecificationExecutor<Adopter> {

    @Query("select new expeditors.backend.adoptapp.domain.OnlyAdopterDTO(a.id, a.name, a.phoneNumber) from Adopter a")
    Page<OnlyAdopterDTO> findAllOnlyAdopterByPage(Pageable page);

    @Query("select new expeditors.backend.adoptapp.domain.OnlyAdopterDTO(a.id, a.name, a.phoneNumber) from Adopter a")
    List<OnlyAdopterDTO> findAllOnlyAdopter();

    @Query("select new expeditors.backend.adoptapp.domain.OnlyAdopterDTO(a.id, a.name, a.phoneNumber) from Adopter a")
    List<OnlyAdopterDTO> findAllOnlyAdopterWithSearhSpec(Specification<Adopter> spec);

    @Query("select new expeditors.backend.adoptapp.domain.OnlyAdopterDTO(a.id, a.name, a.phoneNumber) from Adopter a")
    Page<OnlyAdopterDTO> findAllOnlyAdopterWithSearhSpecAndPage(Specification<Adopter> spec, Pageable page);

    @Query("select a from Adopter a left join fetch a.pets order by a.id")
    List<Adopter> findAllWithPets();

    @Override
    @Query("select a from Adopter a left join fetch a.pets order by a.id")
    List<Adopter> findAll();

    @Query("select a from Adopter a left join fetch a.pets order by a.id")
    Page<Adopter> findAllForPaging(Pageable page);

    @Query("select a from Adopter a left join fetch a.pets order by a.id")
    Page<Adopter> findAll(Pageable page);

    @Query("select a from Adopter a left join fetch a.pets where a.id = :id")
    Adopter findByIdWithPets(@Param("id") int id);

//    @Override
//    @Query("select a from Adopter a left join fetch a.pets order by a.id")
//    List<Adopter> findAll();
//

}
