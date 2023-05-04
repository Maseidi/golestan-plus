package ir.ac.kntu;

import java.util.Scanner;

public class Lunch extends Order {

    private Menu.MainLunchMenu food;

    private boolean salad;

    private Menu.BeverageMenu beverage;

    public Lunch() {}

    public Lunch(Time time, Menu.Place place, String price, Menu.MainLunchMenu food, boolean salad,
                 Menu.BeverageMenu beverage) {
        super(time, place, price);
        this.food = food;
        this.salad = salad;
        this.beverage = beverage;
    }

    public void setFood(Menu.MainLunchMenu food) {
        this.food = food;
    }

    public Menu.MainLunchMenu getFood() {
        return food;
    }

    public boolean getSalad() {
        return salad;
    }

    public void setSalad(boolean salad) {
        this.salad = salad;
    }

    public Menu.BeverageMenu getBeverage() {
        return beverage;
    }

    public void setBeverage(Menu.BeverageMenu beverage) {
        this.beverage = beverage;
    }

    @Override
    public void getOrder(Scanner scan, Handler handler, Student student) {
        System.out.println("Modify the time you want to receive your order:");
        Time time = Time.makeTime(scan);

        System.out.println("Choose the place you wish to receive your order:\n1-Cafeteria\n2-Dorm");
        int option = Operations.checkInputs(scan, 1, 2);
        Menu.Place places[] = Menu.Place.values();
        Menu.Place place = places[option-1];

        int price = 0;
        System.out.println("Now choose your lunch:");
        Operations.showLunchMenu();
        option = Operations.checkInputs(scan, 1, 9);
        Menu.MainLunchMenu foods[] = Menu.MainLunchMenu.values();
        Menu.MainLunchMenu food = foods[option-1];
        price += Operations.getLunchPrice(option);

        System.out.println("Choose the beverage:");
        Operations.showBeverageMenu();
        option = Operations.checkInputs(scan, 1, 6);
        Menu.BeverageMenu beverages[] = Menu.BeverageMenu.values();
        Menu.BeverageMenu beverage = beverages[option-1];
        price += Operations.getBeveragePrice(option);

        System.out.println("Do you wish to have salad with your meal ( costs 5000 tomans )?\n1-Yes\n2-No");
        option = Operations.checkInputs(scan, 1, 2);
        boolean salad;
        if ( option == 1 ) {
            salad = true;
            price += 5000;
        } else {
            salad = false;
        }

        Lunch lunch = new Lunch(time, place, price+" tomans", food, salad, beverage);
        student.addOrder(lunch);
        System.out.println("Order submitted successfully!");
        Operations.pressEnter();
        student.menuOptions(scan, handler, student);
    }

    @Override
    public void showOrder() {
        System.out.format("Time : %s, Place : %s, Salad : %s, Food : %s, Beverage : %s, Price : %s\n",
                this.getTime().showOrderTime(), this.getPlace(), salad, this.getFood(), getBeverage(), this.getPrice());
    }

}
