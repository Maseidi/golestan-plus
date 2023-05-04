package ir.ac.kntu;

import java.util.*;

public class Master extends Admin {

    private static ArrayList<Master> masters = new ArrayList<>();

    private String name;

    private String familyName;

    protected ArrayList<Group> groups;

    public Master() {}

    public Master(String userName, String password, String name, String familyName, ArrayList<Group> groups) {
        super(userName, password);
        this.name = name;
        this.familyName = familyName;
        this.groups = groups;
    }

    public static ArrayList<Master> getMasters() {
        return new ArrayList<>(masters);
    }

    public static void setMasters(ArrayList<Master> masters) {
        Master.masters = new ArrayList<>(masters);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public ArrayList<Group> getGroups() {
        return new ArrayList<>(groups);
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = new ArrayList<>(groups);
    }

    @Override
    public String setUserName(Scanner scan) {
        try {
            System.out.println("Enter ID :");
            String id = scan.nextLine();
            Exceptions.idDigitsCheck(id);
            Exceptions.repeatedUserName(id);
            return id;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setUserName(scan);
        }
    }

    public String setName(Scanner scan) {
        try {
            System.out.println("Enter name:");
            String name = scan.nextLine();
            Exceptions.nameExceptions(name);
            return name;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setName(scan);
        }
    }

    public String setFamilyName(Scanner scan) {
        try {
            System.out.println("Enter family name:");
            String familyName = scan.nextLine();
            Exceptions.nameExceptions(familyName);
            return familyName;
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            return setName(scan);
        }
    }

    @Override
    public void submit(Scanner scan, Handler handler, Admin account) {
        String id = setUserName(scan);
        String password = setPassword(scan);
        String name = setName(scan);
        String familyName = setFamilyName(scan);
        ArrayList<Group> groups = new ArrayList<>();
        Master master = new Master(id, password, name, familyName, groups);
        masters.add(master);
        accounts.put(id, master);
        System.out.println("Master submitted successfully!");
        Operations.pressEnter();
        account.menuOptions(scan, handler, account);
    }

    public Group selectGroup(Scanner scan) {
        Group.showAllGroups();
        System.out.format("%d. Finish setting the groups and apply changes.\n", Group.groups.size()+1);
        int option = Operations.checkInputs(scan, 1, Group.groups.size()+1);
        if ( option == Group.groups.size()+1 ) {
            return null;
        } else {
            return Group.groups.get(option-1);
        }
    }

    public void settingGroupConditions(Master master, Group group, Scanner scan, Handler handler, Admin admin) {
        try {
            Exceptions.groupAlreadyHasMaster(group);
            Exceptions.hasOverlap(master, group);
            master.groups.add(group);
            ArrayList<Master> masters = new ArrayList<>();
            masters.add(master);
            group.setMasters(masters);
            System.out.println("Group has been added successfully to the list of course groups!");
            Operations.pressEnter();
            master.setGroups(scan, handler, admin);
        } catch ( Exceptions ex ) {
            System.out.println(ex.getMessage());
            Operations.pressEnter();
            master.setGroups(scan, handler, admin);
        }
    }

    @Override
    public void setGroups(Scanner scan, Handler handler, Admin admin) {
        while (true) {
            Group group = selectGroup(scan);
            if ( group == null ) {
                break;
            }
            settingGroupConditions(this, group, scan, handler, admin);
        }
        System.out.println("Changes applied successfully!");
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    public void choose(Scanner scan, Handler handler, Admin admin) {
        String id = scan.nextLine();
        for ( Master master : getMasters() ) {
            if ( master.getUserName().equals(id) ) {
                master.setGroups(scan, handler, admin);
            }
        }
        System.out.println("No masters with this ID found! Please try again.");
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    public static void showAllMasters() {
        for ( Master master : masters ) {
            System.out.format("Name : %s, Family name : %s, ID : %s, Password : %s\n", master.name, master.familyName,
                    master.getUserName(), master.getPassword());
            if ( !master.groups.isEmpty() ) {
                System.out.println("Course groups that this master has:");
                for ( Group group : master.groups ) {
                    group.print();
                }
            }
        }
    }

    public void showWeeklySchedule(Scanner scan, Handler handler, Admin admin) {
        for ( int i = 0; i < 5 ; i++ ) {
            for ( Group group : this.groups ) {
                for ( Time time : group.getSessions() ) {
                    if ( i == 0 && time.getDayOfWeek().equals("Saturday") ) {
                        System.out.format("Course name : %s, Time : %s\n", group.getName(), time.showTime());
                    } else if ( i == 1 && time.getDayOfWeek().equals("Sunday") ) {
                        System.out.format("Course name : %s, Time : %s\n", group.getName(), time.showTime());
                    } else if ( i == 2 && time.getDayOfWeek().equals("Monday") ) {
                        System.out.format("Course name : %s, Time : %s\n", group.getName(), time.showTime());
                    } else if ( i == 3 && time.getDayOfWeek().equals("Tuesday") ) {
                        System.out.format("Course name : %s, Time : %s\n", group.getName(), time.showTime());
                    } else if ( i == 4 && time.getDayOfWeek().equals("Wednesday") ) {
                        System.out.format("Course name : %s, Time : %s\n", group.getName(), time.showTime());
                    }
                }
            }
        }
        Operations.pressEnter();
        admin.menuOptions(scan, handler, admin);
    }

    public void checkGroups(Scanner scan, Handler handler, Admin admin) {
        for ( Group group : this.groups ) {
            group.print();
            group.printSessions();
        }
        Operations.pressEnter();
        menuOptions(scan, handler, admin);
    }

    public Group chooseGroup(Scanner scan, Handler handler, Admin admin) {
        System.out.println("Choose the group:");
        for ( Group group : this.groups ) {
            System.out.format("%d. Course name: %s, Course number : %s, Group number : %s\n", this.groups.indexOf(group)+1,
                    group.getName(), group.getNumber(), group.getGroupNumber());
        }
        System.out.format("%d. Cancel choosing the group\n", this.groups.size()+1);
        int option = Operations.checkInputs(scan, 1, this.groups.size()+1);
        if ( option == this.groups.size()+1 ) {
            System.out.println("Process cancelled.");
            Operations.pressEnter();
            return null;
        } else {
            return groups.get(option-1);
        }
    }

    public void checkStudents(Scanner scan, Handler handler, Admin admin) {
        Group group = chooseGroup(scan, handler, admin);
        if ( group == null ) {
            menuOptions(scan, handler, admin);
        } else {
            for ( Student student : group.getStudents() ) {
                System.out.format("%d. Student name : %s, Student family name : %s\n",
                        group.getStudents().indexOf(student) + 1, student.getName(),
                        student.getFamilyName());
            }
        }
        Operations.pressEnter();
        menuOptions(scan, handler, admin);
    }

    public Student chooseStudent(Scanner scan, Handler handler, Admin admin, Group group) {
        for ( Student student : group.getStudents() ) {
            System.out.format("%d. Student name : %s, Student family name : %s\n",
                    group.getStudents().indexOf(student) + 1, student.getName(),
                    student.getFamilyName());
        }
        System.out.format("%d. Cancel choosing students:\n", group.getStudents().size()+1);
        int option = Operations.checkInputs(scan, 1, group.getStudents().size()+1);
        if ( option == group.getStudents().size()+1) {
            System.out.println("Process cancelled.");
            Operations.pressEnter();
            return null;
        } else {
            return group.getStudents().get(option-1);
        }
    }

    public void setMarks(Scanner scan, Handler handler, Admin admin) {
        Group group = chooseGroup(scan, handler, admin);
        if ( group == null ) {
            menuOptions(scan, handler, admin);
        } else {
            Student student = chooseStudent(scan, handler, admin, group);
            if ( student == null ) {
                menuOptions(scan, handler, admin);
            } else {
                System.out.println("Enter the student's mark:");
                int mark = Operations.checkInputs(scan, 1, 20);
                group.addMark(student, mark+"");
                System.out.println("Mark submitted successfully!");
                Operations.pressEnter();
                setMarks(scan, handler, admin);
            }
        }
    }

    @Override
    public void menuOptions(Scanner scan, Handler handler, Admin admin) {
        Master master = (Master) admin;
        Operations.showMasterMenuMessage();
        Menu.MasterMenu options[] = Menu.MasterMenu.values();
        int option = Operations.checkInputs(scan, 1, 5);
        switch ( options[option-1] ) {
            case CHECK_GROUPS:
                master.checkGroups(scan, handler, admin);
                break;
            case CHECK_STUDENTS:
                master.checkStudents(scan, handler, admin);
                break;
            case WEEKLY_SCHEDULE:
                master.showWeeklySchedule(scan, handler, admin);
                break;
            case SET_MARKS:
                master.setMarks(scan, handler, admin);
                break;
            default:
                Main.mainMenu(scan, handler);
        }
    }

}