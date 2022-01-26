package in.out.tracker.model;

import javax.persistence.*;

@Entity
@Table(name = "daily_info")
public class DailyInfo {
    private long id;
    private String day;
    private long hour;
    private long storeid;
    private long count;

    public DailyInfo(long storeid, String day, long hour, long count) {
        this.storeid = storeid;
        this.day = day;
        this.hour = hour;
        this.count = count;
    }

    public DailyInfo() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId(){ return id; }
    public void setId(long id){ this.id= id; }

    @Column(name = "day", nullable = false)
    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    @Column(name = "hour", nullable = false)
    public long getHour() { return hour; }
    public void setHour(long hour) { this.hour = hour; }

    @Column(name = "storeid", nullable = false)
    public long getstoreid() { return storeid; }
    public void setstoreid(long storeid) { this.storeid = storeid; }

    @Column(name = "count", nullable = false)
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
}
