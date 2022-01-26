package in.out.tracker.repository;

import in.out.tracker.model.DailyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyInfoRepository extends JpaRepository<DailyInfo, Long> {
    List<DailyInfo> findByStoreidAndDay(long storeid, long day);

    DailyInfo findByStoreidAndDayAndHour(long storeid, long day, long hour);
}
