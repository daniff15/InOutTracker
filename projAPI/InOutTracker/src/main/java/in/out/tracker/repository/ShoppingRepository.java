package in.out.tracker.repository;

import in.out.tracker.model.Shopping;
import in.out.tracker.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingRepository extends JpaRepository<Shopping, Long> {
}
