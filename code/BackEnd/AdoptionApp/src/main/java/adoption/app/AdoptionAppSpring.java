package adoption.app;

import adoption.domain.Adopter;
import adoption.jconfig.AppConfig;
import adoption.service.AdopterService;
import adoption.service.AdopterServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Comparator;
import java.util.List;

public class AdoptionAppSpring {

    public static void main(String[] args) {
        AdoptionAppSpring app = new AdoptionAppSpring();
        app.go();
    }

    public void go(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(AppConfig.class);
        context.scan("adoption");
        context.refresh();

        AdopterService as = context.getBean("adopterServiceImpl", AdopterServiceImpl.class);

        Adopter adopter = new Adopter("John Doe", "123-123-1234");
        Adopter newAdopter = as.addAdopter(adopter);

        Adopter adopter2 = new Adopter("Jane Doe", "415-123-1234");
        as.addAdopter(adopter2);

        Adopter adopter3 = new Adopter("Tiffany Yee", "415-222-2222");
        as.addAdopter(adopter3);

        List<Adopter> adopters = as.getAllAdopters();
        System.out.println("# of adopters: " + adopters.size());
        adopters.forEach(System.out::println);

        List<Adopter> sortedAdopters = as.sortBy(Comparator.comparing(Adopter::getName));
        System.out.println("Sorted adopters by name:" + sortedAdopters.size());
        sortedAdopters.forEach(System.out::println);

        List<Adopter> findAdopters = as.findBy(a -> a.getName().equalsIgnoreCase("Tiffany Yee"));
        System.out.println("Found adopters:" + findAdopters.size());
        findAdopters.forEach(System.out::println);
    }



}
