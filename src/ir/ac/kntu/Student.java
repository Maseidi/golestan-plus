package ir.ac.kntu;

import java.io.*;
import java.util.*;

public class Student extends Master {

    private static ArrayList<Student> students = new ArrayList<>();

    private String units;

    private HashMap<Course, String> passedCourses;

    private ArrayList<Order> orders;

    public Student() {}

    public Student(String userName, String password, String name, String familyName, ArrayList<Group> groups,
                   String units, HashMap<Course, String> passedCourses) {
        super(userName, password, name, familyName, groups);
        this.units = units;
        this.passedCourses = passedCourses;
        this.orders = new ArrayList<>();
    }

    public static ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public static void setStudents(ArrayList<Student> students) {
        Student.students = new ArrayList<>(students);
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public HashMap<Course, String> getPassedCourses() {
        return new HashMap<>(passedCourses);
    }

    public void setPassedCourses(HashMap<Course, String> passedCourses) {
        this.passedCourses = new HashMap<>(passedCourses);
    }

    public ArrayList<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = new ArrayList<>(orders);
    }

    public String setUnits(Scanner scan) {
        System.out.println("Enter the units this student has for this term:");
        int units = Operations.checkInputs(scan, 0, 20);
        return units+"";
    }

    public void makePassedCourses(Scanner scan, HashMap<Course, String> passedCourses) {
        try {
            while (true) {
                Course.showAllCourses();
                System.out.format("%d. Finish setting the passed courses\n", Course.courses.size() + 1);
                System.out.println("Choose the course to add as student's passed courses:");
                int option = Operations.checkInputs(scan, 1, Course.courses.size() + 1);
                if (option == Course.courses.size() + 1) {
                    break;
                }
                Exceptions.alreadyInThePassedCourses(passedCourses, Course.courses.get(option - 1));
                System.out.println("Now enter the student's mark at this course:");
                int mark = Operations.checkInputs(scan, 10, 20);
                passedCourses.put(Course.getCourses().get(option - 1), mark + "");
                System.out.println("Added successfully to the passed courses!");
                Operations.pressEnter();
            }
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            makePassedCourses(scan, passedCourses);
        }
    }

    public void makeNeededCoursesAsPassed(Scanner scan, HashMap<Course, String> passedCourses) {
        HashMap<Course, String> hashMap = passedCourses;
        for ( Course course1 : hashMap.keySet() ) {
            for ( Course course2 : course1.getNeededCourses() ) {
                if ( !passedCourses.containsKey(course2) ) {
                    System.out.format("The course \"%s\" is a needed course for the course \"%s\".", course2.getName(),
                            course1.getName());
                    System.out.println(" Enter the student's mark at this course:");
                    int mark = Operations.checkInputs(scan, 10, 20);
                    passedCourses.put(course2, mark+"");
                }
            }
        }
    }

    @Override
    public void submit(Scanner scan, Handler handler, Admin account) {
        String id = setUserName(scan);
        String password = setPassword(scan);
        String name = setName(scan);
        String familyName = setFamilyName(scan);
        ArrayList<Group> groups = new ArrayList<>();
        String units = setUnits(scan);
        HashMap<Course, String> passedCourses = new HashMap<>();
        makePassedCourses(scan, passedCourses);
        makeNeededCoursesAsPassed(scan, passedCourses);
        Student student = new Student(id, password, name, familyName, groups, units, passedCourses);
        students.add(student);
        accounts.put(id, student);
        System.out.println("Student submitted successfully!");
        Operations.pressEnter();
        account.menuOptions(scan, handler, account);
    }

    @Override
    public void settingGroupConditions(Master master, Group group, Scanner scan, Handler handler, Admin admin) {
        Student student = (Student) master;
        try {
            Exceptions.alreadyInTheGroupsList(student, group);
            Exceptions.hasPassed(student, group);
            Exceptions.doesNotMeetTheNeeds(student, group);
            Exceptions.noEnoughUnits(student, group);
            Exceptions.hasOverlap(student, group);
            Exceptions.noCapacityLeft(group);
            student.groups.add(group);
            student.setUnits((Integer.parseInt(student.getUnits())-Integer.parseInt(group.getUnits()))+"");
            group.setCapacity((Integer.parseInt(group.getCapacity())-1)+"");
            ArrayList<Student> students = (ArrayList<Student>) group.getStudents().clone();
            students.add(student);
            group.setStudents(students);
            System.out.println("Group has been added successfully to the groups!");
            Operations.pressEnter();
            master.setGroups(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            master.setGroups(scan, handler, admin);
        }
    }

    @Override
    public void choose(Scanner scan, Handler handler, Admin admin) {
        String id = scan.nextLine();
        for ( Student student : students ) {
            if ( student.getUserName().equals(id) ) {
                student.setGroups(scan, handler, admin);
            }
        }
        System.out.println("No students with this ID found! Please try again.");
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    public static void showAllStudents() {
        for ( Student student : students ) {
            System.out.format("Name : %s, Family name : %s, ID : %s, Password : %s, Term units : %s\n",
                    student.getName(), student.getFamilyName(), student.getUserName(), student.getPassword(),
                    student.units);
            if ( !student.passedCourses.isEmpty() ) {
                System.out.println("Courses that this student passed:");
                for ( Course course : student.passedCourses.keySet() ) {
                    System.out.format("Course number : %s, Course name : %s, Units value : %s, Mark : %s\n",
                            course.getNumber(), course.getName(), course.getUnits(), student.passedCourses.get(course));
                }
            }
            if ( !student.getGroups().isEmpty() ) {
                System.out.println("Course groups that this student has:");
                for ( Group group : student.getGroups() ) {
                    group.print();
                }
            }
            System.out.println("\n--------------------------------------------------");
        }
    }

    public void checkPassedCoursesAndAverage(Scanner scan, Handler handler, Admin admin) {
        double sum = 0;
        System.out.println("Here is the list of the courses you have passed:");
        for ( Course course : this.passedCourses.keySet() ) {
            System.out.format("Course name : %s, Course number : %s, Course units : %s, Mark : %s\n", course.getName(),
                    course.getNumber(), course.getUnits(), this.passedCourses.get(course));
            sum += Double.parseDouble(this.passedCourses.get(course));
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Here is the list of groups you have right now:");
        for ( Group group : Group.groups ) {
            for ( Student student : group.getMarks().keySet() ) {
                if ( student.getUserName().equals(this.getUserName()) ) {
                    System.out.format("Course name : %s, Course number : %s, Course units : %s, Group number : %s, " +
                            "Mark : %s\n", group.getName(), group.getNumber(), group.getUnits(), group.getGroupNumber(),
                            group.getMarks().get(student));
                }
            }
        }
        System.out.println("--------------------------------------------------");
        System.out.format("Average of passed courses : %.2f\n", (sum/this.passedCourses.size()));
        Operations.pressEnter();
        menuOptions(scan, handler, admin);
    }

    public void writeRequestToTheFile(Scanner scan, Handler handler, Student student, Group group) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(student.getUserName()+".txt"));
            writer.write(group.getName()+"-");
            writer.write(group.getGroupNumber());
            writer.close();
            System.out.println("Request submitted successfully!");
            Operations.pressEnter();
            requests(scan, handler, student);
        } catch ( IOException ex ) {
            System.out.println(ex.getMessage());
        }
    }

