import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class ReportsLogic {

	static UserInterface ui = new UserInterface();

	public static int displayReportsScreen() {
		boolean ifContinue = true;
		int userChoice = -1;

		while(ifContinue) {
			userChoice = ui.displayReportsScreen();

			switch(userChoice) {
				case 1, 2, 3, 4 -> displayReportByDate(userChoice);
				case 5 -> searchByVendor();
				case 6, 0 -> ifContinue = false;
			}
		}
		return userChoice;
	}

	public static void displayReportByDate(int userAction) {

		//Print out Title based on user choice
		switch (userAction) {
			case 1 -> System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS THIS MONTH---" + Utils.RESET);
			case 2 -> System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS LAST MONTH---" + Utils.RESET);
			case 3 -> System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS THIS YEAR---" + Utils.RESET);
			case 4 -> System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS LAST YEAR---" + Utils.RESET);
		}

		//Call loadReportByDate method and get back ArrayList
		ArrayList<Transaction> transactionsList = loadReportByDate(userAction);

		//Sort and print out ledger ArrayList there are objects in the ArrayList
		if (transactionsList.isEmpty()) {
			System.out.println(Utils.RED + "There are currently no transactions." + Utils.RESET);
		} else {
			//Sort each object in the array list based on the date. Required newest first (2025 before 2024)
			transactionsList.sort(Comparator.comparing(Transaction::getDateTime).reversed());

			//Loop through and print out each object(transaction) in ArrayList
			for (Transaction t : transactionsList) {

				//Set the color of amount based on deposit(green) or payment(red)
				String color = "";
				if (t.getAmount() < 0) {
					color = Utils.RED;
				} else {
					if (t.getAmount() > 0) {
						color = Utils.GREEN;
					}
				}
				System.out.printf("%s|%s|%s|%s|%s%.2f%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), color, t.getAmount(), Utils.RESET);
			}
			//Pause the app until user hits continue
			Utils.pauseApp();
		}
	}

	private static ArrayList<Transaction> loadReportByDate(int userAction) {

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
				int intMonth = Integer.parseInt(dateParts[1]);
				int intYear = Integer.parseInt(dateParts[0]);
				int intCurrentMonth = Integer.parseInt(thisMonth);
				int intCurrentYear = Integer.parseInt(thisYear);

				//Create new instance of Transaction object with data from each line of file
				Transaction newTransactions = new Transaction(date, time, description, vendor, amount);

				//Add newTransactions object to ArrayList based on user request
				switch (userAction) {
					//Adds only transactions from current month
					case 1:
						if (dateParts[1].equals(thisMonth) && dateParts[0].equals(thisYear)) {
							transactionsList.add(newTransactions);
						}
						break;
					//Adds only transactions from previous month
					case 2:
						//If current month is January, look at the last month of the previous year(December). Add to transLastMonth ArrayList.
						//or if current month is not January, just look at previous month of this year. Add to transLastMonth ArrayList.
						if ((intCurrentMonth == 1 && intYear == (intCurrentYear - 1) && intMonth == 12)
									|| (dateParts[0].equals(thisYear) && intMonth == (intCurrentMonth - 1))) {
							transactionsList.add(newTransactions);
						}
						break;
					//Adds only transactions from the current year
					case 3:
						if (dateParts[0].equals(thisYear)) {
							transactionsList.add(newTransactions);
						}
						break;
					//Adds only transactions from the previous year
					case 4:
						if (intYear == (intCurrentYear - 1)) {
							transactionsList.add(newTransactions);
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
		String userVendorSearch = Utils.getUserInput(Utils.YELLOW + "Enter the vendor you would like to search: " + Utils.RESET);

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
			System.out.println(Utils.RED + "\tThere are no transactions with " + userVendorSearch + Utils.RESET);
		} else {
			System.out.println(Utils.PURPLE + "\t---TRANSACTIONS WITH " + userVendorSearch.toUpperCase() + "---" + Utils.RESET);

			//Sort each object in the array list based on the date. Required newest first (2025 before 2024)
			transactionList.sort(Comparator.comparing(Transaction::getDate).reversed());

			//Loop through and print out each object(transaction) in ArrayList
			for (Transaction t : transactionList) {

				//Set the color based on deposit(green) or payment(red)
				String color = "";
				if (t.getAmount() < 0) {
					color = Utils.RED;
				} else {
					if (t.getAmount() > 0) {
						color = Utils.GREEN;
					}
				}
				System.out.printf("%s|%s|%s|%s|%s%.2f%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), color, t.getAmount(), Utils.RESET);
			}
		}
		//Pause the app until user hits continue
		Utils.pauseApp();
	}

}
