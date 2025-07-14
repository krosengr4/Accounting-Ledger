import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        System.out.println(Utils.BLUE + "\n\t\t--------------------WELCOME TO THE ACCOUNTING LEDGER!--------------------" + Utils.RESET);
        displayHomeScreen();
        System.out.println(Utils.CYAN + "Thank you for using the Accounting Ledger!\n\tSee you soon :)" + Utils.RESET);
    }

    public static void displayHomeScreen() {
        String userChoice;
        boolean ifContinue = true;

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {

            System.out.println(Utils.BLUE + "\n\t-----MAIN MENU-----" + Utils.RESET);
            //Get user action input
            System.out.println(Utils.YELLOW + "Enter the letter associated with the desired action" + Utils.RESET + "\nD - Add a deposit \nP - Make a payment(debit) \nL - Go to Ledger Screen \nX - Exit the Ledger Application");
            userChoice = Utils.getUserInput(Utils.YELLOW + "What would you like to do?: " + Utils.RESET).toLowerCase();

            // call correct method that follows users action input
            switch (userChoice) {
                case "d", "p" -> addTransaction(userChoice);
                case "l" -> Ledger.displayLedgerScreen();
                case "x" -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter one of the letters listed");
            }
        }
    }

    private static void addTransaction(String userChoice) {
        String logDateTime = Utils.getFullDate();

        String userDescription = Utils.getUserInput("Enter the description: ");
        String userVendor = Utils.getUserInput("Enter the vendor: ");
        Double userAmount = Double.parseDouble(Utils.getUserInput("Enter the amount: "));

        try {
            FileWriter writer = new FileWriter(Utils.logFile, true);

            if (userChoice.equalsIgnoreCase("d")) {
                writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|" + userAmount);
                writer.close();
                System.out.printf("%s|%s|%s|%.2f\n", logDateTime, userDescription, userVendor, userAmount);
                System.out.println(Utils.GREEN + "Success! Deposit transaction logged!" + Utils.RESET);
            } else if (userChoice.equalsIgnoreCase("p")) {
                writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|-" + userAmount);
                writer.close();

                System.out.printf("%s|%s|%s|-%.2f\n", logDateTime, userDescription, userVendor, -userAmount);
                System.out.println(Utils.GREEN + "Success! Payment transaction logged!" + Utils.RESET);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Utils.pauseApp();

    }
}
