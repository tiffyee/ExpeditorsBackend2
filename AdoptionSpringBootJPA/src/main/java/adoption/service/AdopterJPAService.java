package adoption.service;

import adoption.domain.Adopter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AdopterJPAService implements BaseService<Adopter>{
    String pw = System.getenv("DB_PASSWORD");

    Map<String, String> props = Map.of(
            //"jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5432/larku",
            "jakarta.persistence.jdbc.url", "jdbc:postgresql://localhost:5433/adoptapp",
            "jakarta.persistence.jdbc.user", "larku",
            "jakarta.persistence.jdbc.password", pw,
            "hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect",

            "jakarta.persistence.spi.PersistenceProvider", "org.hibernate.jpa.HibernatePersistenceProvider"
    );

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Adoption_PU",props);
//    private EntityManager em = emf.createEntityManager();
//    private EntityTransaction tx = em.getTransaction();

    @Override
    public Adopter add(Adopter newObject) {
        try (EntityManager manager = emf.createEntityManager()){
            Adopter adopter = new Adopter();
            adopter.setId(newObject.getId());
            adopter.setName(newObject.getName());
            adopter.setPhoneNumber(newObject.getPhoneNumber());

            manager.getTransaction().begin();
            manager.persist(adopter);
            manager.getTransaction().commit();

            System.out.println(adopter);
        return adopter;
        }
    }

    @Override
    public boolean delete(int id) {
        try (EntityManager manager = emf.createEntityManager()){
            Adopter adopter = manager.find(Adopter.class, id);
            if (adopter!=null){
                manager.getTransaction().begin();
                manager.remove(adopter);
                manager.getTransaction().commit();
            }
            //need to implement return
            return true;
        }
    }

    @Override
    public boolean update(Adopter updateObject) {
        try (EntityManager manager = emf.createEntityManager()){
            //need to implement update
            return false;
        }
    }

    @Override
    public Adopter findById(int id) {
        try (EntityManager manager = emf.createEntityManager()){
            Adopter adopter = manager.find(Adopter.class, id);
            System.out.println(adopter);
            return adopter;
        }
    }

    @Override
    public List<Adopter> findAll() {
        try (EntityManager manager = emf.createEntityManager()){
            TypedQuery<Adopter> query = manager.createQuery("select a from Adopter a", Adopter.class);
            List<Adopter> adopters = query .getResultList();

            System.out.println("num Adopters: " + adopters.size());
            adopters.forEach(System.out::println);

            return adopters;

        }
    }
}
