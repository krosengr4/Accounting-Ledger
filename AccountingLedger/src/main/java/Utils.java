import java.util.Scanner;

public class Utils {

    //Prompt user for input and return user input
    static String getUserInput(String message) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = myScanner.nextLine();
        return userInput;
    }

}
