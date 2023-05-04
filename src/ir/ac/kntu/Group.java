package ir.ac.kntu;

import java.util.*;

public class Group extends Course {

    protected static ArrayList<Group> groups = new ArrayList<>();

    private String groupNumber;

    private ArrayList<Student> students = new ArrayList<>();

    private ArrayList<Time> sessions = new ArrayList<>();

    private String capacity;

    private String termNumber;

    private ArrayList<Master> masters = new ArrayList<>();
    
    private HashMap<Student, String> marks = new HashMap<>();

    public Group() {}

    public Group(String number, String name, String units, ArrayList<Course> neededCourses, String groupNumber,
                 ArrayList<Student> students, ArrayList<Time> sessions, String capacity, String termNumber,
                 ArrayList<Master> masters, HashMap<Student, String> marks ) {
        super(number, name, units, neededCourses);
        this.groupNumber = groupNumber;
        this.students = students;
        this.sessions = sessions;
        this.capacity = capacity;
        this.termNumber = termNumber;
        this.masters = masters;
        this.marks = marks;
    }

    public static ArrayList<Group> getGroups() {
        return new ArrayList<>(groups);
    }

    public static void setGroups(ArrayList<Group> groups) {
        Group.groups = new ArrayList<>(groups);
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students);
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = new ArrayList<>(students);
    }

    public ArrayList<Time> getSessions() {
        return new ArrayList<>(sessions);
    }

    public void setSessions(ArrayList<Time> sessions) {
        this.sessions = new ArrayList<>(sessions);
    }

    public String getTermNumber() {
        return termNumber;
    }

    public void setTermNumber(String termNumber) {
        this.termNumber = termNumber;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Master> getMasters() {
        return new ArrayList<>(masters);
    }

    public void setMasters(ArrayList<Master> masters) {
        this.masters = new ArrayList<>(masters);
    }

    public HashMap<Student, String> getMarks() {
        return new HashMap<>(marks);
    }

    public void setMarks(HashMap<Student, String> marks) {
        this.marks = new HashMap<>(marks);
    }

    public String getType() {
        return "";
    }

    public String getClassNumber() {
        return "";
    }

    public static Course selectCourse(Scanner scan) {
        System.out.println("Choose the course from the list below:");
        Course.showAllCourses();
        int option = Operations.checkInputs(scan, 1, courses.size()+1);
        return courses.get(option-1);
    }

    public String setGroupNumber(Course course, Scanner scan) {
        try {
            System.out.println("Enter the group number:");
            String groupNumber = scan.nextLine();
            Exceptions.repeatedGroupNumber(course, groupNumber);
            return groupNumber;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setGroupNumber(course, scan);
        }
    }

    public ArrayList<Time> makeSessions(Scanner scan) {
        ArrayList<Time> sessions = new ArrayList<>();
        System.out.println("Enter the number of sessions in a week:");
        int number = Integer.parseInt(scan.nextLine());
        for ( int i = 0; i < number; i++ ) {
            System.out.format("Enter the time factors for the session %d of the week\n", i+1);
            Time time = Time.makeTime(scan);
            sessions.add(time);
            System.out.println("Session added successfully!");
            Operations.pressEnter();
        }
        System.out.println("Sessions for this group submitted successfully!");
        Operations.pressEnter();
        return sessions;
    }

    public String setCapacity(Scanner scan) {
        System.out.println("Enter the group's capacity: (0 to 80)");
        int capacity = Operations.checkInputs(scan, 0, 80);
        return capacity+"";
    }

    public String setTermNumber(Scanner scan) {
        System.out.println("Enter the termNumber: (1 to 8)");
        int termNumber = Operations.checkInputs(scan, 1, 8);
        return termNumber+"";
    }

    public void print() {
        System.out.format("Course number : %s, Course name : %s, Units value : %s, Group number : %s, Capacity : %s," +
                        " Term number : %s, Type : %s, Class number : %s\n", getNumber(), getName(), getUnits(),
                getGroupNumber(), getCapacity(), getTermNumber(), getType(), getClassNumber());
    }

    public void printSessions() {
        for ( Time time : this.getSessions() ) {
            System.out.println(time.showTime());
        }
    }

    public static void showAllGroups() {
        for ( Group group : groups ) {
            System.out.format("%d. ", groups.indexOf(group)+1);
            group.print();
            System.out.println("Sessions in the week:");
            group.printSessions();
            System.out.println("--------------------------------------------------");
        }
    }

    public void deleteGroup(Scanner scan, Handler handler, Admin admin) {}

    public void removeFromStudentsAndMasters(Group group) {
        for ( Master master : Master.getMasters() ) {
            if ( master.getGroups().contains(group) ) {
                ArrayList<Group> arrayList = master.getGroups();
                arrayList.remove(group);
                master.setGroups(arrayList);
            }
        }
    }

    public void editCourse(Scanner scan, Handler handler, Admin admin) {
        try {
            Course course = selectCourse(scan);
            Exceptions.notNewCourse(this, course);
            this.setName(course.getName());
            this.setNumber(course.getNumber());
            this.setUnits(course.getUnits());
            System.out.println("The group's course changed successfully!");
            Operations.pressEnter();
            editGroup(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            editCourse(scan, handler, admin);
        }
    }

    public void editGroupNumber(Scanner scan, Handler handler, Admin admin) {
        try {
            System.out.println("Enter the new group number:");
            String groupNumber = scan.nextLine();
            Exceptions.notNewGroupNumber(this, groupNumber);
            Exceptions.notNewGroup(groupNumber, this.getName());
            this.setGroupNumber(groupNumber);
            System.out.println("Group number changed successfully!");
            Operations.pressEnter();
            editGroup(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            editGroupNumber(scan, handler, admin);
        }
    }

    public void editGroupCapacity(Scanner scan, Handler handler, Admin admin) {
        try {
            String capacity = setCapacity(scan);
            Exceptions.notNewCapacity(this, capacity);
            this.setCapacity(capacity);
            System.out.println("Group capacity changed successfully!");
            Operations.pressEnter();
            editGroup(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            editGroupCapacity(scan, handler, admin);
        }
    }

    public void editGroupTermNumber(Scanner scan, Handler handler, Admin admin) {
        try {
            String termNumber = setTermNumber(scan);
            Exceptions.notNewTermNumber(this, termNumber);
            this.setTermNumber(termNumber);
            System.out.println("Group term number changed successfully!");
            Operations.pressEnter();
            editGroup(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            editGroupTermNumber(scan, handler, admin);
        }
    }

    public void editGroupSessions(Scanner scan, Handler handler, Admin admin) {
        System.out.println("Enter the new sessions' times:");
        ArrayList<Time> sessions = makeSessions(scan);
        this.setSessions(sessions);
        System.out.println("Group sessions changed successfully!");
        Operations.pressEnter();
        editGroup(scan, handler, admin);
    }

    public void removeMaster(Scanner scan, Handler handler, Admin admin) {
        if ( this.masters.size() == 0 ) {
            System.out.println("The course doesn't have master!");
            Operations.pressEnter();
            editGroup(scan, handler, admin);
        }
        Master master = this.masters.get(0);
        this.masters.remove(0);
        ArrayList<Group> groups = (ArrayList<Group>) master.getGroups().clone();
        groups.remove(this);
        master.setGroups(groups);
        System.out.println("Master removed successfully!");
        Operations.pressEnter();
        editGroup(scan, handler, admin);
    }

    public void chooseStudent(Scanner scan, Handler handler, Admin admin) {
        System.out.println("Enter the student's ID that you wish to remove from this group:");
        String id = scan.nextLine();
        for ( Student student : this.getStudents() ) {
            if ( student.getUserName().equals(id) ) {
                removeStudent(student, scan, handler, admin);
            }
        }
        System.out.println("No students in this group have this ID! Please try again.");
        Operations.pressEnter();
        editGroup(scan, handler, admin);
    }

    public void removeStudent(Student student, Scanner scan, Handler handler, Admin admin) {
        ArrayList<Group> groups = (ArrayList<Group>)student.getGroups().clone();
        groups.remove(this);
        student.setGroups(groups);
        this.students.remove(student);
        System.out.println("Student removed successfully!");
        Operations.pressEnter();
        editGroup(scan, handler, admin);
    }

    public void addMark(Student student, String mark) {
        if ( marks.containsKey(student) ) {
            System.out.println("This student's mark is already defined!");
            Operations.pressEnter();
        } else {
            marks.put(student, mark);
        }
    }

    public void editGroupType(Scanner scan, Handler handler, Admin admin) {}

    public void removeStudent(Student student) {
        this.students.remove(student);
    }

    public void editGroup(Scanner scan, Handler handler, Admin admin) {
        Operations.editCourseGroupMenu();
        Menu.EditGroupMenu options[] = Menu.EditGroupMenu.values();
        int option = Operations.checkInputs(scan, 1, 9);
        switch ( options[option-1] ) {
            case EDIT_COURSE:
                editCourse(scan, handler, admin);
                break;
            case EDIT_GROUP_NUMBER:
                editGroupNumber(scan, handler, admin);
                break;
            case EDIT_CAPACITY:
                editGroupCapacity(scan, handler, admin);
                break;
            case EDIT_TERM_NUMBER:
                editGroupTermNumber(scan, handler, admin);
                break;
            case REMOVE_MASTER:
                removeMaster(scan, handler, admin);
                break;
            case REMOVE_STUDENT:
                chooseStudent(scan, handler, admin);
                break;
            case EDIT_SESSIONS:
                editGroupSessions(scan, handler, admin);
                break;
            case EDIT_TYPE:
                editGroupType(scan, handler, admin);
                break;
            default:
                System.out.println("Changes applied successfully!");
                Operations.pressEnter();
                admin.menuOptions(scan, handler, admin);
        }
    }
}