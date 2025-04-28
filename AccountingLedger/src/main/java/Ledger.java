import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Ledger {

    public static void displayLedgerScreen() {

        String userChoice;

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (true) {
            //Get user input
            System.out.println("\n\t-----LEDGER-----");
            System.out.println("OPTIONS: \nA - Display all entries \nD - Display deposits \nP - Display payments \nR - Go to Reports Screen \nH - Return to Home Screen");
            userChoice = Utils.promptGetUserInput("What would you like to do?: ");

            //todo replace if/else statements with new switch statement (Java 14+)

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
                Main.displayHomeScreen();
                break;
            } else {
                System.err.println("ERROR! Please enter one of the letters listed");
            }
        }
    }

    public static void displayEntries() {

        ArrayList <Transaction> ledger = new ArrayList<Transaction>();

        try {
            FileReader reader = new FileReader("AccountingLedger/src/main/resources/transactions.csv");
            BufferedReader bufReader = new BufferedReader(reader);
            String input;

            while((input = bufReader.readLine()) !=null) {
                String[] lineData = input.split("\\|");

                if(lineData[0].equals("date")) {
                    continue;
                }

                String date = lineData[0];
                String time = lineData[1];
                String description = lineData[2];
                String vendor = lineData[3];
                double amount = Double.parseDouble(lineData[4]);

                Transaction newTransaction = new Transaction(date, time, description, vendor, amount);
                ledger.add(newTransaction);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            System.out.printf("%s|%s|%s|%s|%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }

    public static void displayDeposits() {
        System.out.println("Display deposits only");
    }

    public static void displayPayments() {
        System.out.println("Display payments only");
    }
}
