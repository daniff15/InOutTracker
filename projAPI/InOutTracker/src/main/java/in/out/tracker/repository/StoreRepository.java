package in.out.tracker.repository;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}