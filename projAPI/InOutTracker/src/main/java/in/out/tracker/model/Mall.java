package in.out.tracker.model;

import java.util.List;


public class Mall {
    private String name;
    private int limit;
    private List<Store> stores;
    private String opening;
    private String close;
    private List<Integer> inside_mall;
    private List<Integer> waiting_mall;

    public Mall(String name, int limit, List<Store> stores, String opening, String close, List<Integer> inside_mall, List<Integer> waiting_mall) {
        this.name = name;
        this.limit = limit;
        this.stores = stores;
        this.opening = opening;
        this.close = close;
        this.inside_mall = inside_mall;
        this.waiting_mall = waiting_mall;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getOpening() {
        return opening;
    }

    public void setOpening(String opening) {
        this.opening = opening;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public List<Integer> getInside_mall() {
        return inside_mall;
    }

    public void setInside_mall(List<Integer> inside_mall) {
        this.inside_mall = inside_mall;
    }

    public List<Integer> getWaiting_mall() {
        return waiting_mall;
    }

    public void setWaiting_mall(List<Integer> waiting_mall) {
        this.waiting_mall = waiting_mall;
    }

    @Override
    public String toString() {
        return "Mall{" +
                "name='" + name + '\'' +
                ", limit=" + limit +
                ", stores=" + stores +
                ", opening='" + opening + '\'' +
                ", close='" + close + '\'' +
                ", inside_mall=" + inside_mall +
                ", waiting_mall=" + waiting_mall +
                '}';
    }
}
