package logic;

import configurations.DatabaseConfig;
import data.TransactionDao;
import data.mysql.MySqlTransactionDao;
import models.Transaction;
import userInterface.UserInterface;
import utilities.Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Logic {

	static TransactionDao transactionDao = new MySqlTransactionDao(DatabaseConfig.setDataSource());
	static UserInterface ui = new UserInterface();

	public static void processMainMenu() {
		boolean ifContinue = true;

		while(ifContinue) {
			int userAction = ui.displayMainMenu();

			switch(userAction) {
				case 1 -> addTransaction("d");
				case 2 -> addTransaction("p");
				case 3 -> LedgerLogic.displayLedgerScreen();
				case 0 -> ifContinue = false;
			}
		}
	}

	private static void addTransaction(String userChoice) {
		String description = Utils.getUserInput("Enter the description: ");
		String vendor = Utils.getUserInput("Enter the vendor: ");
		double amount = Utils.getUserInputDouble("Enter the amount: ");

		if(userChoice.equalsIgnoreCase("p") && amount > 0) {
			amount = -amount;
		}

		Transaction transaction = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
		Transaction addedTransaction =  transactionDao.addTransaction(transaction);
		addedTransaction.print();
	}
}
