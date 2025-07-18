import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Ledger {

    public static void displayLedgerScreen() {
        boolean ifContinue = true;
        String userChoice;
        String userInputFromReports;

        //This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {
            //Get user input
            System.out.println(Utils.BLUE + "\n\t-----LEDGER-----" + Utils.RESET);
            System.out.println(Utils.YELLOW + "OPTIONS:" + Utils.RESET + "\nA - Display all entries \nD - Display deposits \nP - Display payments \nR - Go to Reports Screen \nH - Return to Main Menu");
            userChoice = Utils.getUserInput(Utils.YELLOW + "What would you like to do?: ").toLowerCase();

            // call correct method that follows users action input
            switch (userChoice) {
                case "a", "d", "p" -> sortAndPrintLedger(userChoice);
                case "r" -> {
                    userInputFromReports = Reports.displayReportsScreen();
                    //If user chooses "Go to Main Page" from the ledger page, don't continue this loop
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

        //Switch statement to print out correct title based on user choice
        switch (userChoice) {
            case "a" -> System.out.println(Utils.PURPLE + "\t\t---ALL TRANSACTIONS---" + Utils.RESET);
            case "d" -> System.out.println(Utils.GREEN + "\t\t---ALL DEPOSITS---" + Utils.RESET);
            case "p" -> System.out.println(Utils.RED + "\t\t---ALL PAYMENTS---" + Utils.RESET);
        }

        //Calls loadLedger() method and gets back ArrayList
        ArrayList<Transaction> transactionsList = loadLedger(userChoice);


        //Sort and print out ledger ArrayList there are objects in the ArrayList
        if (transactionsList.isEmpty()) {
            System.out.println(Utils.RED + "There are currently no transactions." + Utils.RESET);
        } else {
            //Sort each object in the array list based on the date and time. Required newest first (2025 before 2024)
            transactionsList.sort(Comparator.comparing(Transaction::getDateTime).reversed());

            //loop through array list and print out each object(transaction) in transactionList Array List
            for (Transaction t : transactionsList) {

                //Set the color based on deposit(green) or payment(red)
                String color = "";
                if (t.getAmount() < 0) {
                    color = Utils.RED;
                } else {
                    if (t.getAmount() > 0) {
                        color = Utils.RED;
                    }
                }

                System.out.printf("%s|%s|%s|%s|%s%.2f%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), color, t.getAmount(), Utils.RESET);
            }
            //Pause the app until user hits continue
            Utils.pauseApp();
        }
    }

    public static ArrayList<Transaction> loadLedger(String userChoice) {

        //Create empty ArrayList to populate with what I am going to print
        ArrayList<Transaction> transactionsList = new ArrayList<>();

        try {
            //Open Buffered Reader
            BufferedReader bufReader = new BufferedReader(new FileReader(Utils.logFile));
            String input;

            //While the line isn't null read each line of file
            while ((input = bufReader.readLine()) != null) {
                //Split each line at the "|"
                String[] lineData = input.split("\\|");

                //Ignore blank lines or the header line
                if (lineData[0].isEmpty() || lineData[0].equals("date")) {
                    continue;
                }

                //Create new instance of Transaction object with data from each line of file
                Transaction newTransaction = new Transaction(lineData[0], lineData[1], lineData[2], lineData[3], Double.parseDouble(lineData[4]));

                //Add object to newTransaction ArrayList based on user request
                //No default because this is an internal call with clean data
                switch (userChoice) {
                    //Adds all the transactions in the file
                    case "a":
                        transactionsList.add(newTransaction);
                        break;
                    //Adds only the deposits in the file
                    case "d":
                        if (!lineData[4].startsWith("-")) {
                            transactionsList.add(newTransaction);
                        }
                        break;
                    //Adds only the payments in the file
                    case "p":
                        if (lineData[4].startsWith("-")) {
                            transactionsList.add(newTransaction);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //Return ArrayList after requested objects are added
        return transactionsList;
    }
}
