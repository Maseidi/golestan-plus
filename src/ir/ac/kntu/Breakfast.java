package ir.ac.kntu;

import java.util.Scanner;

public class Breakfast extends Order {

    private Menu.BreakfastMenu food;

    public Breakfast() {}

    public Breakfast(Time time, Menu.Place place, String price, Menu.BreakfastMenu food) {
        super(time, place, price);
        this.food = food;
    }

    public Menu.BreakfastMenu getFood() {
        return food;
    }

    public void setFood(Menu.BreakfastMenu food) {
        this.food = food;
    }

    @Override
    public void getOrder(Scanner scan, Handler handler, Student student) {
        System.out.println("Modify the time you want to receive your order:");
        Time time = Time.makeTime(scan);

        System.out.println("Choose the place you wish to receive your order:\n1-Cafeteria\n2-Dorm");
        int option = Operations.checkInputs(scan, 1, 2);
        Menu.Place places[] = Menu.Place.values();
        Menu.Place place = places[option-1];

        System.out.println("Now choose your breakfast:");
        Operations.showBreakFastMenu();
        option = Operations.checkInputs(scan, 1, 9);
        Menu.BreakfastMenu foods[] = Menu.BreakfastMenu.values();
        Menu.BreakfastMenu food = foods[option-1];

        String price = Operations.getBreakfastPrice(option);

        Breakfast breakfast = new Breakfast(time, place, price, food);
        student.addOrder(breakfast);
        System.out.println("Order submitted successfully!");
        Operations.pressEnter();
        student.menuOptions(scan, handler, student);
    }

    @Override
    public void showOrder() {
        System.out.format("Time : %s, Place : %s, Food : %s, Price : %s\n", this.getTime().showOrderTime(), this.getPlace(),
                this.getFood(), this.getPrice());
    }

}
