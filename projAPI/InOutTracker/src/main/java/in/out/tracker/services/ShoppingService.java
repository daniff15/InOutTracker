package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Shopping;
import in.out.tracker.model.Store;
import in.out.tracker.repository.ShoppingRepository;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingService {
    @Autowired
    private ShoppingRepository shoppingRepository;

    @Value("${pulsar.service-url}")
    private String serviceURL;

    public String getServiceURL() { return serviceURL; }

    public List<Shopping> getShoppings() {
        return shoppingRepository.findAll();
    }

    public ResponseEntity<Shopping> getShoppingById(long id)
            throws ResourceNotFoundException {
        Shopping shoppings = shoppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + id));
        return ResponseEntity.ok().body(shoppings);
    }

    public List<Store> getStoresInShopping(long id)
            throws ResourceNotFoundException {
        Shopping shopping = shoppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + id));
        return shopping.getStores();
    }

    public Shopping createShopping(Shopping shopping) throws PulsarClientException {
        Shopping newShopping = shoppingRepository.save(shopping);
        new UpdateDataGen().informDataGen("newShopping: " + newShopping.getName());
        return newShopping;
    }

    public void deleteShopping(long id)
            throws ResourceNotFoundException, PulsarClientException {
        Shopping shopping = shoppingRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + id));
        shoppingRepository.delete(shopping);
        new UpdateDataGen().informDataGen("delete: " + id);

    }

    public Shopping updateCount(long shopping_id, int people) throws ResourceNotFoundException {
        Shopping shopping = shoppingRepository.findById(shopping_id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + shopping_id));
        shopping.setPeople_count(people);
        shoppingRepository.save(shopping);
        return shopping;
    }

    public Shopping updateShopping(Shopping updatedShopping, long id) throws ResourceNotFoundException, PulsarClientException {
        Shopping shopping = shoppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + id));
        shopping.setName(updatedShopping.getName());
        shopping.setOpening_time(updatedShopping.getOpening_time());
        shopping.setClosing_time(updatedShopping.getClosing_time());
        shopping.setMax_capacity(updatedShopping.getMax_capacity());
        shopping.setPeople_count(updatedShopping.getPeople_count());
        shoppingRepository.save(shopping);
        new UpdateDataGen().informDataGen("update: " + updatedShopping.getName());
        return shopping;
    }
}
