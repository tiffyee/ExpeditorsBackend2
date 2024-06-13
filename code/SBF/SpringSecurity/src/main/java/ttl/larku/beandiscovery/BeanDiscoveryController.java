package ttl.larku.beandiscovery;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author whynot
 */
@RestController
@RequestMapping({"/discovery", "/discoveryrest"})
public class BeanDiscoveryController {

    private DependancyDiscoverer discoverer;
    public BeanDiscoveryController(DependancyDiscoverer discoverer) {
        this.discoverer = discoverer;
    }

    @GetMapping("/dependencies")
    public List<BeanNode> getDependencies(HttpServletRequest request, @RequestParam Map<String, String> queryStrings) {
        var result = queryStrings.keySet().stream()
                .flatMap(key -> discoverer.getDependencies(key).stream())
                .toList();

        var servletPath = request.getServletPath();
        var contextPath = request.getContextPath();
        var params = request.getQueryString();
        var user = request.getUserPrincipal();
        var isAdmin = request.isUserInRole("ROLE_ADMIN");

        System.out.println("servletPath: " + servletPath
                + ", contextPath: " + contextPath
                + ", params: " + params
                + ", user: " + user
                + ", isAdmin" + isAdmin
        );

        return result;
    }

    @GetMapping("/dependants")
    public List<BeanNode> getDependants(@RequestParam Map<String, String> queryStrings) {
        var result = queryStrings.keySet().stream()
                .flatMap(key -> discoverer.getDependants(key).stream())
                .toList();

        return result;
    }
}
