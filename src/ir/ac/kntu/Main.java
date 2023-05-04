package ir.ac.kntu;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Welcome to Golestan!");
        Handler handler = new Handler();
        Scanner scan = new Scanner(System.in);
        mainMenu(scan, handler);
    }

    public static void mainMenu(Scanner scan, Handler handler) {
        Operations.showMainMenuMessage();
        int option = Operations.checkInputs(scan, 1, 3);
        Menu.MainMenu options[] = Menu.MainMenu.values();
        switch (options[option-1]) {
            case SUBMIT_ADMIN:
                Admin account = new Admin();
                handler.submit(1, scan, handler, account);
                break;
            case LOG_IN:
                handler.logIn(scan, handler);
                break;
            default:
                scan.close();
                System.exit(1);
        }
    }
}