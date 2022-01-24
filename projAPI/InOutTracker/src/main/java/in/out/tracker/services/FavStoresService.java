package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.FavStores;
import in.out.tracker.repository.FavStoresRepository;
import in.out.tracker.repository.ShoppingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavStoresService {
    @Autowired
    private FavStoresRepository favStoresRepository;

    @Value("${pulsar.service-url}")
    private String serviceURL;

    public String getServiceURL() { return serviceURL; }

    public List<FavStores> getAllFavStores() { return favStoresRepository.findAll(); }

    public List<Integer> getUserFavStore(long user_id)
                    throws ResourceNotFoundException{
        List<FavStores> allFavStores = getAllFavStores();
        List<Integer> userFavStores = new ArrayList<>();
        for(FavStores relation: allFavStores){
            if (relation.getUser_id() == user_id)
                userFavStores.add(relation.getStore_id());
        }
        return userFavStores;
    }

    public FavStores createFavRelation(FavStores relation){
        if (getAllFavStores().contains(relation)) return relation;
        return favStoresRepository.save(relation);
    }

    public void deleteFavStore(FavStores fav) {
        List<FavStores> allFavStores = getAllFavStores();
        for(FavStores relation: allFavStores){
            if (relation.getUser_id() == fav.getUser_id() && relation.getStore_id() == fav.getStore_id())
                favStoresRepository.delete(relation);
        }
    }
}
