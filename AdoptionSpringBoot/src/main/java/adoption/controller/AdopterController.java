package adoption.controller;

import adoption.domain.Adopter;
import adoption.service.AdopterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/adopter")
public class AdopterController {

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private UriCreator uriCreator;

    public AdopterController(AdopterService adopterService, UriCreator uriCreator){
        this.adopterService = adopterService;
        this.uriCreator = uriCreator;
    }

    @GetMapping
    public List<Adopter> getAllAdopters(){
        List<Adopter> adopters = adopterService.getAllAdopters();
        return adopters;
    }

    @GetMapping("/{id:\\d+}")
    public ResponseEntity<?> getAdopter(@PathVariable("id") int id){
        Adopter adopter = adopterService.findById(id);
        if(adopter == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No adopter with id: " + id);
        }
        return ResponseEntity.ok(adopter);
    }

    @PostMapping
    public ResponseEntity<?> addAdopter(@RequestBody Adopter adopter){
        Adopter newAdopter = adopterService.addAdopter(adopter);

        URI newResource = uriCreator.getURI(newAdopter.getId());
//        URI newResource = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(newAdopter.getId())
//                .toUri();

        return ResponseEntity.created(newResource).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdopter(@PathVariable("id") int id) {
        boolean result = adopterService.deleteAdopter(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No adopter with id: " + id);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateAdopter(@RequestBody Adopter adopter){
        boolean result = adopterService.updateAdopter(adopter);
        if(!result){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No adopter with id: " + adopter.getId());
        }
        return ResponseEntity.noContent().build();
    }
}
