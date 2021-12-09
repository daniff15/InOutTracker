package in.out.tracker.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fav_stores")
public class FavStores {

    private int user_id;
    private int store_id;
}
