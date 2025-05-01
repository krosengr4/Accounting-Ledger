import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        switch (userAction) {
            case "1" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS THIS MONTH---" + Utils.ANSI_RESET);
            case "2" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS LAST MONTH---" + Utils.ANSI_RESET);
            case "3" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS THIS YEAR---" + Utils.ANSI_RESET);
            case "4" -> System.out.println(Utils.ANSI_PURPLE + "\t\t---TRANSACTIONS LAST YEAR---" + Utils.ANSI_RESET);
        }

        ArrayList<Transaction> transactions = loadReportByDate(userAction);


        if (transactions.size() == 0) {
            System.out.println(Utils.ANSI_RED + "There are currently no transactions." + Utils.ANSI_RESET);
        } else {
            //Sort each object in the array list based on the date
            transactions.sort(Comparator.comparing(Transaction::getDateTime).reversed());

            //Loop through and print out each object(transaction) in transactions ArrayList
            for (int i = 0; i < transactions.size(); i++) {
                Transaction t = transactions.get(i);
                System.out.printf("%s|%s|%s|%s|%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }

    private static ArrayList<Transaction> loadReportByDate(String userAction) {

        String thisMonth = Utils.getLocalMonth();
        String thisYear = Utils.getLocalYear();

        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(Utils.logFile));
            String input;

            while ((input = bufReader.readLine()) != null) {
                String[] lineData = input.split("\\|");

                if (lineData[0].equals("") || lineData[0].equals("date")) {
                    continue;
                }

                String date = lineData[0];
                String time = lineData[1];
                String description = lineData[2];
                String vendor = lineData[3];
                double amount = Double.parseDouble(lineData[4]);

                String[] dateParts = date.split("-");

                Integer intMonth = Integer.parseInt(dateParts[1]);
                Integer intYear = Integer.parseInt(dateParts[0]);
                Integer intCurrentMonth = Integer.parseInt(thisMonth);
                Integer intCurrentYear = Integer.parseInt(thisYear);

                Transaction newTransactions = new Transaction(date, time, description, vendor, amount);

                switch (userAction) {
                    case "1":
                        if (dateParts[1].equals(thisMonth) && dateParts[0].equals(thisYear)) {
                            transactions.add(newTransactions);
                        }
                        break;
                    case "2":
                        //If current month is January, look at the last month of the previous year(December). Add to transLastMonth ArrayList.
                        //or if current month is not January, just look at previous month of this year. Add to transLastMonth ArrayList.
                        if ((intCurrentMonth == 01 && intYear == (intCurrentYear - 1) && intMonth == 12)
                                || (dateParts[0].equals(thisYear) && intMonth == (intCurrentMonth - 1))) {
                            transactions.add(newTransactions);
                        }
                        break;
                    case "3":
                        if (dateParts[0].equals(thisYear)) {
                            Transaction transThisMonth = new Transaction(date, time, description, vendor, amount);
                            transactions.add(transThisMonth);
                        }
                        break;
                    case "4":
                        if (intYear == (intCurrentYear - 1)) {
                            Transaction transLastYear = new Transaction(date, time, description, vendor, amount);
                            transactions.add(transLastYear);
                        }
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return transactions;
    }

    private static void searchByVendor() {

        String userVendorSearch = Utils.promptGetUserInput(Utils.ANSI_YELLOW + "Enter the vendor you would like to search: " + Utils.ANSI_RESET);

        ArrayList<Transaction> transactionList = new ArrayList<>();

        try {

            BufferedReader bufReader = new BufferedReader(new FileReader(Utils.logFile));
            String input;

            while ((input = bufReader.readLine()) != null) {

                String[] lineData = input.split("\\|");

                if (lineData[0].equals("date")) {
                    continue;
                }

                String date = lineData[0];
                String time = lineData[1];
                String description = lineData[2];
                String vendor = lineData[3];
                double amount = Double.parseDouble(lineData[4]);

                Transaction newTransaction = new Transaction(date, time, description, vendor, amount);

                if (userVendorSearch.equalsIgnoreCase(vendor)) {
                    transactionList.add(newTransaction);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        transactionList.sort(Comparator.comparing(Transaction::getDate).reversed());

        if (transactionList.size() == 0) {
            System.out.println(Utils.ANSI_RED + "\tThere are no transactions with " + userVendorSearch + Utils.ANSI_RESET);
        } else {
            System.out.println(Utils.ANSI_PURPLE + "\t---TRANSACTIONS WITH " + userVendorSearch.toUpperCase() + "---" + Utils.ANSI_RESET);

            for (int i = 0; i < transactionList.size(); i++) {
                Transaction t = transactionList.get(i);
                System.out.printf("%s|%s|%s|%s|%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
            }
        }
    }
}
