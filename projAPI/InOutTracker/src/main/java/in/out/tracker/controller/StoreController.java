package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Shopping;
import in.out.tracker.model.Store;
import in.out.tracker.services.StoreService;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://deti-engsoft-08:5500"})
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

    @PutMapping("api/v1/store/update/{id}")
    public Store updateStore(@Valid @RequestBody Store store, @PathVariable(value = "id") long id)
            throws ResourceNotFoundException, PulsarClientException {
        return service.updateStore(store, id);
    }

    @PutMapping("api/v1/store/update/{id}/count/{count}")
    public Store updateCount(@PathVariable(value = "id") long id, @PathVariable(value = "count") int count)
            throws ResourceNotFoundException {
        return service.updateCount(id, count);
    }

    @PutMapping("api/v1/store/update/{id}/waiting/{count}")
    public Store updateWaiting(@PathVariable(value = "id") long id, @PathVariable(value = "count") int count)
            throws ResourceNotFoundException {
        return service.updateWaiting(id, count);
    }

    @PostMapping("api/v1/stores")
    public Store addStore(@Valid @RequestBody Store store) throws PulsarClientException { return service.createStore(store); }

    @DeleteMapping("api/v1/stores/{id}")
    public void removeStore(@PathVariable(value = "id") long id)
            throws ResourceNotFoundException, PulsarClientException { service.deleteStore(id); }
}
