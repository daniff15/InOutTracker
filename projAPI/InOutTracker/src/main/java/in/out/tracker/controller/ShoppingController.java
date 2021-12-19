package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Shopping;
import in.out.tracker.services.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class ShoppingController {
    @Autowired
    private ShoppingService service;

    @GetMapping("/api/v1/shoppings")
    public List<Shopping> getAllShoppings() { return service.getShoppings(); }

    @GetMapping("/api/v1/shopping/{id}")
    public ResponseEntity<Shopping> getShopping(@PathVariable(value = "id") long id)
                        throws ResourceNotFoundException{
        return service.getShoppingById(id);
    }

    @PostMapping("/api/v1/shoppings")
    public Shopping addShopping(@Valid @RequestBody Shopping shopping) { return service.createShopping(shopping); }
}
