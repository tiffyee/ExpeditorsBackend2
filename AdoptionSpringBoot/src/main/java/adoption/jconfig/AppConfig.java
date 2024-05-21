package adoption.jconfig;

import adoption.dao.AdopterDAO;
import adoption.dao.BaseDAO;
import adoption.domain.Adopter;
import adoption.service.AdopterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"adoption"})
public class AppConfig {


//    @Bean
//    public BaseDAO<Adopter> adopterDAO(){
//        var dao = DAOFactory.adopterDAO();
//        return dao;
//    }

    @Bean
    public AdopterService adopterService(BaseDAO<Adopter> adopterDAO){
        AdopterService as = new AdopterService();
        BaseDAO<Adopter> dao = adopterDAO;
        as.setAdopterDAO(adopterDAO);
        return as;
    }

}
