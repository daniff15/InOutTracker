package in.out.tracker.services;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.DailyInfo;
import in.out.tracker.repository.DailyInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DailyInfoService {
    @Autowired
    private DailyInfoRepository dailyInfoRepository;

    public List<DailyInfo> getAll() { return dailyInfoRepository.findAll(); }


    public long getStoreHourInfo(long storeid, String day, long hour) throws ResourceNotFoundException {
        return dailyInfoRepository.findByStoreidAndDayAndHour(storeid, day, hour).getCount();
    }

    public DailyInfo createDailyInfo(DailyInfo daily) { return dailyInfoRepository.save(daily); }
}
