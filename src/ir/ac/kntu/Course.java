package ir.ac.kntu;

import java.util.*;

public class Course {

    protected static ArrayList<Course> courses = new ArrayList<>();

    private String number;

    private String name;

    private String units;

    private ArrayList<Course> neededCourses = new ArrayList<>();

    public Course() {}

    public Course(String number, String name, String units, ArrayList<Course> neededCourses) {
        this.number = number;
        this.name = name;
        this.units = units;
        this.neededCourses = neededCourses;
    }

    public static ArrayList<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public static void setCourses(ArrayList<Course> courses) {
        Course.courses = new ArrayList<>(courses);
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public ArrayList<Course> getNeededCourses() {
        return new ArrayList<>(neededCourses);
    }

    public void setNeededCourses(ArrayList<Course> neededCourses) {
        this.neededCourses = new ArrayList<>(neededCourses);
    }

    public String setNumber(Scanner scan) {
        try {
            System.out.println("Enter the course number:");
            String number = scan.nextLine();
            Exceptions.courseNumberExceptions(number);
            return number;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setNumber(scan);
        }
    }

    public String setName(Scanner scan) {
        try {
            System.out.println("Enter the course name:");
            String name = scan.nextLine();
            Exceptions.nameExceptionForCourses(name);
            return name;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setName(scan);
        }
    }

    public void makeNeededCourses(Scanner scan, ArrayList<Course> neededCourses) {
        try {
            while (true) {
                System.out.println("Choose your option to add as the course's needed courses:");
                Course.showAllCourses();
                System.out.format("%d. Submit setting the needed courses\n", courses.size() + 1);
                int option = Operations.checkInputs(scan, 1, courses.size() + 1);
                if (option == courses.size() + 1) {
                    break;
                } else {
                    Exceptions.alreadyInTheNeededCourses(neededCourses, courses.get(option-1));
                    neededCourses.add(courses.get(option-1));
                    System.out.println("Added successfully!");
                    Operations.pressEnter();
                }
            }
            System.out.println("Needed courses submitted successfully!");
            Operations.pressEnter();
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            makeNeededCourses(scan, neededCourses);
        }
    }

    public void deepNeededCourses(ArrayList<Course> neededCourses) {
        ArrayList<Course> arrayList = (ArrayList<Course>) neededCourses.clone();
        for ( Course course1 : arrayList ) {
            for ( Course course2 : course1.neededCourses ) {
                if ( !neededCourses.contains(course2) ) {
                    neededCourses.add(course2);
                }
            }
        }
    }

    public void submit(Scanner scan, Handler handler, Admin admin) {
        String number = setNumber(scan);
        String name = setName(scan);
        System.out.println("Enter unit values (1 to 4):");
        int units = Operations.checkInputs(scan, 1, 4);
        ArrayList<Course> neededCourses = new ArrayList<>();
        makeNeededCourses(scan, neededCourses);
        deepNeededCourses(neededCourses);
        Course course = new Course(number, name, units+"", neededCourses);
        courses.add(course);
        System.out.println("Course submitted successfully!");
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    public static void showAllCourses() {
        for ( Course course1 : courses ) {
            System.out.format("%d. Course number : %s, Course name : %s, Units value : %s\n",
                        courses.indexOf(course1)+1, course1.number, course1.name, course1.units);
            if ( !course1.neededCourses.isEmpty() ) {
                System.out.println("Needed courses for this course:");
                for ( Course course2 : course1.neededCourses ) {
                    System.out.format("Course number : %s, Course name : %s, Units value : %s\n", course2.number,
                            course2.name, course2.units);
                }
            }
            System.out.println("--------------------------------------------------");
        }
    }
}