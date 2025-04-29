import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    //public file to Write to
    public static String logFile = "AccountingLedger/src/main/resources/transactions.csv";

    //todo Create displayHomeScreen() method and move the code in main method into displayHomeScreen method

    public static void main(String[] args) {
        System.out.println("\n\t\t--------------------WELCOME TO THE ACCOUNTING LEDGER!--------------------");
        displayHomeScreen();
    }

    public static void displayHomeScreen() {
        String userChoice;
        boolean ifContinue = true;

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {
            //Get user action input
            System.out.println("\n\t-----MAIN MENU-----");
            System.out.println("Enter the letter associated with the desired action\nD - Add a deposit \nP - Make a payment(debit) \nL - Go to Ledger Screen \nX - Exit the Ledger Application");
            userChoice = Utils.promptGetUserInput("What would you like to do?: ").toLowerCase();

            // call correct method that follows users action input
            switch (userChoice) {
                case "d" -> addDeposit();
                case "p" -> makePayment();
                case "l" -> Ledger.displayLedgerScreen();
                case "x" -> {
                    System.out.println("Thank you for using the Accounting Ledger!\n\tSee you soon :)");
                    ifContinue = false;
                }
                default -> System.err.println("ERROR! Please enter one of the letters listed");
            }
        }
    }

    private static void addDeposit() {
        //Get user input
        String userDescription = Utils.promptGetUserInput("What is the deposit description?: ");
        String userVendor = Utils.promptGetUserInput("What the is deposit vendor?: ");
        Double userAmount = Double.parseDouble(Utils.promptGetUserInput("What is the deposit amount?: "));

        //Get and format the date and time
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        String logDateTime = dateTime.format(formattedDateTime);

        //Open the file writer
        try {
            FileWriter writer = new FileWriter(logFile, true);
            writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|" + userAmount);
            writer.close();
            System.out.printf("%s|%s|%s|%.2f\n", logDateTime, userDescription, userVendor, userAmount);
            System.out.println("Success! Deposit transaction logged!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void makePayment() {
        //To add a payment well need file writer
        System.out.println("Make a payment");

        String userDescription = Utils.promptGetUserInput("What is the payment description?: ");
        String userVendor = Utils.promptGetUserInput("What is the payment description?: ");
        String userAmount = Utils.promptGetUserInput("What is the payment description?: ");

        //Get and format the date and time
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        String logDateTime = dateTime.format(formattedDateTime);



    }

}
