package ttl.larku.exceptions;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ttl.larku.controllers.rest.RestResultWrapper;

@Component
public class WebExceptionHandler {

   @ExceptionHandler(value = {Exception.class})
   protected RestResultWrapper<?> lastPortOfCall(Exception ex, WebRequest request) {
      ex.printStackTrace();
      RestResultWrapper<String> rr = RestResultWrapper.ofError("Unexpected Exception: " + ex);
      return rr;
   }

}
