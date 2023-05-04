package ir.ac.kntu;

import java.util.Scanner;

public abstract class Order {

    private Time time;

    private Menu.Place place;

    private String price;

    public Order() {}

    public Order(Time time, Menu.Place place, String price) {
        this.time = time;
        this.place = place;
        this.price = price;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Menu.Place getPlace() {
        return place;
    }

    public void setPlace(Menu.Place place) {
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public abstract void showOrder();

    public abstract void getOrder(Scanner scan, Handler handler, Student student);
}
