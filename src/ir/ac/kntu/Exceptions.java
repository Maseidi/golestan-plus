package ir.ac.kntu;

import java.util.*;

public class Exceptions extends Exception {

    public Exceptions(String message) {
        super(message);
    }

    public static void repeatedUserName(String userName) throws Exceptions {
        if ( Admin.getAccounts().containsKey(userName) ) {
            throw new Exceptions("This username is already taken! Please try again");
        }
    }


    public static void logInExceptions(String userName, String password) throws Exceptions {
        if ( !Admin.getAccounts().containsKey(userName) ) {
            throw new Exceptions("This username does not exist! Please try again.");
        } else if ( !Admin.getAccounts().get(userName).getPassword().equals(password) ) {
            throw new Exceptions("The password is wrong! Please try again.");
        }
    }

    public static void courseNumberExceptions(String number) throws Exceptions {
        if ( !number.matches("\\d{2}") ) {
            throw new Exceptions("Course number must be a 2 digits number! Please try again.");
        }
        for ( Course course : Course.courses ) {
            if ( course.getNumber().equals(number) ) {
                throw new Exceptions("This course number is already defined! Please try again.");
            }
        }
    }

    public static void nameExceptionForCourses(String name) throws Exceptions {
        for ( Course course : Course.courses ) {
            if ( course.getName().equals(name) ) {
                throw new Exceptions("This course name is already defined! Please try again.");
            }
        }
    }

    public static void nameExceptions(String name) throws Exceptions {
        if ( !name.matches("[A-Z][a-z]*") ) {
            throw new Exceptions("Names must start with a capital letter! Please try again.");
        }
    }

    public static void alreadyInTheNeededCourses(ArrayList<Course> neededCourses, Course course) throws Exceptions {
        if ( neededCourses.contains(course) ) {
            throw new Exceptions("The course is already added to the needed courses list.");
        }
    }

    public static void repeatedGroupNumber(Course course, String groupNumber) throws Exceptions {
        for ( Group group : Group.getGroups() ) {
            if ( group.getName().equals(course.getName()) && group.getGroupNumber().equals(groupNumber) ) {
                throw new Exceptions("This course group is already defined! Please try again.");
            }
        }
    }

    public static void classIsOverlap(int classNumber, String termNumber, ArrayList<Time> sessions)
            throws Exceptions {
        for ( AttendanceGroup attendanceGroup : AttendanceGroup.getAttendanceGroups() ) {
            if ( attendanceGroup.getClassNumber().equals(classNumber+"")
                    && attendanceGroup.getTermNumber().equals(termNumber) ) {
                for ( Time time1 : attendanceGroup.getSessions() ) {
                    for ( Time time2 : sessions ) {
                        throw new Exceptions("This class is reserved at the sessions for this class! " +
                                "Please choose another class instead.");
                    }
                }
            }
        }
    }

    public static void idDigitsCheck(String id) throws Exceptions {
        if ( !id.matches("\\d{10}") ) {
            throw new Exceptions("ID must be a 10 digits number! Please try again.");
        }
    }

    public static void alreadyInThePassedCourses(HashMap<Course, String> passedCourses, Course course)
            throws Exceptions {
        if ( passedCourses.containsKey(course) ) {
            throw new Exceptions("The course is already added to the passed courses list.");
        }
    }

    public static void noGroupsExist() throws Exceptions {
        if ( Group.groups.isEmpty() ) {
            throw new Exceptions("There are no course groups to choose from! Please try again later.");
        }
    }

    public static void notNewCourse(Group group, Course course) throws Exceptions {
        if ( course.getName().equals(group.getName()) ) {
            throw new Exceptions("The new course can't be the same course as this group's course! Please try again.");
        }
    }

    public static void notNewGroupNumber(Group group, String groupNumber) throws Exceptions {
        if ( group.getGroupNumber().equals(groupNumber) ) {
            throw new Exceptions("The new group number can't be the same number as this group's number! Please try " +
                    "again.");
        }
    }

    public static void notNewGroup(String groupNumber, String name) throws Exceptions {
        for ( Group group : Group.groups ) {
            if ( group.getName().equals(name) && group.getGroupNumber().equals(groupNumber) ) {
                throw new Exceptions("This course group is already defined! Please try again.");
            }
        }
    }

    public static void notNewCapacity(Group group, String capacity) throws Exceptions {
        if ( group.getCapacity().equals(capacity) ) {
            throw new Exceptions("The new capacity can't be the same as this group's capacity! Please try again.");
        }
    }

    public static void notNewTermNumber(Group group, String termNumber) throws Exceptions {
        if ( group.getTermNumber().equals(termNumber) ) {
            throw new Exceptions("The new term number can't be the same as the group's term number! Please try again.");
        }
    }

    public static void groupAlreadyHasMaster(Group group) throws Exceptions {
        if ( group.getMasters().size() == 1 ) {
            throw new Exceptions("This group already has a master! Please try again.");
        }
    }

    public static void hasOverlap(Master master, Group group) throws Exceptions {
        for ( Group group1 : master.getGroups() ) {
            for ( Time time1 : group1.getSessions() ) {
                for ( Time time2 : group.getSessions() ) {
                    if ( time1.isOverlap(time2) ) {
                        throw new Exceptions("This group overlaps another class of this user! Please try again.");
                    }
                }
            }
        }
    }

    public static void alreadyInTheGroupsList(Student student, Group group) throws Exceptions {
        if ( student.getGroups().contains(group) ) {
            throw new Exceptions("This group is already in the student's group lists!");
        }
    }

    public static void hasPassed(Student student, Group group) throws Exceptions {
        for ( Course course : student.getPassedCourses().keySet() ) {
            if ( course.getName().equals(group.getName()) ) {
                throw new Exceptions("This course is already passed by this student! Please try again.");
            }
        }
    }

    public static void doesNotMeetTheNeeds(Student student, Group group) throws Exceptions {
        for ( Course course : group.getNeededCourses() ) {
            if ( !student.getPassedCourses().containsKey(course) ) {
                throw new Exceptions("This student has not passed the needed courses for this group! Please try again.");
            }
        }
    }

    public static void noEnoughUnits(Student student, Group group) throws Exceptions {
        if ( Integer.parseInt(student.getUnits()) < Integer.parseInt(group.getUnits()) ) {
            throw new Exceptions("This student does not have enough units for this group! Please try again.");
        }
    }

    public static void noCapacityLeft(Group group) throws Exceptions {
        if ( group.getCapacity().equals("0") ) {
            throw new Exceptions("This group is full and has 0 capacity! Please try again.");
        }
    }

    public static void noCourseExist() throws Exceptions {
        if ( Course.courses.size() == 0 ) {
            throw new Exceptions("No courses are defined! Please try again later.");
        }
    }
}