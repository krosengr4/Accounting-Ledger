import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class Reports {

    public static String displayReportsScreen() {

        boolean ifContinue = true;
        String userAction = "";

        // This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {
            System.out.println(Utils.ANSI_BLUE + "\n\t-----LEDGER REPORT-----" + Utils.ANSI_RESET);

            //Get user input
            System.out.println(Utils.ANSI_YELLOW + "SORT REPORT BY:" + Utils.ANSI_RESET + "\n1 - Transactions this Month \n2 - Transactions last Month \n3 - Transactions this Year " +
                    "\n4 - Transactions last Year \n5 - Search by Vendor \n0 - Go back to Ledger Screen \nH - Return to Main Menu");
            userAction = Utils.promptGetUserInput(Utils.ANSI_YELLOW + "What would you like to do?: " + Utils.ANSI_RESET).toLowerCase();

            // call correct method that follows users action input
            switch (userAction) {
                case "1", "2", "3", "4" -> displayReportByDate(userAction);
                case "5" -> searchByVendor();
                case "0", "h" -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter one of the letters or numbers listed");
            }
        }
        return userAction;
    }

    public static void displayReportByDate(String userAction) {

        //Print out Title based on user choice
        switch (userAction) {
            case "1" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS THIS MONTH---" + Utils.ANSI_RESET);
            case "2" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS LAST MONTH---" + Utils.ANSI_RESET);
            case "3" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS THIS YEAR---" + Utils.ANSI_RESET);
            case "4" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS LAST YEAR---" + Utils.ANSI_RESET);
        }

        //Call loadReportByDate method and get back ArrayList
        ArrayList<Transaction> transactionsList = loadReportByDate(userAction);

        //Sort and print out ledger ArrayList there are objects in the ArrayList
        if (transactionsList.isEmpty()) {
            System.out.println(Utils.ANSI_RED + "There are currently no transactions." + Utils.ANSI_RESET);
        } else {
            //Sort each object in the array list based on the date
            transactionsList.sort(Comparator.comparing(Transaction::getDateTime).reversed());

            //Loop through and print out each object(transaction) in ArrayList
            for (Transaction t : transactionsList) {
//                Transaction t = transactionsList.get(t);

                //Set the color of amount based on deposit(green) or payment(red)
                String color ="";
                if(t.getAmount() < 0) {
                    color = Utils.ANSI_RED;
                } else {
                    if(t.getAmount() > 0) {
                        color = Utils.ANSI_GREEN;
                    }
                }
                System.out.printf("%s|%s|%s|%s|%s%.2f%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), color, t.getAmount(), Utils.ANSI_RESET);
            }
        }
        //Pause the app until user hits continue
        Utils.pauseApp();
    }

    private static ArrayList<Transaction> loadReportByDate(String userAction) {

        //Get the local current month and year
        String thisMonth = Utils.getLocalMonth();
        String thisYear = Utils.getLocalYear();

        //Create new ArrayList
        ArrayList<Transaction> transactionsList = new ArrayList<>();

        try {
            //Open Buffered Reader to read file
            BufferedReader bufReader = new BufferedReader(new FileReader(Utils.logFile));
            String input;

            //Read each filled out line of file
            while ((input = bufReader.readLine()) != null) {
                //Split each line of data at the "|"
                String[] lineData = input.split("\\|");

                //Ignore blank lines or the header line
                if (lineData[0].isEmpty() || lineData[0].equals("date")) {
                    continue;
                }

                //Store each lineData part in variables
                String date = lineData[0];
                String time = lineData[1];
                String description = lineData[2];
                String vendor = lineData[3];
                double amount = Double.parseDouble(lineData[4]);

                //Further split date (lineData[0])
                String[] dateParts = date.split("-");

                //Parse the month and year from the file and the current date
                Integer intMonth = Integer.parseInt(dateParts[1]);
                Integer intYear = Integer.parseInt(dateParts[0]);
                Integer intCurrentMonth = Integer.parseInt(thisMonth);
                Integer intCurrentYear = Integer.parseInt(thisYear);

                //Create new instance of Transaction object with data from each line of file
                Transaction newTransactions = new Transaction(date, time, description, vendor, amount);

                //Add newTransactions object to ArrayList based on user request
                switch (userAction) {
                    //Adds only transactions from current month
                    case "1":
                        if (dateParts[1].equals(thisMonth) && dateParts[0].equals(thisYear)) {
                            transactionsList.add(newTransactions);
                        }
                        break;
                        //Adds only transactions from previous month
                    case "2":
                        //If current month is January, look at the last month of the previous year(December). Add to transLastMonth ArrayList.
                        //or if current month is not January, just look at previous month of this year. Add to transLastMonth ArrayList.
                        if ((intCurrentMonth == 01 && intYear == (intCurrentYear - 1) && intMonth == 12)
                                || (dateParts[0].equals(thisYear) && intMonth == (intCurrentMonth - 1))) {
                            transactionsList.add(newTransactions);
                        }
                        break;
                        //Adds only transactions from the current year
                    case "3":
                        if (dateParts[0].equals(thisYear)) {
                            Transaction transThisMonth = new Transaction(date, time, description, vendor, amount);
                            transactionsList.add(transThisMonth);
                        }
                        break;
                        //Adds only transactions from the previous year
                    case "4":
                        if (intYear == (intCurrentYear - 1)) {
                            Transaction transLastYear = new Transaction(date, time, description, vendor, amount);
                            transactionsList.add(transLastYear);
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

    private static void searchByVendor() {

        //Ask user for vendor to search
        String userVendorSearch = Utils.promptGetUserInput(Utils.ANSI_YELLOW + "Enter the vendor you would like to search: " + Utils.ANSI_RESET);

        //Create ArrayList
        ArrayList<Transaction> transactionList = new ArrayList<>();

        try {
            //Open Buffered Reader to read file
            BufferedReader bufReader = new BufferedReader(new FileReader(Utils.logFile));
            String input;

            //Read each line of file that has data
            while ((input = bufReader.readLine()) != null) {

                //Split data in file at "|"
                String[] lineData = input.split("\\|");

                //Ignore blank lines or the header line
                if (lineData[0].isEmpty() || lineData[0].equals("date")) {
                    continue;
                }

                //Store each lineData part in variables
                String date = lineData[0];
                String time = lineData[1];
                String description = lineData[2];
                String vendor = lineData[3];
                double amount = Double.parseDouble(lineData[4]);

                //Create new instance of Transaction object with data from each line of file
                Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

                //Add only the Transactions that match the vendor with user input
                if (userVendorSearch.equalsIgnoreCase(vendor)) {
                    transactionList.add(newTransaction);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //Sort and print out ledger ArrayList there are objects in the ArrayList
        if (transactionList.isEmpty()) {
            System.out.println(Utils.ANSI_RED + "\tThere are no transactions with " + userVendorSearch + Utils.ANSI_RESET);
        } else {
            System.out.println(Utils.ANSI_PURPLE + "\t---TRANSACTIONS WITH " + userVendorSearch.toUpperCase() + "---" + Utils.ANSI_RESET);

            //Sort each object in the array list based on the date
            transactionList.sort(Comparator.comparing(Transaction::getDate).reversed());

            //Loop through and print out each object(transaction) in ArrayList
            for (Transaction t : transactionList) {

                //Set the color based on deposit(green) or payment(red)
                String color ="";
                if(t.getAmount() < 0) {
                    color = Utils.ANSI_RED;
                } else {
                    if(t.getAmount() > 0) {
                        color = Utils.ANSI_GREEN;
                    }
                }
                System.out.printf("%s|%s|%s|%s|%s%.2f%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), color, t.getAmount(), Utils.ANSI_RESET);
            }
        }
        //Pause the app until user hits continue
        Utils.pauseApp();
    }
}
