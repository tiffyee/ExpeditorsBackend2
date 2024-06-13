package ttl.mie.controller.web;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ttl.mie.domain.track.entity.TrackEntity;
import ttl.mie.jconfig.ExtraStuffBean;
import ttl.mie.service.TrackRepoService;

@Controller
public class TrackWebController {

   @Autowired
   private TrackRepoService trackService;

   @GetMapping ("/tracks")
   public ModelAndView getAllTracks() {
      List<TrackEntity> tracks = trackService.getAllTracks();
      ModelAndView mav = new ModelAndView("showTracks", "tracks", tracks);
      return mav;
   }


   @GetMapping("/tracks/showOneTrack")
   public ModelAndView getTrackWithArtists(@RequestParam("trackId") int trackId) {
      TrackEntity track = trackService.getById(trackId).orElse(null);
      if (track != null) {
         ModelAndView mav = new ModelAndView("showOneTrack", "track", track);
         return mav;
      } else {
         ModelAndView mav = new ModelAndView("realerror", "message", "No Track With Id " + trackId);
         return mav;
      }
   }
//
//   @GetMapping("/pets/addAdopter")
//   public ModelAndView addAdopterShowForm() {
//      Adopter blankAdopter = new Adopter();
//      ModelAndView mav = new ModelAndView("addAdopter", "adopter", blankAdopter);
//      return mav;
//   }
//
//   @PostMapping("/pets/addAdopter")
//   public ModelAndView addAdopter(Adopter adopter) {
//      Adopter addedAdopter = trackService.addAdopter(adopter);
//      ModelAndView mav = new ModelAndView("showAdopter", "adopter", addedAdopter);
//      return mav;
//   }
//
//   @GetMapping("/pets/addPetToAdopter")
//   public ModelAndView addPetToAdopterShowForm(
//         @RequestParam(name = "adopterId") int adopterId) {
//
//      Adopter blankAdopter = new Adopter();
//      PetType [] types = PetType.values();
//      Pet blankPet = new Pet();
//      var params = Map.of("adopterId", adopterId,
//            "petTypes", types,
//            "pet", blankPet);
//      ModelAndView mav = new ModelAndView("addPetToAdopter", params);
//      return mav;
//   }
//
//   @PostMapping("/pets/addPetToAdopter/{adopterId}")
//   public ModelAndView addPetToAdopterPost(@PathVariable("adopterId") int adopterId,
//                                           Pet newPet) {
//      boolean result = trackService.addPetToAdopter(adopterId, newPet);
//      if(result) {
//         var adopterWithNewPet = trackService.getAdopter(adopterId);
//         ModelAndView mav = new ModelAndView("showAdopter", "adopter", adopterWithNewPet);
//         return mav;
//      } else {
//         ModelAndView mav = new ModelAndView("realerror", "message", "No Adopter With Id " + adopterId);
//         return mav;
//      }
//   }
//
//   @PostMapping("/pets/removePetFromAdopter/{adopterId}/{petId}")
//   public ModelAndView removePetFromAdopterPostWithPathParams(@PathVariable("adopterId") int adopterId,
//                                                @PathVariable("petId") int petId) {
//      return removePetFromAdopterPost(adopterId, petId);
//   }
//
//   @PostMapping("/pets/removePetFromAdopter")
//   public ModelAndView removePetFromAdopterPost(@RequestParam("adopterId") int adopterId,
//                                           @RequestParam("petId") int petId) {
//      boolean result = trackService.removePetFromAdopter(adopterId, petId);
//      if(result) {
//         var adopterWithNewPet = trackService.getAdopter(adopterId);
//         ModelAndView mav = new ModelAndView("showAdopter", "adopter", adopterWithNewPet);
//         return mav;
//      } else {
//         ModelAndView mav = new ModelAndView("realerror", "message", "No Adopter Or Pet With Id "
//               + adopterId + ", " + petId);
//         return mav;
//      }
//   }
}
