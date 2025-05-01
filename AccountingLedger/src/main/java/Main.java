import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) {
        System.out.println(Utils.ANSI_BLUE + "\n\t\t--------------------WELCOME TO THE ACCOUNTING LEDGER!--------------------" + Utils.ANSI_RESET);
        displayHomeScreen();
        System.out.println(Utils.ANSI_CYAN + "Thank you for using the Accounting Ledger!\n\tSee you soon :)" + Utils.ANSI_RESET);
    }

    public static void displayHomeScreen() {
        String userChoice;
        boolean ifContinue = true;

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {

            System.out.println(Utils.ANSI_BLUE + "\n\t-----MAIN MENU-----" + Utils.ANSI_RESET);
            //Get user action input
            System.out.println(Utils.ANSI_YELLOW + "Enter the letter associated with the desired action" + Utils.ANSI_RESET + "\nD - Add a deposit \nP - Make a payment(debit) \nL - Go to Ledger Screen \nX - Exit the Ledger Application");
            userChoice = Utils.promptGetUserInput(Utils.ANSI_YELLOW + "What would you like to do?: " + Utils.ANSI_RESET).toLowerCase();

            // call correct method that follows users action input
            switch (userChoice) {
                case "d" -> addDeposit();
                case "p" -> makePayment();
                case "l" -> Ledger.displayLedgerScreen();
                case "x" -> ifContinue = false;
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
            FileWriter writer = new FileWriter(Utils.logFile, true);
            writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|" + userAmount);
            writer.close();
            System.out.printf("%s|%s|%s|%.2f\n", logDateTime, userDescription, userVendor, userAmount);
            System.out.println(Utils.ANSI_GREEN + "Success! Deposit transaction logged!" + Utils.ANSI_RESET);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void makePayment() {
        //To add a payment well need file writer
        System.out.println("Make a payment");

        String userDescription = Utils.promptGetUserInput("What is the payment description?: ");
        String userVendor = Utils.promptGetUserInput("What is the payment vendor?: ");
        Double userAmount = Double.parseDouble(Utils.promptGetUserInput("What is the payment amount?: "));

        //Get and format the date and time
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formattedDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm:ss");
        String logDateTime = dateTime.format(formattedDateTime);

        try {
            FileWriter writer = new FileWriter(Utils.logFile, true);
            writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|-" + userAmount);
            writer.close();

            System.out.printf("%s|%s|%s|-%.2f\n", logDateTime, userDescription, userVendor, userAmount);
            System.out.println(Utils.ANSI_GREEN + "Success! Payment transaction logged!" + Utils.ANSI_RESET);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
