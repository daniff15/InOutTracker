package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Shopping;
import in.out.tracker.model.Store;
import in.out.tracker.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StoreController {
    @Autowired
    private StoreService service;

    @GetMapping("api/v1/stores")
    public List<Store> getAllStores() { return service.getStores(); }

    @GetMapping("api/v1/store/{id}")
    public ResponseEntity<Store> getStore(@PathVariable(value = "id") long id)
            throws ResourceNotFoundException{
        return service.getStoreById(id);
    }

    @PutMapping("api/v1/store/update")
    public Store updateStore(@Valid @RequestBody Store store) throws ResourceNotFoundException { return service.updateStore(store); }

    @PostMapping("api/v1/stores")
    public Store addStore(@Valid @RequestBody Store store) { return service.createStore(store); }

    @DeleteMapping("api/v1/stores")
    public void removeStore(@Valid @RequestBody Store store) { service.deleteStore(store); }
}
