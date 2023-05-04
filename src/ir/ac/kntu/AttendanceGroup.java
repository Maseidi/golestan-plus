package ir.ac.kntu;

import java.util.*;

public class AttendanceGroup extends Group {

    private static ArrayList<AttendanceGroup> attendanceGroups = new ArrayList<>();

    private final String type = "Attendance";

    private String classNumber;

    public AttendanceGroup() {}

    public AttendanceGroup(String number, String name, String units, ArrayList<Course> neededCourses,
                           String groupNumber, ArrayList<Student> students, ArrayList<Time> sessions, String capacity,
                           String termNumber, ArrayList<Master> masters, HashMap<Student, String> marks, 
                           String classNumber) {
        super(number, name, units, neededCourses, groupNumber, students, sessions, capacity, termNumber, masters, marks);
        this.classNumber = classNumber;
    }

    public static ArrayList<AttendanceGroup> getAttendanceGroups() {
        return new ArrayList<>(attendanceGroups);
    }

    public static void setAttendanceGroups(ArrayList<AttendanceGroup> attendanceGroups) {
        AttendanceGroup.attendanceGroups = new ArrayList<>(attendanceGroups);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(String classNumber) {
        this.classNumber = classNumber;
    }

    public static String setClassNumber(Scanner scan, String termNumber, ArrayList<Time> sessions) {
        try {
            System.out.println("Enter the class number (100 to 999):");
            int classNumber = Operations.checkInputs(scan, 100, 999);
            Exceptions.classIsOverlap(classNumber, termNumber, sessions);
            return classNumber + "";
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setClassNumber(scan, termNumber, sessions);
        }
    }

    @Override
    public void submit(Scanner scan, Handler handler, Admin admin) {
        try {
            Exceptions.noCourseExist();
            Course course = selectCourse(scan);
            String groupNumber = setGroupNumber(course, scan);
            ArrayList<Student> students = new ArrayList<>();
            ArrayList<Time> sessions = makeSessions(scan);
            String capacity = setCapacity(scan);
            String termNumber = setTermNumber(scan);
            ArrayList<Master> masters = new ArrayList<>();
            HashMap<Student, String> marks = new HashMap<>();
            String classNumber = setClassNumber(scan, termNumber, sessions);
            AttendanceGroup attendanceGroup = new AttendanceGroup(course.getNumber(), course.getName(), course.getUnits(),
                    course.getNeededCourses(), groupNumber, students, sessions, capacity, termNumber, masters, marks,
                    classNumber);
            groups.add(attendanceGroup);
            attendanceGroups.add(attendanceGroup);
            System.out.println("Attendance group submitted successfully!");
            Operations.pressEnter();
            admin.menuOptions(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            admin.menuOptions(scan, handler, admin);
        }
    }

    @Override
    public void deleteGroup(Scanner scan, Handler handler, Admin admin) {
        groups.remove(this);
        attendanceGroups.remove(this);
        removeFromStudentsAndMasters(this);
        System.out.println("Attendance course group removed successfully!");
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    @Override
    public void editGroupType(Scanner scan, Handler handler, Admin admin) {
        OnlineGroup onlineGroup = new OnlineGroup(this.getNumber(), this.getName(), this.getUnits(),
                this.getNeededCourses(), this.getGroupNumber(), this.getStudents(), this.getSessions(),
                this.getCapacity(), this.getTermNumber(), this.getMasters(), this.getMarks());
        groups.remove(this);
        attendanceGroups.remove(this);
        removeFromStudentsAndMasters(this);
        groups.add(onlineGroup);
        ArrayList<OnlineGroup> arrayList = (ArrayList<OnlineGroup>) OnlineGroup.getOnlineGroups().clone();
        arrayList.add(onlineGroup);
        OnlineGroup.setOnlineGroups(arrayList);
        System.out.println("The course type changed successfully!");
        Operations.pressEnter();
        editGroup(scan, handler, admin);
    }
}