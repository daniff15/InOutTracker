package in.out.tracker.controller;

import in.out.tracker.exception.ResourceNotFoundException;
import in.out.tracker.model.DailyInfo;
import in.out.tracker.services.DailyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500", "http://deti-engsoft-08:5500"})
@RestController
public class DailyInfoController {
    @Autowired
    private DailyInfoService service;

    @GetMapping("api/v1/daily")
    public List<DailyInfo> getAllDailyInfo() { return service.getAll(); }

    @GetMapping("api/v1/daily/{storeid}/{day}")
    public List<DailyInfo> getStoreDayInfo(@PathVariable(value = "storeid") long id, @PathVariable(value = "day") long day)
            throws ResourceNotFoundException {
        return service.getStoreDayInfo(id, day);
    }

    @GetMapping("api/v1/daily/{storeid}/{day}/{hour}")
    public long getStoreHourInfo(@PathVariable(value = "storeid") long id, @PathVariable(value = "day") long day, @PathVariable(value = "hour") long hour) throws ResourceNotFoundException {
        return service.getStoreHourInfo(id, day, hour);
    }

    @PostMapping("api/v1/add/daily")
    public DailyInfo addStore(@Valid @RequestBody DailyInfo daily) { return service.createDailyInfo(daily); }

    @PostMapping("api/v1/add/daily/{storeid}/{day}/{hour}/{count}")
    public DailyInfo addStoreWithValues(@PathVariable(value = "storeid") long id, @PathVariable(value = "day") long day, @PathVariable(value = "hour") long hour, @PathVariable(value = "count") long count) {
        return service.createDailyInfo(new DailyInfo(id, day, hour, count));
    }
}
