import jdk.jshell.execution.Util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Ledger {

    public static void displayLedgerScreen() {
        boolean ifContinue = true;
        String userChoice;
        String userInputFromReports = "";

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {
            //Get user input
            System.out.println(Utils.ANSI_BLUE + "\n\t-----LEDGER-----" + Utils.ANSI_RESET);
            System.out.println(Utils.ANSI_YELLOW + "OPTIONS:" + Utils.ANSI_RESET + "\nA - Display all entries \nD - Display deposits \nP - Display payments \nR - Go to Reports Screen \nH - Return to Main Menu");
            userChoice = Utils.promptGetUserInput(Utils.ANSI_YELLOW + "What would you like to do?: ").toLowerCase();

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
            case "a" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---ALL TRANSACTIONS---" + Utils.ANSI_RESET);
            case "d" -> System.out.println(Utils.ANSI_GREEN + "\t\t---ALL DEPOSITS---" + Utils.ANSI_RESET);
            case "p" -> System.out.println(Utils.ANSI_RED + "\t\t---ALL PAYMENTS---" + Utils.ANSI_RESET);
        }

        ArrayList<Transaction> ledger = loadLedger(userChoice);

        if (ledger.size() == 0) {
            System.out.println(Utils.ANSI_RED + "There are currently no transactions." + Utils.ANSI_RESET);
        } else {
            //Sort each object in the array list based on the date and time
            ledger.sort(Comparator.comparing(Transaction::getDateTime).reversed());

            //loop through array list and print out each object(transaction) in ledger Array List
            for (int i = 0; i < ledger.size(); i++) {
                Transaction t = ledger.get(i);
                System.out.printf("%s|%s|%s|%s|%.2f \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }

    public static ArrayList<Transaction> loadLedger(String userChoice) {

        ArrayList<Transaction> ledger = new ArrayList<>();

        try {
            FileReader reader = new FileReader(Utils.logFile);
            BufferedReader bufReader = new BufferedReader(reader);
            String input;

            while ((input = bufReader.readLine()) != null) {
                String[] lineData = input.split("\\|");

                //Ignore blank lines or the header line
                if (lineData[0].equals("") || lineData[0].equals("date")) {
                    continue;
                }

                Transaction newTransaction = new Transaction(lineData[0], lineData[1], lineData[2], lineData[3], Double.parseDouble(lineData[4]));

                //Add object to newTransaction ArrayList based on user request
                //No default because this is an internal call with clean data
                switch (userChoice) {
                    //Shows all the transactions in the file
                    case "a":
                        ledger.add(newTransaction);
                        break;
                    //Shows only the deposits in the file
                    case "d":
                        if (!lineData[4].startsWith("-")) {
                            ledger.add(newTransaction);
                        }
                        break;
                    //Shows only the payments in the file
                    case "p":
                        if (lineData[4].startsWith("-")) {
                            ledger.add(newTransaction);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ledger;
    }
}