    public void submitRequest(Scanner scan, Handler handler, Student student) {
        System.out.println("Choose the group you wish to remove:");
        for ( Group group : student.groups ) {
            System.out.format("%d. Course name: %s, Course number : %s, Group number : %s\n", student.groups.indexOf(group)+1,
                    group.getName(), group.getNumber(), group.getGroupNumber());
        }
        System.out.format("%d. Cancel requesting\n", student.groups.size()+1);
        int option = Operations.checkInputs(scan, 1, student.groups.size()+1);
        if ( option == student.groups.size()+1 ) {
            System.out.println("Request cancelled.");
            Operations.pressEnter();
            requests(scan, handler, student);
        } else {
            writeRequestToTheFile(scan, handler, student, student.groups.get(option-1));
        }
    }

    public void checkResults(Scanner scan, Handler handler, Student student) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(student.getUserName() + ".txt"));
            System.out.println(reader.readLine());
            reader.close();
            Operations.pressEnter();
            requests(scan, handler, student);
        } catch ( IOException ex ) {
            System.out.println("No results available at the moment!");
            Operations.pressEnter();
            requests(scan, handler, student);
        }
    }

    public void requests(Scanner scan, Handler handler, Student student) {
        System.out.println("Choose your option:\n1-New request\n2-Result\n3-Back");
        int option = Operations.checkInputs(scan, 1, 3);
        if ( option == 1 ) {
            submitRequest(scan, handler, student);
        } else if ( option == 2 ) {
            checkResults(scan, handler, student);
        } else {
            student.menuOptions(scan, handler, student);
        }
    }

    public void removeGroup(Group group) {
        this.groups.remove(group);
    }

    public void orderMeal(Scanner scan, Handler handler, Student student) {
        System.out.println("Choose the meal you wish to order:\n1-Breakfast\n2-Lunch\n3-Exit");
        int option = Operations.checkInputs(scan, 1, 3);
        Order order;
        if ( option == 1 ) {
            order = new Breakfast();
            order.getOrder(scan, handler, student);
        } else if ( option == 2 ) {
            order = new Lunch();
            order.getOrder(scan, handler, student);
        } else {
            orderMenu(scan, handler, student);
        }
    }

    public void checkOrders(Scanner scan, Handler handler, Student student) {
        for ( Order order : orders ) {
            order.showOrder();
            System.out.println("--------------------------------------------------");
        }
        Operations.pressEnter();
        menuOptions(scan, handler, student);
    }

    public void orderMenu(Scanner scan, Handler handler, Student student) {
        System.out.println("Enter your choice:\n1-Order a meal\n2-Check orders\n3-Exit");
        int option = Operations.checkInputs(scan, 1, 3);
        Menu.OrderMenu options[] = Menu.OrderMenu.values();
        switch ( options[option-1] ) {
            case ORDER_MEAL:
                orderMeal(scan, handler, student);
                break;
            case CHECK_ORDERS:
                checkOrders(scan, handler, student);
                break;
            default:
                menuOptions(scan, handler, student);
        }
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    @Override
    public void menuOptions(Scanner scan, Handler handler, Admin admin) {
        Student student = (Student) admin;
        Operations.showStudentMenuMessage();
        Menu.StudentMenu options[] = Menu.StudentMenu.values();
        int option = Operations.checkInputs(scan, 1, 6);
        switch ( options[option-1] ) {
            case SET_GROUPS:
                student.setGroups(scan, handler, student);
                break;
            case WEEKLY_SCHEDULE:
                student.showWeeklySchedule(scan, handler, admin);
                break;
            case CHECK_PASSED_COURSES_AND_MARKS:
                student.checkPassedCoursesAndAverage(scan, handler, admin);
                break;
            case REQUEST_TO_REMOVE_A_GROUP:
                requests(scan, handler, student);
                break;
            case ORDER_FOOD:
                student.orderMenu(scan, handler, student);
                break;
            default:
                Main.mainMenu(scan, handler);
        }
    }
}