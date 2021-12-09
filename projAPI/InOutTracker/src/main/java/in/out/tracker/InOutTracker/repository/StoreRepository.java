package in.out.tracker.InOutTracker.repository;

import in.out.tracker.InOutTracker.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}