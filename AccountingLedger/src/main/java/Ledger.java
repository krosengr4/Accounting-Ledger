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
            //Get user input
            System.out.println("\n\t-----LEDGER-----");
            System.out.println("OPTIONS: \nA - Display all entries \nD - Display deposits \nP - Display payments \nR - Go to Reports Screen \nH - Return to Home Screen");
            userChoice = Utils.promptGetUserInput("What would you like to do?: ");

            // call correct method that follows users action input
            if (userChoice.equalsIgnoreCase("A")) {
                displayEntries();
            } else if (userChoice.equalsIgnoreCase("D")) {
                displayDeposits();
            } else if (userChoice.equalsIgnoreCase("P")) {
                displayPayments();
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

    //!Change void to a return type (either hashmap or array list)
    public static void displayEntries() {
        System.out.println("Display all entries");
    }

    //!Change void to a return type (either hashmap or array list)\
    public static void displayDeposits() {
        System.out.println("Display deposits only");
    }

    //!Change void to a return type (either hashmap or array list)
    public static void displayPayments() {
        System.out.println("Display payments only");
    }
}
