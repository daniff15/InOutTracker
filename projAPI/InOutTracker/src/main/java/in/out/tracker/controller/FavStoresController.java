package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.FavStores;
import in.out.tracker.services.FavStoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class FavStoresController {
    @Autowired
    private FavStoresService service;

    @GetMapping("/api/v1/user/{id}/favorites")
    public List<Integer> getUserFavStores(@PathVariable(value = "id") long user_id)
                        throws ResourceNotFoundException{
        return service.getUserFavStore(user_id);
    }

    @PostMapping("/api/v1/add/favorite")
    public FavStores addRelation(@Valid @RequestBody FavStores relation) { return service.createFavRelation(relation); }
}
