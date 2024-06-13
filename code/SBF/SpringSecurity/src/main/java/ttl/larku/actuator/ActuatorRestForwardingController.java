package ttl.larku.actuator;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Simple Trick to allow both Web App and REST access to the
 * Spring actuators.
 * The problem lies with Security.  The REST api is secured
 * with Basic Authentication and the Web front end has Form Login
 * and HttpSessions etc.  They need different URLs for Spring Security.
 * But the actuator only allows a single root endpoint, e.g. /actuator.
 *
 * So, in this Controller we simply forward the request
 * from '/actuatorrest' to '/actuator'.
 *
 * At this point we are past the security checks, so the
 * request goes through to '/actuator' via either path.
 *
 * Seems to work.
 * @author whynot
 */
@Controller
@RequestMapping("/actuatorrest/**")
public class ActuatorRestForwardingController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String forwardActuatorGetPost(HttpServletRequest httpRequest) {
        return doIt(httpRequest);
    }

    public String forwardActuatorGet(HttpServletRequest httpRequest, @RequestParam Map<String, String> rqp) {
        return doIt(httpRequest);
    }

    public String doIt(HttpServletRequest httpRequest) {
        String uri = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String pathInfo = httpRequest.getPathInfo();

        String servletPath = httpRequest.getServletPath();
        String params = httpRequest.getQueryString();

        String newPath = servletPath.replace("/actuatorrest", "/actuator");
        if(params != null && params.length() > 0) {
            newPath += "?" + params;
        }
        logger.debug("Forwarding {} to {}", servletPath, newPath);
        return "forward:" + newPath;
    }
}