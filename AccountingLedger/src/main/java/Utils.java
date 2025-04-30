import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utils {

    //prompt user, then get and return user input
    static String promptGetUserInput(String message) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = myScanner.nextLine();
        return userInput.trim();
    }

    public static String getLocalYear() {
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        String thisYear = todayDate.format(year);

        return thisYear;
    }

    public static String getLocalMonth() {
        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        String thisMonth = todayDate.format(month);

        return thisMonth;
    }

}
