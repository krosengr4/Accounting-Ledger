public class Main {
    /*
Home Screen
The home screen should give the user the following options. The application should continue to run until the user chooses to exit.
D) Add Deposit - prompt user for the deposit information and save it to the csv file
P) Make Payment (Debit) - prompt user for the debit information and save it to the csv file
L) Ledger - display the ledger screen
X) Exit - exit the application

Ledger - All entries should show the newest entries first
A) All - Display all entries
D) Deposits - Display only the entries that are deposits into the account
P) Payments - Display only the negative entries (or payments)
R) Reports - A new screen that allows the user to run pre-defined reports or to run a custom search:
    1) Month To Date
    2) Previous Month
    3) Year To Date
    4) Previous Year
    5) Search by Vendor - prompt the user for the vendor name and display all entries for that vendor
    0) Back - go back to the report page
    H) Home - go back to the home page
     */

    public static void main(String[] args) {
        System.out.println("\t----------WELCOME TO THE ACCOUNTING LEDGER!----------");

        homeScreen();
    }

    //Displays home screen
    private static void homeScreen() {

        String userChoice;

        //Get user action input
        System.out.println("\n\t-----MAIN MENU-----");
        System.out.println("Enter the letter associated with the desired action\nD - Add a deposit \nP - Make a payment(debit) \nL - Go to Ledger Screen \nX - Exit the Ledger Application");
        userChoice = Utils.promptGetUserInput("What would you like to do?: ").toUpperCase();

        // call correct method that follows users action input
        switch (userChoice) {
            case "D":
                addDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                Ledger.displayLedgerScreen();
                break;
            case "X":
                System.out.println("Thank you for using the Accounting Ledger! \n \tSee you soon :)");
                break;
            default:
                throw new IllegalStateException("Please enter one of the letters listed!");
        }
    }

    private static void addDeposit() {
        System.out.println("Add a deposit");
    }

    private static void makePayment() {
        System.out.println("Make a payment");
    }

}
