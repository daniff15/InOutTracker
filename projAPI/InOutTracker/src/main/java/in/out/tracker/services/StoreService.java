package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Store;
import in.out.tracker.repository.StoreRepository;
import org.apache.pulsar.client.api.PulsarClientException;
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

    public Store createStore(Store store) throws PulsarClientException {
        Store newStore = storeRepository.save(store);
        new UpdateDataGen().informDataGen("new: " + store.getName());
        return newStore;
    }

    public void deleteStore(long id) throws ResourceNotFoundException, PulsarClientException {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + id));
        storeRepository.delete(store);
        new UpdateDataGen().informDataGen("delete: " + id);
    }

    public Store updateCount(long store_id, int people) throws ResourceNotFoundException {
        Store store = storeRepository.findById(store_id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + store_id));
        store.setPeople_count(people);
        storeRepository.save(store);
        return store;
    }

    public Store updateWaiting(long store_id, int people) throws ResourceNotFoundException {
        Store store = storeRepository.findById(store_id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + store_id));
        store.setWaiting(people);
        storeRepository.save(store);
        return store;
    }

    public Store updateStore(Store updatedStore, long id) throws ResourceNotFoundException, PulsarClientException {
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Store not found for this id :: " + id));
        store.setName(updatedStore.getName());
        store.setOpening_time(updatedStore.getOpening_time());
        store.setClosing_time(updatedStore.getClosing_time());
        store.setMax_capacity(updatedStore.getMax_capacity());
        store.setPeople_count(updatedStore.getPeople_count());
        store.setWaiting(updatedStore.getWaiting());
        storeRepository.save(store);
        new UpdateDataGen().informDataGen("update: " + updatedStore.getName());
        return store;
    }
}
