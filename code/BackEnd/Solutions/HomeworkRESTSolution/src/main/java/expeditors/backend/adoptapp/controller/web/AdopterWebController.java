package expeditors.backend.adoptapp.controller.web;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.domain.PetType;
import expeditors.backend.adoptapp.service.AdopterService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdopterWebController {

   @Autowired
   private AdopterService adopterService;

   @GetMapping ("/pets")
   public ModelAndView getAllAdoptersAndPets() {
      List<Adopter> adopters = adopterService.getAllAdopters();
      ModelAndView mav = new ModelAndView("showAdopters", "adopters", adopters);
      return mav;
   }

   @GetMapping ("/pets/showAdopter")
   public ModelAndView getAdopterWithPets(@RequestParam("adopterId") int adopterId) {
      Adopter adopter = adopterService.getAdopter(adopterId);
      ModelAndView mav = new ModelAndView("showAdopter", "adopter", adopter);
      return mav;
   }

   @GetMapping("/pets/addAdopter")
   public ModelAndView addAdopterShowForm() {
      Adopter blankAdopter = new Adopter();
      PetType[] petTypes = PetType.values();
      var params = Map.of(
            "petTypes", petTypes,
            "adopter", blankAdopter);
      ModelAndView mav = new ModelAndView("addAdopter", params);
      return mav;
   }

   @PostMapping("/pets/addAdopter")
   public ModelAndView addAdopter(Adopter adopter) {
      Adopter addedAdopter = adopterService.addAdopter(adopter);
      ModelAndView mav = new ModelAndView("showAdopter", "adopter", addedAdopter);
      return mav;
   }
}
