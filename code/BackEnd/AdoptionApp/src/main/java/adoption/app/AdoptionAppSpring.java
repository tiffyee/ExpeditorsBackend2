package adoption.app;

import adoption.domain.Adopter;
import adoption.jconfig.AppConfig;
import adoption.service.AdopterService;
import adoption.service.AdopterServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Comparator;
import java.util.List;

public class AdoptionAppSpring {

    public static void main(String[] args) {
        AdoptionAppSpring app = new AdoptionAppSpring();
        app.go();
    }

    public void go(){
        postAnAdopter();
        getAllAdopters();
        getSortedAdopterByName();
        getAdopterByName("Tiffany Yee");
//        List<Adopter> adopters = as.getAllAdopters();
//        System.out.println("Count of students: " + adopters.size());
//        System.out.println(adopters);
    }

    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    AdopterService as = context.getBean("adopterServiceImpl", AdopterServiceImpl.class);

    public void postAnAdopter() {
        Adopter adopter = new Adopter("John Doe", "123-123-1234");
        Adopter newAdopter = as.addAdopter(adopter);

        Adopter adopter2 = new Adopter("Jane Doe", "415-123-1234");
        as.addAdopter(adopter2);

        Adopter adopter3 = new Adopter("Tiffany Yee", "415-222-2222");
        as.addAdopter(adopter3);

        List<Adopter> adopters = as.getAllAdopters();
        System.out.println("# of adopters: " + adopters.size());
        adopters.forEach(System.out::println);
    }

    public void getAllAdopters() {
        List<Adopter> adopters = as.getAllAdopters();
        System.out.println("# of adopters: " + adopters.size());
        adopters.forEach(System.out::println);
    }

    public void getSortedAdopterByName(){
        List<Adopter> adopters = as.sortBy(Comparator.comparing(Adopter::getName));
        System.out.println("Sorted adopters by name:" + adopters.size());
        adopters.forEach(System.out::println);
    }

    public void getAdopterByName(String name){
        List<Adopter> adopters = as.findBy(adopter -> adopter.getName().equalsIgnoreCase(name));
        System.out.println("Found adopters:" + adopters.size());
        adopters.forEach(System.out::println);

    }


}
