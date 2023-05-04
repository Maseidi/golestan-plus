package ir.ac.kntu;

import java.util.Scanner;

public class Operations {

    public static int checkInputs(Scanner scan, int first, int second) {
        String input = scan.nextLine();
        while ( Integer.parseInt(input) < first || Integer.parseInt(input) > second ) {
            System.out.println("Invalid input! Please try again.");
            input = scan.nextLine();
        }
        return Integer.parseInt(input);
    }

    public static void pressEnter() {
        System.out.println("Press enter to continue...");
        try {
            System.in.read();
        } catch ( Exception ex ) {

        }
    }

    public static void showMainMenuMessage() {
        System.out.println("Enter your choice:");
        System.out.println("1- Submit a new Admin");
        System.out.println("2- Log in to system");
        System.out.println("3- Exit");
    }

    public static void showAdminMenuMessage() {
        System.out.println("Choose your option:");
        System.out.println(" 1- Submit a new course");
        System.out.println(" 2- Submit a new group");
        System.out.println(" 3- Submit a new student");
        System.out.println(" 4- Submit a new master");
        System.out.println(" 5- Edit a group");
        System.out.println(" 6- Delete a group");
        System.out.println(" 7- Set groups for students");
        System.out.println(" 8- Set groups for masters");
        System.out.println(" 9- Show all courses");
        System.out.println("10- Show all groups");
        System.out.println("11- Show all students");
        System.out.println("12- Show all masters");
        System.out.println("13- Check for removing group requests");
        System.out.println("14- Log out");
    }

    public static void editCourseGroupMenu() {
        System.out.println("Choose the factor you wish to edit:");
        System.out.println("1- Edit course");
        System.out.println("2- Edit course group number");
        System.out.println("3- Edit course group capacity");
        System.out.println("4- Edit course group term number");
        System.out.println("5- Remove master");
        System.out.println("6- Remove students");
        System.out.println("7- Edit course group sessions");
        System.out.println("8- Change course group type");
        System.out.println("9- Exit");
    }

    public static void showStudentMenuMessage() {
        System.out.println("Choose your option:");
        System.out.println("1- Set course groups");
        System.out.println("2- Weekly schedule");
        System.out.println("3- Check all passed courses and marks");
        System.out.println("4- Request to remove a group");
        System.out.println("5- Order food");
        System.out.println("6- Log out");
    }

    public static void showMasterMenuMessage() {
        System.out.println("Choose your option:");
        System.out.println("1- Check course groups");
        System.out.println("2- Check students of a course group");
        System.out.println("3- Weekly schedule");
        System.out.println("4- Set marks for students");
        System.out.println("5- Log out");
    }

    public static void showBreakFastMenu() {
        System.out.println("1- Egg                         price: 5000");
        System.out.println("2- Jam                         price: 5000");
        System.out.println("3- Honey                       price: 6000");
        System.out.println("4- Chocolate                   price: 6000");
        System.out.println("5- Cream                       price: 6000");
        System.out.println("6- Butter                      price: 10000");
        System.out.println("7- Cheese                      price: 12000");
        System.out.println("8- Omelette                    price: 10000");
        System.out.println("9- Sausage                     price: 25000");
    }

    public static String getBreakfastPrice(int number) {
        if ( number == 1 || number == 2 ) {
            return "5000 tomans";
        } else if ( number == 3 || number == 4 || number == 5 ) {
            return "6000 tomans";
        } else if ( number == 6 || number == 8 ) {
            return "10000 tomans";
        } else if ( number == 7 ) {
            return "12000 tomans";
        } else {
            return "25000 tomans";
        }
    }

    public static void showLunchMenu() {
        System.out.println("1- Ghorme sabzi                price: 30000");
        System.out.println("2- Gheime                      price: 30000");
        System.out.println("3- Chelo kabab                 price: 40000");
        System.out.println("4- Chelo jooje                 price: 35000");
        System.out.println("5- Zereshk polo ba morgh       price: 40000");
        System.out.println("6- Maccaroni                   price: 25000");
        System.out.println("7- Egg                         price: 5000");
        System.out.println("8- Omelette                    price: 10000");
        System.out.println("9- Sausage                     price: 25000");
    }

    public static int getLunchPrice(int number) {
        if ( number == 1 || number == 2 ) {
            return 30000;
        } else if ( number == 3 || number == 5 ) {
            return 40000;
        } else if ( number == 4 ) {
            return 35000;
        } else if ( number == 6 || number == 9 ) {
            return 25000;
        } else if ( number == 7 ) {
            return 5000;
        } else {
            return 10000;
        }
    }

    public static void showBeverageMenu() {
        System.out.println("1- Soda                        price: 12000");
        System.out.println("2- Delester                    price: 15000");
        System.out.println("3- Doogh                       price: 15000");
        System.out.println("4- Juice                       price: 15000");
        System.out.println("5- Water                       price: 5000");
        System.out.println("6- None");
    }

    public static int getBeveragePrice(int number) {
        if ( number == 1 ) {
            return 12000;
        } else if ( number == 2 || number == 3 || number == 4 ) {
            return 15000;
        } else if ( number == 5 ) {
            return 5000;
        } else {
            return 0;
        }
    }
}