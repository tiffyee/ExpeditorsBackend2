package adoption.dao;

import adoption.service.AdopterServiceImpl;

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
    public static AdopterDAO adopterDAO(){

        return switch (profile){
            case "dev" -> (AdopterDAO)objects.computeIfAbsent("adopterDAO", k -> new InMemoryAdopterDAO());
            case "prod" -> (AdopterDAO)objects.computeIfAbsent("adopterDAO", k -> new OtherDAO());
            default ->  throw new RuntimeException("Unknown profile: " + profile);
        };

    }

    public static AdopterServiceImpl adopterService() {
        AdopterServiceImpl service =
                (AdopterServiceImpl) objects.computeIfAbsent("adopterService",
                        k -> {
                            AdopterServiceImpl as = new AdopterServiceImpl();
                            AdopterDAO dao = adopterDAO();
                            as.setAdopterDAO(dao);
                            return as;
                        });
        return service;
    }

}
