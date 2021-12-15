package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Shopping;
import in.out.tracker.model.Store;
import in.out.tracker.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingService {
    @Autowired
    private ShoppingRepository shoppingRepository;

    public List<Shopping> getShoppings() {
        return shoppingRepository.findAll();
    }

    public ResponseEntity<Shopping> getShoppingById(long id)
            throws ResourceNotFoundException {
        Shopping shoppings = shoppingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + id));
        return ResponseEntity.ok().body(shoppings);
    }

    public Shopping createShopping(Shopping shopping) {
        return shoppingRepository.save(shopping);
    }

    public void deleteShopping(Shopping shopping) {
        shoppingRepository.delete(shopping);
    }

    public Shopping updateShopping(Shopping shopping) throws ResourceNotFoundException {
        deleteShopping(shoppingRepository.findById(shopping.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Shopping not found for this id :: " + shopping.getId())));
        createShopping(shopping);
        return shopping;
    }
}