package expeditors.backend.adoptapp.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

/**
 * Some of these methods just declared here as @ExceptionHandlers.
 * 
 * Others are @Overrides of the ResponseEntityExceptionHandler to override
 * specific errors.
 *
 *
 * @author whynot
 *
 */
@Order(1)
//@ControllerAdvice
public class LastStopWebHandler {

    @ExceptionHandler(value = {OurAuthWebException.class})
    public ModelAndView accessDenied(OurAuthWebException ex, WebRequest request) {
        var mav = new ModelAndView("redirect:/error", "message", ex.getMessage());
        return mav;
    }
}
