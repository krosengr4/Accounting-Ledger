import java.util.Scanner;

public class Utils {

    //Prompt user and return user input
    static String promptAndGetUserInput(String message) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println(message);
        String userInput = myScanner.nextLine();
        return userInput;
    }

}
