package ir.ac.kntu;

import java.io.*;
import java.util.*;

public class Admin {

    protected static HashMap<String, Admin> accounts = new HashMap<>();

    private String userName;

    private String password;

    public Admin() {}

    public Admin(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public static HashMap<String, Admin> getAccounts() {
        return new HashMap<>(accounts);
    }

    public static void setAccounts(HashMap<String, Admin> accounts) {
        Admin.accounts = new HashMap<>(accounts);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String setUserName(Scanner scan) {
        try {
            System.out.println("Enter username:");
            String userName = scan.nextLine();
            Exceptions.repeatedUserName(userName);
            return userName;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setUserName(scan);
        }
    }

    public String setPassword(Scanner scan) {
        System.out.println("Enter password:");
        String password = scan.nextLine();
        return password;
    }

    public void submit(Scanner scan, Handler handler, Admin account) {
        String userName = setUserName(scan);
        String password = setPassword(scan);
        Admin admin = new Admin(userName, password);
        accounts.put(userName, admin);
        System.out.println("Admin submitted successfully!");
        Operations.pressEnter();
        Main.mainMenu(scan, handler);
    }

    protected void setGroups(Scanner scan, Handler handler, Admin admin) {}

    public void declineRequest(Scanner scan, Handler handler, Admin admin, Student student, String courseName,
                               String groupNumber) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(student.getUserName()+".txt"));
            writer.write("Admin declined your request for removing the course with name \""+courseName+"\" and group" +
                    " number of \""+groupNumber+"\".\n");
            writer.close();
            System.out.println("Request declined.");
            Operations.pressEnter();
            menuOptions(scan, handler, admin);
        } catch ( IOException ex ) {
            System.out.println(ex.getMessage());
        }
    }

    public void acceptRequest(Scanner scan, Handler handler, Admin admin, Student student, String courseName,
                              String groupNumber) {
        for ( Group group : Group.groups ) {
            if ( group.getGroupNumber().equals(groupNumber) && group.getName().equals(courseName) ) {
                student.removeGroup(group);
                group.removeStudent(student);
            }
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(student.getUserName()+".txt"));
            writer.write("Admin accepted your request for removing the course with name \""+courseName+"\" and group" +
                    " number of \""+groupNumber+"\".\n");
            writer.close();
            System.out.println("Request accepted.");
            Operations.pressEnter();
            menuOptions(scan, handler, admin);
        } catch ( IOException ex ) {
            System.out.println(ex.getMessage());
        }
    }

    public void checkFile(Scanner scan, Handler handler, Admin admin, Student student) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(student.getUserName() + ".txt"));
            String request = reader.readLine();
            String courseName = request.substring(0, request.indexOf('-'));
            String groupNumber = request.substring(request.indexOf('-')+1);
            System.out.format("This student requested to remove the group named \"%s\" with the group number of %s\n",
                    courseName, groupNumber);
            reader.close();
            System.out.println("Choose your decision:\n1-Accept\n2-Decline");
            int option = Operations.checkInputs(scan, 1, 2);
            if ( option == 1 ) {
                acceptRequest(scan, handler, admin, student, courseName, groupNumber);
            } else {
                declineRequest(scan, handler, admin, student, courseName, groupNumber);
            }
        } catch ( Exception ex ) {
            System.out.println("This student has no removing requests.");
            Operations.pressEnter();
            menuOptions(scan, handler, admin);
        }
    }

    public void checkRequests(Scanner scan, Handler handler, Admin admin) {
        System.out.println("Enter the student ID to check their request:");
        String id = scan.nextLine();
        for ( Student student : Student.getStudents() ) {
            if ( student.getUserName().equals(id) ) {
                checkFile(scan, handler, admin, student);
            }
        }

        System.out.println("No students have this ID! Please try again.");
        checkRequests(scan, handler, admin);
    }

    public void menuOptions(Scanner scan, Handler handler, Admin admin) {
        Operations.showAdminMenuMessage();
        int option = Operations.checkInputs(scan, 1, 14);
        Menu.AdminMenu options[] = Menu.AdminMenu.values();
        switch ( options[option-1] ) {
            case SUBMIT_COURSE:
                handler.submitCourse(scan, handler, admin);
                break;
            case SUBMIT_GROUP:
                handler.submitCourseGroup(scan, handler, admin);
                break;
            case SUBMIT_STUDENT:
                handler.submit(2, scan, handler, admin);
                break;
            case SUBMIT_MASTER:
                handler.submit(3, scan, handler, admin);
                break;
            case EDIT_GROUP:
                handler.chooseGroup(1, scan, handler, admin);
                break;
            case DELETE_GROUP:
                handler.chooseGroup(2, scan, handler, admin);
                break;
            case SET_FOR_STUDENTS:
                handler.choose(1, scan, handler, admin);
                break;
            case SET_FOR_MASTERS:
                handler.choose(2, scan, handler, admin);
                break;
            case SHOW_ALL_COURSES:
                Course.showAllCourses();
                Operations.pressEnter();
                menuOptions(scan, handler, admin);
                break;
            case SHOW_ALL_GROUPS:
                Group.showAllGroups();
                Operations.pressEnter();
                menuOptions(scan, handler, admin);
                break;
            case SHOW_ALL_STUDENTS:
                Student.showAllStudents();
                Operations.pressEnter();
                menuOptions(scan, handler, admin);
                break;
            case SHOW_ALL_MASTERS:
                Master.showAllMasters();
                Operations.pressEnter();
                menuOptions(scan, handler, admin);
                break;
            case REMOVE_REQUESTS:
                checkRequests(scan, handler, admin);
                break;
            default:
                Main.mainMenu(scan, handler);
        }
    }
}