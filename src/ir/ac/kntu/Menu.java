package ir.ac.kntu;

public class Menu {

    public enum MainMenu {
        SUBMIT_ADMIN, LOG_IN, EXIT_SYSTEM
    }

    public enum AdminMenu {
        SUBMIT_COURSE, SUBMIT_GROUP, SUBMIT_STUDENT, SUBMIT_MASTER, EDIT_GROUP, DELETE_GROUP, SET_FOR_STUDENTS,
        SET_FOR_MASTERS, SHOW_ALL_COURSES, SHOW_ALL_GROUPS, SHOW_ALL_STUDENTS, SHOW_ALL_MASTERS, REMOVE_REQUESTS,
        LOG_OUT
    }

    public enum EditGroupMenu {
        EDIT_COURSE, EDIT_GROUP_NUMBER, EDIT_CAPACITY, EDIT_TERM_NUMBER, REMOVE_MASTER, REMOVE_STUDENT, EDIT_SESSIONS,
        EDIT_TYPE, EXIT
    }

    public enum StudentMenu {
        SET_GROUPS, WEEKLY_SCHEDULE, CHECK_PASSED_COURSES_AND_MARKS, REQUEST_TO_REMOVE_A_GROUP, ORDER_FOOD, LOG_OUT
    }

    public enum MasterMenu {
        CHECK_GROUPS, CHECK_STUDENTS, WEEKLY_SCHEDULE, SET_MARKS, LOG_OUT
    }

    public enum OrderMenu {
        ORDER_MEAL, CHECK_ORDERS, EXIT
    }

    public enum Place {
        CAFETERIA, DORM
    }

    public enum BreakfastMenu {
        EGG, JAM, HONEY, CHOCOLATE, CREAM, BUTTER, CHEESE, OMELETTE, SAUSAGE
    }

    public enum MainLunchMenu {
        GHORME_SABZI, GHEIME, CHELO_KABAB, CHELO_JOOJE, ZERESHK_POLO_BA_MORGH, MACARRONI, EGG, OMLETTE, SAUSAGE
    }

    public enum BeverageMenu {
        SODA, DELESTER, DOOGH, JUICE, WATER, NONE
    }
}