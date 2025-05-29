import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LedgerFileManager {

    public static ArrayList<Transaction> allTransactions;

    public static ArrayList<Transaction> getAllTransactions() {
        allTransactions = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Utils.logFile));
            String input;

            while ((input = bufferedReader.readLine()) != null) {
                String[] lineParts = input.split("\\|");

                //Ignore blank lines or the header line
                if (lineParts[0].isEmpty() || lineParts[0].equals("date")) {
                    continue;
                }

                Transaction newTransaction = new Transaction(lineParts[0], lineParts[1], lineParts[2], lineParts[3], Double.parseDouble(lineParts[4]));

                allTransactions.add(newTransaction);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return allTransactions;
    }

}
