package in.out.tracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fav_stores")
public class FavStores {

    private int user_id;
    private int store_id;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUser_id() { return user_id; }
    public void setUser_id(int user_id) { this.user_id = user_id; }

    @Id
    @Column(name = "store_id", nullable = false)
    public int getStore_id() { return store_id; }
    public void setStore_id(int store_id) { this.store_id = store_id; }
}
