package adoption.dao;

import adoption.dao.AdopterDAO;
import adoption.dao.DAOFactory;
import org.junit.jupiter.api.Test;
import adoption.service.AdopterService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class DaoFactoryTest {
    @Test
    public void testDAOFactoryInstances(){
        AdopterDAO dao = DAOFactory.adopterDAO();
        assertNotNull(dao);
        AdopterDAO dao2 = DAOFactory.adopterDAO();
        assertSame(dao,dao2);
    }

    @Test
    public void testDAOFactoryAdopterServiceInstance(){
        final AdopterService service1 = DAOFactory.adopterService();
        final AdopterService service2 = DAOFactory.adopterService();
        assertSame(service1,service2);
        assertSame(service1.getAdopterDAO(),service2.getAdopterDAO());
    }
}
