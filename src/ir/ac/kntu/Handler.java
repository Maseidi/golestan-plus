package ir.ac.kntu;

import java.util.*;

public class Handler {

    public void submit(int number ,Scanner scan, Handler handler, Admin account) {
        Admin admin;
        if ( number == 1 ) {
            admin = new Admin();
        } else if ( number == 2 ) {
            admin = new Student();
        } else {
            admin = new Master();
        }
        admin.submit(scan, handler, account);
    }

    public void logIn(Scanner scan, Handler handler) {
        try {
            System.out.println("Enter username:");
            String userName = scan.nextLine();
            System.out.println("Enter password:");
            String password = scan.nextLine();
            Exceptions.logInExceptions(userName, password);
            System.out.println("Log in successful!");
            Operations.pressEnter();
            Admin.getAccounts().get(userName).menuOptions(scan, handler, Admin.getAccounts().get(userName));
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            logIn(scan, handler);
        }
    }

    public void submitCourse(Scanner scan, Handler handler, Admin admin) {
        Course course = new Course();
        course.submit(scan, handler, admin);
    }

    public void submitCourseGroup(Scanner scan, Handler handler, Admin admin) {
        System.out.println("Enter the group type:\n1-Attendance\n2-Online");
        int option = Operations.checkInputs(scan, 1, 2);
        Group group;
        if ( option == 1 ) {
            group = new AttendanceGroup();
        } else {
            group = new OnlineGroup();
        }
        group.submit(scan, handler, admin);
    }

    public void chooseGroup(int number, Scanner scan, Handler handler, Admin admin) {
        try {
            Exceptions.noGroupsExist();
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            admin.menuOptions(scan, handler, admin);
        }
        System.out.println("Choose the group you wish to edit or delete:");
        for ( Group group : Group.groups ) {
            System.out.format("%d. ", Group.groups.indexOf(group)+1);
            group.print();
        }
        System.out.format("%d. Cancel choosing the groups\n", Group.groups.size()+1);
        int option = Operations.checkInputs(scan, 1, Group.groups.size()+1);
        if ( option == Group.groups.size()+1 ) {
            System.out.println("Editing/Removing course group cancelled.");
            Operations.pressEnter();
            admin.menuOptions(scan, handler, admin);
        } else {
            if ( number == 1 ) {
                Group.groups.get(option-1).editGroup(scan, handler, admin);
            } else {
                Group.groups.get(option-1).deleteGroup(scan, handler, admin);
            }
        }
    }

    public void choose(int number, Scanner scan, Handler handler, Admin admin) {
        Master master;
        if ( number == 1 ) {
            System.out.println("Enter the student ID to choose the student you wish to set groups for:");
            master = new Student();
        } else {
            System.out.println("Enter the master ID to choose the master you wish to set groups for:");
            master = new Master();
        }
        master.choose(scan, handler, admin);
    }
}