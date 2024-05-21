package expeditors.backend.custapp.dao.repo;

import expeditors.backend.custapp.domain.Customer;
import expeditors.backend.custapp.domain.CustomerDTO;
import expeditors.backend.sql.ScriptFileProperties;
import jakarta.persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author whynot
 */

//@SpringBootTest
@DataJpaTest(properties = {"spring.profiles.include=jpqltester"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({ScriptFileProperties.class})
public class JPQLTester {
    @Autowired
    private CustomerRepo customerDAO;
    
    @PersistenceUnit
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ScriptFileProperties props;

    @Autowired
    private ApplicationContext context;

    @BeforeEach
    public void init() throws SQLException {
//        customerDAO = new JPACustomerDAO();
        try (Connection conn = dataSource.getConnection()) {
            props.getAllSchemaLocs().forEach(schemaFile -> {
                System.err.println("Running schemaFile: " + schemaFile);
                ScriptUtils.executeSqlScript(conn, context.getResource(schemaFile));
            });

            props.getAllDataLocs().forEach(dataFile -> {
                System.err.println("Running dataFile: " + dataFile);
                ScriptUtils.executeSqlScript(conn, context.getResource(dataFile));
            });
        }
    }

    @Test
    public void testJPQLSimple() {

        String jpqlStr = "select c from Customer c where id = :id";

        TypedQuery<Customer> query = em.createQuery(jpqlStr, Customer.class);
        query.setParameter("id", 1);

        Customer cust = query.getSingleResult();
        System.out.println("cust: " + cust);
    }

    @Test
    public void testJPQLLessSimple() {
        //Fetch Customers who match a particular Phone Number
        //We do this with a subselect:
        //  Select customers and their phones for all
        //    customers where they have a phone which matches the one we
        //    are looking for.

        String phoneNumber = "222 333-5555";
        String jpqlStr = " select " +
                "new expeditors.backend.custapp.domain.CustomerDTO(c.id, " +
                "c.name, c.dob, c.status) " +
                "from Customer c ";

        List<CustomerDTO> result =
                doQuery(CustomerDTO.class, jpqlStr);

        result.forEach(System.out::println);
    }


    private <T> List<T> doQuery(Class<T> clazz, String jpqlStr, String ...params) {
        TypedQuery<T> query = em.createQuery(jpqlStr, clazz);

        for(int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        List<T> result = query.getResultList();

        return result;
    }
}
