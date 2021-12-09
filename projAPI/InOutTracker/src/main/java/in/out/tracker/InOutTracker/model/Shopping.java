package in.out.tracker.InOutTracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "shopping_center")
public class Shopping {

    private long id;
    private String name;
    private String opening_time;
    private int max_capacity;
    private int people_count;
}
