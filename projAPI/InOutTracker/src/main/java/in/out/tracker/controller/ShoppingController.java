package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Shopping;
import in.out.tracker.model.Store;
import in.out.tracker.services.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ShoppingController {
    @Autowired
    private ShoppingService service;

    @GetMapping("/api/v1/shoppings")
    public List<Shopping> getAllShoppings() { return service.getShoppings(); }

    @PostMapping("/api/v1/shoppings")
    public Shopping addShopping(@Valid @RequestBody Shopping shopping) { return service.createShopping(shopping); }

    @DeleteMapping("api/v1/shoppings")
    public void removeShopping(@Valid @RequestBody Shopping shopping) { service.deleteShopping(shopping); }

    @GetMapping("/api/v1/shopping/{id}")
    public ResponseEntity<Shopping> getShopping(@PathVariable(value = "id") long id)
                        throws ResourceNotFoundException{
        return service.getShoppingById(id);
    }

    @PutMapping("api/v1/shopping/update")
    public Shopping updateShopping(@Valid @RequestBody Shopping shopping) throws ResourceNotFoundException { return service.updateShopping(shopping); }

    @PutMapping("api/v1/shopping/update/{id}/count/{count}")
    public Shopping updateCount(@PathVariable(value = "id") long id, @PathVariable(value = "count") int count) throws ResourceNotFoundException {
        return service.updateCount(id, count);
    }

}
