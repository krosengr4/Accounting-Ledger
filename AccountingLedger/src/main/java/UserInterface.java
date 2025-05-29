import java.util.ArrayList;

public class UserInterface {


    public void processMainMenuChoice() {
        boolean ifContinue = true;

        do {
            String userChoice = displayMainMenu();

            switch (userChoice) {


                case "x" -> ifContinue = false;
            }

        } while (ifContinue);


    }

    public String displayMainMenu() {
        System.out.println(Utils.ANSI_BLUE + "\n\t\t-----MAIN MENU-----" + Utils.ANSI_RESET);
        System.out.println();
        return Utils.promptGetUserInput("What would you like to do?: ").trim().toLowerCase();
    }

    public void processLedgerMenuChoice() {
        boolean ifContinue = true;
    }

    public String displayLedgerMenu() {
        return Utils.promptGetUserInput("What would you like to do?: ");
    }


}
