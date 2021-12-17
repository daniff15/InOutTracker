package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Store;
import in.out.tracker.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {
    @Autowired
    private StoreRepository storeRepository;

    public List<Store> getStores() { return storeRepository.findAll(); }

    public ResponseEntity<Store> getStoreById(long id)
                        throws ResourceNotFoundException{
            Store stores = storeRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + id));
            return ResponseEntity.ok().body(stores);
    }

    public Store createStore(Store store) { return storeRepository.save(store); }

    public void deleteStore(Store store) { storeRepository.delete(store); }

    public Store updateStore(Store store) throws ResourceNotFoundException {
        deleteStore(storeRepository.findById(store.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + store.getId())));
        createStore(store);
        return store;
    }
}