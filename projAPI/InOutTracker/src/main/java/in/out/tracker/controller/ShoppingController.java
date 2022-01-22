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

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://deti-engsoft-08:5500"})
@RestController
public class ShoppingController {
    @Autowired
    private ShoppingService service;

    @GetMapping("/api/v1/shoppings")
    public List<Shopping> getAllShoppings() { return service.getShoppings(); }

    @PostMapping("/api/v1/shoppings")
    public Shopping addShopping(@Valid @RequestBody Shopping shopping) { return service.createShopping(shopping); }

    @DeleteMapping("api/v1/shoppings/{id}")
    public void removeShopping(@PathVariable(value = "id") long id)
            throws ResourceNotFoundException { service.deleteShopping(id); }

    @GetMapping("/api/v1/shopping/{id}")
    public ResponseEntity<Shopping> getShopping(@PathVariable(value = "id") long id)
                        throws ResourceNotFoundException{
        return service.getShoppingById(id);
    }

    @GetMapping("/api/v1/shopping/{id}/stores")
    public List<Store> getStoresInShopping(@PathVariable(value = "id") long id)
            throws ResourceNotFoundException {
        return service.getStoresInShopping(id);
    }

    @PutMapping("api/v1/shopping/update/{id}")
    public Shopping updateShopping(@Valid @RequestBody Shopping shopping, @PathVariable(value = "id") long id)
            throws ResourceNotFoundException {
        return service.updateShopping(shopping, id);
    }

    @PutMapping("api/v1/shopping/update/{id}/count/{count}")
    public Shopping updateCount(@PathVariable(value = "id") long id, @PathVariable(value = "count") int count) throws ResourceNotFoundException {
        return service.updateCount(id, count);
    }
}
