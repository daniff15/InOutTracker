package in.out.tracker.InOutTracker.repository;

import in.out.tracker.InOutTracker.model.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingRepository extends JpaRepository<Shopping, Long> {
}
