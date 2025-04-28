public class Reports {

    public static String displayReportsScreen() {

        boolean ifContinue = true;
        String userAction = "";

        // This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while(ifContinue) {
            System.out.println("\n\t-----LEDGER REPORT-----");

            //Get user input
            System.out.println("OPTIONS: \n1 - Transactions this Month \n2 - Transactions last Month \n3 - Transactions this Year " +
                    "\n4 - Transactions last Year \n5 - Search by Vendor \n0 - Go back to Ledger Screen \nH - Return to Home Screen");
            userAction = Utils.promptGetUserInput("Sort Ledger Report by: ").toLowerCase();

            //todo replace if/else statements with new switch statement (Java 14+)

            switch (userAction) {
                case "1" -> formatMonthToDate();
                case "2" -> formatPreviousMonth();
                case "3" -> formatYearToDate();
                case "4" -> formatPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> ifContinue = false;
                case "h" -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter one of the letters or numbers listed");
            }

            // call correct method that follows users action input
//            if (userAction.equals("1")) {
//                formatMonthToDate();
//            } else if (userAction.equals("2")) {
//                formatPreviousMonth();
//            } else if (userAction.equals("3")) {
//                formatYearToDate();
//            } else if (userAction.equals("4")) {
//                formatPreviousYear();
//            } else if (userAction.equals("5")) {
//                searchByVendor();
//            } else if (userAction.equals("0")) {
//                ifContinue = false;
//            } else if (userAction.equalsIgnoreCase("H")) {
//                ifContinue = false;
//            } else {
//                System.err.println("ERROR! Please enter one of the letters or numbers listed");
//            }
        }
        return userAction;
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
