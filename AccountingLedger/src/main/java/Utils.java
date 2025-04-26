import java.util.Scanner;

public class Utils {

    //prompt user, then get and return user input
    static String promptGetUserInput(String message) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = myScanner.nextLine();
        return userInput;
    }

}
