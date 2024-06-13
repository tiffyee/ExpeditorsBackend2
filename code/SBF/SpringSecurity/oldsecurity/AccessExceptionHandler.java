package ttl.larku;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * To deal with 403 Forbidden Errors. 
 * 
 * We do it in a slightly round about way, using a little trick 
 * picked up from so.  We inject the standard 
 * HandlerExceptionResolver, and ask it find a handler for the Exception.
 * 
 * Nice thing about this is that we can write the Handler as
 * a standard Spring Exception Handler, which have we declare in 
 * ttl.larku.exceptions.LastStopHandler.  This gives us a nicer
 * interface to work with than the request and response that we 
 * get here.
 * @author whynot
 *
 */
@Component
public class AccessExceptionHandler implements AccessDeniedHandler {

    //This little puppy resolves the given exception to a Spring
    //@ExceptionHandler.  We have one in .../exceptions/LastStopHandler.java
    @Autowired(required = false)
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver resolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		resolver.resolveException(request, response, null, exception);
    }
}

