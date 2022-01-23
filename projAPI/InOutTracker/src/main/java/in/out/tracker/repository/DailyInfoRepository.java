package in.out.tracker.repository;

import in.out.tracker.model.DailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyInfoRepository extends JpaRepository<DailyInfo, Long> {
    DailyInfo findByStoreidAndDayAndHour(long storeid, long day, long hour);
}
