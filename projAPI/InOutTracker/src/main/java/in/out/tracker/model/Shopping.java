package in.out.tracker.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shopping_center")
public class Shopping {


    private long id;
    private String name;
    private String opening_time;
    private String closing_time;
    private int max_capacity;
    private int people_count;

    @OneToMany(mappedBy = "shopping_center")
    private List<Store> stores;


    @Id
    @Column(name = "id", nullable = false)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "name", nullable = true)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Column(name = "stores", nullable = true)
    public List<Store> getStores() { return stores; }
    public void setStores(List<Store> stores) { this.stores = stores; }


    @Column(name = "opening_time", nullable = true)
    public String getOpening_time() { return opening_time; }
    public void setOpening_time(String opening_time) { this.opening_time = opening_time; }

    @Column(name = "closing_time", nullable = true)
    public String getClosing_time() { return closing_time; }
    public void setClosing_time(String closing_time) { this.closing_time = closing_time; }

    @Column(name = "max_capacity", nullable = true)
    public int getMax_capacity() { return max_capacity; }
    public void setMax_capacity(int max_capacity) { this.max_capacity = max_capacity; }

    @Column(name = "people_count", nullable = true)
    public int getPeople_count() { return people_count; }
    public void setPeople_count(int people_count) { this.people_count = people_count; }
}
