package logic;

import configurations.DatabaseConfig;
import data.TransactionDao;
import data.mysql.MySqlTransactionDao;
import models.Transaction;
import userInterface.UserInterface;
import utilities.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReportsLogic {

	static TransactionDao transactionDao = new MySqlTransactionDao(DatabaseConfig.setDataSource());
	static UserInterface ui = new UserInterface();

	public static int displayReportsScreen() {
		boolean ifContinue = true;
		int userChoice = -1;

		while(ifContinue) {
			userChoice = ui.displayReportsScreen();

			switch(userChoice) {
				case 1 -> processThisMonth();
				case 2 -> processLastMonth();
				case 3 -> processThisYear();
				case 4 -> processLastYear();
				case 5 -> processByVendor();
				case 6, 0 -> ifContinue = false;
			}
		}
		return userChoice;
	}

	private static void processThisMonth() {
		int year = LocalDate.now().getYear();
		Month month = LocalDate.now().getMonth();
		int intMonth = month.getValue();

		String maxDay = "";

		switch(intMonth) {
			case 1, 3, 5, 7, 8, 10, 12 -> maxDay = "31";
			case 4, 6, 9, 11 -> maxDay = "30";
			case 2 -> maxDay = "28"; //<--- Not accounting for leap years
		}

		String minDate = year + "-" + intMonth + "-" + "01";
		String maxDate = year + "-" + intMonth + "-" + maxDay;

		List<Transaction> transactionList = transactionDao.getByDate(minDate, maxDate);
		System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS THIS MONTH---" + Utils.RESET);
		printData(transactionList);
	}

	private static void processLastMonth() {
		int year = LocalDate.now().getYear();
		Month month = LocalDate.now().getMonth();
		int intMonth = month.getValue();

		int lastMonth = 0;
		if(intMonth == 1) {
			lastMonth = 12;
			year = year - 1;
		} else {
			lastMonth = intMonth - 1;
		}

		String maxDay = "";
		switch(lastMonth) {
			case 1, 3, 5, 7, 8, 10, 12 -> maxDay = "31";
			case 4, 6, 9, 11 -> maxDay = "30";
			case 2 -> maxDay = "28"; //<--- Not accounting for leap years
		}

		String minDate = year + "-" + lastMonth + "-" + "01";
		String maxDate = year + "-" + lastMonth + "-" + maxDay;

		List<Transaction> transactionList = transactionDao.getByDate(minDate, maxDate);
		System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS LAST MONTH---" + Utils.RESET);
		printData(transactionList);
	}

	private static void processThisYear() {
		int year = LocalDate.now().getYear();
		String minDate = year + "-01-01";
		String maxDate = year + "-12-31";

		List<Transaction> transactionList = transactionDao.getByDate(minDate, maxDate);
		System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS THIS YEAR---" + Utils.RESET);
		printData(transactionList);
	}

	private static void processLastYear() {
		int year = LocalDate.now().getYear();
		int lastYear = year - 1;

		String minDate = lastYear + "-01-01";
		String maxDate = lastYear + "-12-31";

		List<Transaction> transactionList = transactionDao.getByDate(minDate, maxDate);
		System.out.println(Utils.PURPLE + "\t\t---TRANSACTIONS LAST YEAR---" + Utils.RESET);
		printData(transactionList);

	}

	private static void processByVendor() {
		String userVendorSearch = Utils.getUserInput("Enter the vendor: ");

		List<Transaction> transactionList = transactionDao.getByVendor(userVendorSearch);
		System.out.println(Utils.PURPLE + "\t---TRANSACTIONS WITH " + userVendorSearch.toUpperCase() + "---" + Utils.RESET);
		printData(transactionList);
	}

	private static void printData(List<Transaction> transactionList) {
		if(transactionList.isEmpty()) {
			System.out.println("There are no transactions for this month...");
		} else {
			for(Transaction transaction : transactionList) {
				transaction.print();
			}
		}

		Utils.pauseApp();
	}
}
