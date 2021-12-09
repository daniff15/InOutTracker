package in.out.tracker.model;

import javax.persistence.*;

@Entity
@Table(name = "store")
public class Store {

    private long id;
    private long shop_id;
    private String name;
    private String opening_time;
    private String closing_time;
    private int people_count;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    @Column(name = "shop", nullable = false)
    public getShopId(){ return shop_id; }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }
}
