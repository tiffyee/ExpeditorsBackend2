package adoption.dao;

import adoption.dao.inmem.InMemoryAdopterDAO;
import adoption.domain.Adopter;
import adoption.service.AdopterService;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class DAOFactory {

    private static Map<String, Object> objects = new ConcurrentHashMap<>();
    private static String profile;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("adopt");
        profile = bundle.getString("adopt.profile");
    }
    public static BaseDAO<Adopter> adopterDAO(){

        return switch (profile){
            case "dev" -> (BaseDAO<Adopter>)objects.computeIfAbsent("adopterDAO", k -> new InMemoryAdopterDAO());
            case "prod" -> (BaseDAO<Adopter>)objects.computeIfAbsent("adopterDAO", k -> new OtherDAO());
            default ->  throw new RuntimeException("Unknown profile: " + profile);
        };

    }

    public static AdopterService adopterService() {
        AdopterService service =
                (AdopterService) objects.computeIfAbsent("adopterService",
                        k -> {
                            AdopterService as = new AdopterService();
                            BaseDAO<Adopter> dao = adopterDAO();
                            as.setAdopterDAO(dao);
                            return as;
                        });
        return service;
    }

}
