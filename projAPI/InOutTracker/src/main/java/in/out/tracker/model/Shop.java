package in.out.tracker.model;


public class Shop {

    private long id;
    private long shop_id;
    private String name;
    private String opening_time;
    private String closing_time;
    private int max_capacity;
    private int people_count;

    public Shop(long id, long shop_id, String name, String opening_time, String closing_time, int max_capacity, int people_count) {
        this.id = id;
        this.shop_id = shop_id;
        this.name = name;
        this.opening_time = opening_time;
        this.closing_time = closing_time;
        this.max_capacity = max_capacity;
        this.people_count = people_count;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public int getPeople_count() {
        return people_count;
    }

    public void setPeople_count(int people_count) {
        this.people_count = people_count;
    }
}
