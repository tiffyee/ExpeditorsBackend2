package expeditors.backend.adoptapp.week4and5.dao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDAOFactory {

    @Test
    public void testDAOFactoryGivesSingletonDAOs() {
        var first = DAOFactory.adopterDAO();
        var second = DAOFactory.adopterDAO();

        assertSame(first, second);
    }

    @Test
    public void testDAOFactoryGivesSingletonServices() {
        var first = DAOFactory.adopterService();
        var second = DAOFactory.adopterService();

        assertSame(first, second);
    }
}
