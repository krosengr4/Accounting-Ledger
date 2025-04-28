public class Reports {

    public static void displayReportsScreen() {
        /*
        1) Month To Date
        2) Previous Month
        3) Year To Date
        4) Previous Year
        5) Search by Vendor - prompt the user for the vendor name and display all entries for that vendor
        0) Back - go back to the report page
        H) Home - go back to the home page
         */
        String userAction;

        // This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while(true) {
            System.out.println("\n\t-----LEDGER REPORT-----");

            //Get user input
            System.out.println("OPTIONS: \n1 - Transactions this Month \n2 - Transactions last Month \n3 - Transactions this Year " +
                    "\n4 - Transactions last Year \n5 - Search by Vendor \n0 - Go back to Ledger Screen \nH - Return to Home Screen");
            userAction = Utils.promptGetUserInput("Sort Ledger Report by: ");

            //todo replace if/else statements with new switch statement (Java 14+)

            // call correct method that follows users action input
            if (userAction.equals("1")) {
                formatMonthToDate();
            } else if (userAction.equals("2")) {
                formatPreviousMonth();
            } else if (userAction.equals("3")) {
                formatYearToDate();
            } else if (userAction.equals("4")) {
                formatPreviousYear();
            } else if (userAction.equals("5")) {
                searchByVendor();
            } else if (userAction.equals("0")) {
                Ledger.displayLedgerScreen();
            } else if (userAction.equalsIgnoreCase("H")) {
                Main.displayHomeScreen();
            } else {
                System.err.println("ERROR! Please enter one of the letters or numbers listed");
            }
        }
    }

    //todo Create 1 single method to format report that takes in user request as parameter and formats accordingly

    private static void formatMonthToDate() {
        System.out.println("All transactions this month");
    }
    private static void formatPreviousMonth() {
        System.out.println("All transactions last month");
    }

    private static void formatYearToDate() {
        System.out.println("Transactions this year");
    }

    private static void formatPreviousYear() {
        System.out.println("Transactions last year");
    }

    private static void searchByVendor() {
        System.out.println("Search transactions by vendor name");
    }

}
