package expeditors.backend.controller;

import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class UriCreator {

   public URI getURI(int id) {
      URI newResource = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(id)
            .toUri();

      return newResource;
   }
}
