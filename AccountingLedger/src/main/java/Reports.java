import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Reports {

    public static String displayReportsScreen() {

        boolean ifContinue = true;
        String userAction = "";

        // This while loop will continue with various options presented to the user.  It will terminate when the user inputs the option to exit.
        while (ifContinue) {
            System.out.println("\n\t-----LEDGER REPORT-----");

            //Get user input
            System.out.println("OPTIONS: \n1 - Transactions this Month \n2 - Transactions last Month \n3 - Transactions this Year " +
                    "\n4 - Transactions last Year \n5 - Search by Vendor \n0 - Go back to Ledger Screen \nH - Return to Home Screen");
            userAction = Utils.promptGetUserInput("Sort Ledger Report by: ").toLowerCase();

            // call correct method that follows users action input
            switch (userAction) {
                case "1" -> formatMonthToDate();
                case "2" -> formatPreviousMonth();
                case "3" -> formatYearToDate();
                case "4" -> formatPreviousYear();
                case "5" -> searchByVendor();
                case "0" -> ifContinue = false;
                case "h" -> ifContinue = false;
                default -> System.err.println("ERROR! Please enter one of the letters or numbers listed");
            }
        }
        return userAction;
    }

    //todo Create 1 single method to format report that takes in user request as parameter and formats accordingly

    private static void formatMonthToDate() {

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter month = DateTimeFormatter.ofPattern("MM");
        String thisMonth = todayDate.format(month);
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        String thisYear = todayDate.format(year);

        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader(Main.logFile));
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

                String[] dateParts = date.split("-");

                if (dateParts[1].equals(thisMonth) && dateParts[0].equals(thisYear)) {
                    Transaction transThisMonth = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transThisMonth);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        System.out.println("---THIS MONTHS TRANSACTIONS---");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            System.out.printf("%s|%s|%s|%s|%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }

    }

    private static void formatPreviousMonth() {
        System.out.println("All transactions last month");
    }

    private static void formatYearToDate() {

        LocalDate todayDate = LocalDate.now();
        DateTimeFormatter year = DateTimeFormatter.ofPattern("yyyy");
        String thisYear = todayDate.format(year);

        ArrayList<Transaction> transactions = new ArrayList<>();

        try{
            BufferedReader bufReader = new BufferedReader(new FileReader(Main.logFile));

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

                String[] dateParts = date.split("-");

                if (dateParts[0].equals(thisYear)) {
                    Transaction transThisMonth = new Transaction(date, time, description, vendor, amount);
                    transactions.add(transThisMonth);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        System.out.println("---THIS YEARS TRANSACTIONS---");

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            System.out.printf("%s|%s|%s|%s|%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), t.getAmount());
        }


    }

    private static void formatPreviousYear() {
        System.out.println("Transactions last year");
    }

    private static void searchByVendor() {
        System.out.println("Search transactions by vendor name");
    }

}
