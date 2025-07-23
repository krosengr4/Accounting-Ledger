package logic;

import models.Transaction;
import userInterface.UserInterface;
import utilities.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;

public class LedgerLogic {

	static UserInterface ui = new UserInterface();

	public static void displayLedgerScreen() {
		boolean ifContinue = true;

		while(ifContinue) {
			int userChoice = ui.displayLedgerScreen();

			switch(userChoice) {
				case 1, 2, 3 -> sortAndPrintLedger(userChoice);
				case 4 -> {
					int userChoiceFromReports = ReportsLogic.displayReportsScreen();
					if(userChoiceFromReports == 0) {
						ifContinue = false;
					}
				}
				case 0 -> ifContinue = false;
			}
		}
	}

	public static void sortAndPrintLedger(int userChoice) {

		//Switch statement to print out correct title based on user choice
		switch (userChoice) {
			case 1 -> System.out.println(Utils.PURPLE + "\t\t---ALL TRANSACTIONS---" + Utils.RESET);
			case 2 -> System.out.println(Utils.GREEN + "\t\t---ALL DEPOSITS---" + Utils.RESET);
			case 3 -> System.out.println(Utils.RED + "\t\t---ALL PAYMENTS---" + Utils.RESET);
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
						color = Utils.GREEN;
					}
				}

				System.out.printf("%s|%s|%s|%s|%s%.2f%s \n", t.getDate(), t.getTime(), t.getDescription(), t.getVendor(), color, t.getAmount(), Utils.RESET);
			}
			//Pause the app until user hits continue
			Utils.pauseApp();
		}
	}

	public static ArrayList<Transaction> loadLedger(int userChoice) {

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

				//Create new instance of models.Transaction object with data from each line of file
				Transaction newTransaction = new Transaction(lineData[0], lineData[1], lineData[2], lineData[3], Double.parseDouble(lineData[4]));

				//Add object to newTransaction ArrayList based on user request
				//No default because this is an internal call with clean data
				switch (userChoice) {
					//Adds all the transactions in the file
					case 1:
						transactionsList.add(newTransaction);
						break;
					//Adds only the deposits in the file
					case 2:
						if (!lineData[4].startsWith("-")) {
							transactionsList.add(newTransaction);
						}
						break;
					//Adds only the payments in the file
					case 3:
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
