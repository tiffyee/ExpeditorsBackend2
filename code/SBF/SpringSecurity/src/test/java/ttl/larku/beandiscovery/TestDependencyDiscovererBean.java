package ttl.larku.beandiscovery;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author whynot
 */
@SpringBootTest
public class TestDependencyDiscovererBean {

    @Autowired
    private DependancyDiscoverer dd;

    @Autowired
    private ObjectMapper mapper;

    private ObjectWriter writer;

    @PostConstruct
    private void uberInit() {
        writer = mapper.writerWithDefaultPrettyPrinter();
    }

    @Test
    public void testDependenciesWithDDBean() {
       List<BeanNode> dependencies = dd.getDependencies("org.springframework.security");
       dependencies.forEach(node -> {
           System.out.println("***************************************");
           System.out.println("Dependencies for Bean: " + node.getBeanName() + " : " + node.getClazz());
           dd.printBeanNode(node, "   ");
       });
    }

    @Test
    public void testDependantsWithDDBean() {
        List<BeanNode> dependants = dd.getDependants("org.springframework.security");
        dependants.forEach(node -> {
            System.out.println("***************************************");
            System.out.println("Beans Dependant on: " + node.getBeanName() + " : " + node.getClazz());
            dd.printBeanNode(node, "   ");
//            try {
//                //System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node));
//                System.out.println(writer.writeValueAsString(node));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
        });
    }

    @Test
    public void testGetBoth() {
        String searchExpression = "ttl.larku";
        List<BeanNode> dependants = dd.getDependants(searchExpression);
        List<BeanNode> dependencies = dd.getDependencies(searchExpression);

        dependants.forEach(node -> {
            System.out.println("***************************************");
            System.out.println("Dependency Tree for : " + node.getBeanName() + " : " + node.getClazz());
            dd.printBeanNode(node, "   ");
        });


    }
}
