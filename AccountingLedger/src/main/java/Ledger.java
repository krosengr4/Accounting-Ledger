public class Ledger {

    public static void displayLedgerScreen() {
        /*
        A) All - Display all entries
        D) Deposits - Display only the entries that are deposits into the account
        P) Payments - Display only the negative entries (or payments)
        R) Reports - A new screen that allows the user to run pre-defined reports or to run a custom search:
         */

        String userChoice;

        while (true) {
            System.out.println("\n\t-----LEDGER-----");
            System.out.println("OPTIONS: \nA - Display all entries \nD - Display deposits \nP - Display payments \nR - Go to Reports Screen \nH - Return to Home Screen");
            userChoice = Utils.promptGetUserInput("What would you like to do?: ");

            if (userChoice.equalsIgnoreCase("A")) {
                System.out.println("Display all entries");
            } else if (userChoice.equalsIgnoreCase("D")) {
                System.out.println("Display deposits");
            } else if (userChoice.equalsIgnoreCase("P")) {
                System.out.println("Display payments");
            } else if (userChoice.equalsIgnoreCase("R")) {
                Reports.displayReportsScreen();
            } else if (userChoice.equalsIgnoreCase("H")) {
                System.out.println("Return to Home");
                break;
            } else {
                System.err.println("ERROR! Please enter one of the letters listed");
            }
        }
    }
}
