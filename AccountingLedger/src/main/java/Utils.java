import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utils {

    static Scanner myScanner = new Scanner(System.in);

    //public file to Write to
    public static String logFile = "AccountingLedger/src/main/resources/transactions.csv";

    //region Colors for printing out colored messages
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    //endregion

    //prompt user, then get and return user input
    static String promptGetUserInput(String message) {
        System.out.println(message);
        String userInput = myScanner.nextLine();
        return userInput.trim();
    }

    //Pauses the app until user hits Enter
    public static void pauseApp() {
        System.out.println("\nPress Enter to Continue...");
        myScanner.nextLine();
    }

    //Returns current year as "yyyy"
    public static String getLocalYear() {
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        String thisYear = todayDate.format(year);
        return thisYear;
    }

    //Returns current month as "MM"
    public static String getLocalMonth() {
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        String thisMonth = todayDate.format(month);
        return thisMonth;
    }
}
