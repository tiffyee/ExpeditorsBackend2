package adoption.controller;

import adoption.domain.Adopter;
import adoption.service.AdopterRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adopterrepo")
public class AdopterRepoController {

    @Autowired
    private AdopterRepoService adopterRepoService;

    @Autowired
    private UriCreator uriCreator;

    public AdopterRepoController(AdopterRepoService adopterRepoService, UriCreator uriCreator){
        this.adopterRepoService = adopterRepoService;
        this.uriCreator = uriCreator;
    }

    @GetMapping
    public List<Adopter> findAll(){
        return adopterRepoService.findAll();
    }



}
