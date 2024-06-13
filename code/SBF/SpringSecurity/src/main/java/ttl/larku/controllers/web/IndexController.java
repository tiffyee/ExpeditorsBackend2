package ttl.larku.controllers.web;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "redirect:/admin";
    }

    @GetMapping({"/error", "/eek"})
    public ModelAndView error(@RequestParam(name = "message",
          required = false) String message) {
        return new ModelAndView("realerror", "message", message);
    }

    @GetMapping("/adminPage")
    public String adminPage(Authentication authentication) {
        return "adminPage";
    }

    @GetMapping("/admin")
    public String startPage(Authentication authentication) {
        return "startPage";
    }
}
