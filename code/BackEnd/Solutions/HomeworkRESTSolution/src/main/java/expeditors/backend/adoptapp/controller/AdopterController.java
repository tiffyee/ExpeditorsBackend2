package expeditors.backend.adoptapp.controller;

import expeditors.backend.adoptapp.domain.Adopter;
import expeditors.backend.adoptapp.service.AdopterService;
import expeditors.backend.utils.UriCreator;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * @author whynot
 */
@RestController
@RequestMapping("/petservice")
public class AdopterController {

    private AdopterService adopterService;
    private UriCreator uriCreator;

    public AdopterController(AdopterService adopterService,
                             UriCreator uriCreator) {
        this.adopterService = adopterService;
        this.uriCreator = uriCreator;
    }

//    @GetMapping
//    public ResponseEntity<?> getAll() {
//        List<Adopter> adopters = adopterService.getAllAdopters();
//        return ResponseEntity.ok(adopters);
//    }
    /**
     * A very simple example of passing in query parameters to specify search criteria.
     * Should be called like: http://localhost:8080/track?title=Blah%20Blah%20Blah&album=OtherBlah.
     * We capture all the passed in parameters, if any.  Look in the service and DAO to
     * how we process them.
     *
     * @param queryStrings the query params in a map
     * @return a possibly empty list of tracks
     */

    @GetMapping
    public ResponseEntity<?> getAllTracks(@RequestParam Map<String, String> queryStrings) {
        List<Adopter> adopters = null;
        if(queryStrings.isEmpty()) {
            adopters = adopterService.getAllAdopters();
        } else {
            adopters = adopterService.getAdoptersBy(queryStrings);
        }
        return ResponseEntity.ok(adopters);
    }

    @GetMapping("/{id:\\d+}")   //id of only digits
    public ResponseEntity<?> getAdopter(@PathVariable("id") int id) {
        Adopter adopter = adopterService.getAdopter(id);
        if(adopter == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No adopters with id: " + id);
        }
        return ResponseEntity.ok(adopter);
    }

    @PostMapping
    public ResponseEntity<?> addAdopter(@RequestBody @Valid Adopter adopter) {
        Adopter newAdopter = adopterService.addAdopter(adopter);

        URI uri = uriCreator.getURI(newAdopter.getId());

        String s = null;
        System.out.println(s.length());

        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id:\\d+}")
    public ResponseEntity<?> deleteAdopter(@PathVariable("id") int id) {
        boolean result = adopterService.deleteAdopter(id);
        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Adopter with id: " + id);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<?> updateAdopter(@RequestBody Adopter adopter) {
        boolean result = adopterService.updateAdopter(adopter);
        if(!result) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No adopter with id: " + adopter.getId());
        }

        return ResponseEntity.noContent().build();
    }
}
