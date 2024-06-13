package ttl.larku.controllers.rest;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Component
public class BeanHelpers {
	
	public URI getUriFor(int id) {
		
        URI newResource = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
		
        return newResource;
	}

    public SecurityContext getSecurityContext() {
	    return SecurityContextHolder.getContext();
    }
}
