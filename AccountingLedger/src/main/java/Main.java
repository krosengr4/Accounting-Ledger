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
        System.out.println("----------WELCOME TO THE ACCOUNTING LEDGER!----------");

        homeScreen();


    }

    //Displays home screen
    private static void homeScreen() {
        String userChoice = Utils.promptAndGetUserInput("What would you like to do?: \nEnter \"D\" to add a deposit. " +
                "\nEnter \"P\" to make a payment(debit). \nEnter \"L\" to display ledger screen. " +
                "\nEnter \"X\" to exit the application. ").toUpperCase();

        System.out.println(userChoice);
    }

}
