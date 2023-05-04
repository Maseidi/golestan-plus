package ir.ac.kntu;

import java.util.*;

public class OnlineGroup extends Group {

    private static ArrayList<OnlineGroup> onlineGroups = new ArrayList<>();

    private final String type = "Online";

    private final String classNumber = "-";

    public OnlineGroup() {}

    public OnlineGroup(String number, String name, String units, ArrayList<Course> neededCourses, String groupNumber,
                       ArrayList<Student> students, ArrayList<Time> sessions, String capacity, String termNumber,
                       ArrayList<Master> masters, HashMap<Student, String> marks) {
        super(number, name, units, neededCourses, groupNumber, students, sessions, capacity, termNumber, masters, marks);
    }

    public static ArrayList<OnlineGroup> getOnlineGroups() {
        return new ArrayList<>(onlineGroups);
    }

    public static void setOnlineGroups(ArrayList<OnlineGroup> onlineGroups) {
        OnlineGroup.onlineGroups = new ArrayList<>(onlineGroups);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getClassNumber() {
        return classNumber;
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
            OnlineGroup onlineGroup = new OnlineGroup(course.getNumber(), course.getName(), course.getUnits(),
                    course.getNeededCourses(), groupNumber, students, sessions, capacity, termNumber, masters, marks);
            groups.add(onlineGroup);
            onlineGroups.add(onlineGroup);
            System.out.println("Online group submitted successfully!");
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
        onlineGroups.remove(this);
        removeFromStudentsAndMasters(this);
        System.out.println("Online course group removed successfully!");
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    @Override
    public void editGroupType(Scanner scan, Handler handler, Admin admin) {
        String classNumber = AttendanceGroup.setClassNumber(scan, this.classNumber, this.getSessions());
        AttendanceGroup attendanceGroup = new AttendanceGroup(this.getNumber(), this.getName(), this.getUnits(),
                this.getNeededCourses(), this.getGroupNumber(), this.getStudents(), this.getSessions(),
                this.getCapacity(), this.getTermNumber(), this.getMasters(), this.getMarks(), classNumber);
        groups.remove(this);
        onlineGroups.remove(this);
        removeFromStudentsAndMasters(this);
        groups.add(attendanceGroup);
        ArrayList<AttendanceGroup> arrayList = (ArrayList<AttendanceGroup>) AttendanceGroup.getAttendanceGroups().clone();
        arrayList.add(attendanceGroup);
        AttendanceGroup.setAttendanceGroups(arrayList);
        System.out.println("The course type changed successfully!");
        Operations.pressEnter();
        editGroup(scan, handler, admin);
    }
}