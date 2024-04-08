package adoption.jconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"adoption"})
public class AppConfig {


//    @Bean
//    public AdopterDAO adopterDAO(){
//        var dao = DAOFactory.adopterDAO();
//        return dao;
//    }

//    @Bean
//    public AdopterService adopterService(){
//        AdopterService as = new AdopterServiceImpl();
//        AdopterDAO dao = adopterDAO();
//        as.setAdopterDAO(dao);
//        return as;
//    }

}
