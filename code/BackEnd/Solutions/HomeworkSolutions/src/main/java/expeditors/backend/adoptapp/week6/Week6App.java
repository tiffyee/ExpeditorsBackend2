package expeditors.backend.adoptapp.week6;

import expeditors.backend.adoptapp.week6.domain.Adopter;
import expeditors.backend.adoptapp.week6.domain.Pet;
import expeditors.backend.adoptapp.week6.service.AdopterService;
import expeditors.backend.custapp.week6.jconfig.CustAppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.List;

import static java.lang.System.out;

public class Week6App {

    public static void main(String[] args) {
        Week6App week6App = new Week6App();
        week6App.go();
    }

    public void go() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev");
        context.register(CustAppConfig.class);
        context.scan("expeditors.backend.adoptapp.week6");  //redundant in this case
        context.refresh();

        AdopterService adopterService = context.getBean("adopterService", AdopterService.class);

        Adopter adopter = new Adopter("Joey", "383 9999 9393", LocalDate.of(1960, 6, 9),
                Pet.builder(Pet.PetType.DOG).name("woofie").build());
        adopterService.addAdopter(adopter);

        List<Adopter> result = adopterService.getAllAdopters();
        out.println("result: " + result.size());
        result.forEach(out::println);
    }
}
