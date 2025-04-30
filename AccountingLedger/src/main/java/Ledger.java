import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Ledger {

    public static void displayLedgerScreen() {
        boolean ifContinue = true;
        String userChoice;
        String userInputFromReports = "";

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {
            //Get user input
            System.out.println("\n\t-----LEDGER-----");
            System.out.println("OPTIONS: \nA - Display all entries \nD - Display deposits \nP - Display payments \nR - Go to Reports Screen \nH - Return to Home Screen");
            userChoice = Utils.promptGetUserInput("What would you like to do?: ").toLowerCase();

            // call correct method that follows users action input
            switch (userChoice) {
                case "a", "d", "p" -> sortAndPrintLedger(userChoice);
                case "r" -> {
                    userInputFromReports = Reports.displayReportsScreen();
                    if (userInputFromReports.equalsIgnoreCase("h")) {
                        ifContinue = false;
                    }
                }
                case "h" -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter one of the letters listed");
            }
        }
    }

    public static void sortAndPrintLedger(String userChoice) {

        switch (userChoice) {
            case "a" -> System.out.println("\t\t---ALL TRANSACTIONS---");
            case "d" -> System.out.println("\t\t---ALL DEPOSITS---");
            case "p" -> System.out.println("\t\t---ALL PAYMENTS---");
        }

        ArrayList<Transaction> ledger = loadLedger(userChoice);

        //todo: Sort each object in the array list based on the date (newest first, oldest last)

        for (int i = 0; i < ledger.size(); i++) {
            Transaction t = ledger.get(i);
            System.out.printf("%s|%s|%s|%s|%.2f \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }
    }

    public static ArrayList<Transaction> loadLedger(String userChoice) {

        ArrayList<Transaction> ledger = new ArrayList<>();

        try {
            FileReader reader = new FileReader(Main.logFile);
            BufferedReader bufReader = new BufferedReader(reader);
            String input;

            while ((input = bufReader.readLine()) != null) {
                String[] lineData = input.split("\\|");

                if (lineData[0].equals("date")) {
                    continue;
                }

                Transaction newTransaction = new Transaction(lineData[0], lineData[1], lineData[2], lineData[3], Double.parseDouble(lineData[4]));

                switch(userChoice) {
                    case "a":
                        ledger.add(newTransaction);
                        break;
                    case "d":
                        if(lineData[4].startsWith("-")) {
                            continue;
                        } else {
                            ledger.add(newTransaction);
                            break;
                        }
                    case "p":
                        if(lineData[4].startsWith("-")) {
                            ledger.add(newTransaction);
                            break;
                        }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ledger;

    }
}
