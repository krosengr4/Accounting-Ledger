package logic;

import configurations.DatabaseConfig;
import data.TransactionDao;
import data.mysql.MySqlTransactionDao;
import models.Transaction;
import userInterface.UserInterface;
import utilities.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LedgerLogic {

	static TransactionDao transactionDao = new MySqlTransactionDao(DatabaseConfig.setDataSource());
	static UserInterface ui = new UserInterface();

	public static void displayLedgerScreen() {
		boolean ifContinue = true;

		while(ifContinue) {
			int userChoice = ui.displayLedgerScreen();

			switch(userChoice) {
				case 1 -> processAllTransactions();
				case 2 -> processAllDeposits();
				case 3 -> processAllPayments();
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

	private static void processAllTransactions() {
		List<Transaction> transactionList = transactionDao.getAll();
		System.out.println(Utils.PURPLE + "\t\t---ALL TRANSACTIONS---" + Utils.RESET);
		Utils.designLine(40, false, "_");

		if(transactionList.isEmpty()) {
			System.out.println("There are no transactions do be displayed...");
		} else {
			for(Transaction transaction : transactionList) {
				transaction.print();
			}
		}

		Utils.pauseApp();
	}

	private static void processAllDeposits() {
		List<Transaction> despositsList = transactionDao.getDeposits();
		System.out.println(Utils.GREEN + "\t\t---ALL DEPOSITS---" + Utils.RESET);
		Utils.designLine(40, false, "_");

		if(despositsList.isEmpty()) {
			System.out.println("There are no deposits to be displayed...");
		} else {
			for(Transaction transaction : despositsList) {
				transaction.print();
			}
		}

		Utils.pauseApp();
	}

	private static void processAllPayments() {
		List<Transaction> paymentsList = transactionDao.getPayments();
		System.out.println(Utils.RED + "\t\t---ALL PAYMENTS---" + Utils.RESET);
		Utils.designLine(40, false, "_");

		if(paymentsList.isEmpty()) {
			System.out.println("There are no payments to be displayed...");
		} else {
			for(Transaction transaction : paymentsList) {
				transaction.print();
			}
		}

		Utils.pauseApp();
	}
}
