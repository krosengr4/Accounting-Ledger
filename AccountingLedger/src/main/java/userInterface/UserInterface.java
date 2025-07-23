package userInterface;

import utilities.Utils;

public class UserInterface {

	public int displayMainMenu() {
		System.out.println(Utils.BLUE + "\n\t-----MAIN MENU-----\n" + Utils.RESET);
		System.out.println("""
				-----OPTIONS-----
				1 - Add a deposit
				2 - Add a payment (debit)
				3 - Go to the Ledger screen
				
				0 - Exit application
				""");

		return Utils.getUserInputIntMinMax("Enter your option: ", 0, 3);
	}

	public int displayLedgerScreen() {
		System.out.println(Utils.BLUE + "\n\t-----LEDGER-----\n" + Utils.RESET);
		System.out.println("""	
				-----OPTIONS-----
				1 - Display all entries
				2 - Display deposits
				3 - Display payments
				4 - Go to reports screen
				
				0 - Return to Main Menu
				""");

		return Utils.getUserInputIntMinMax(Utils.YELLOW + "Enter your option: ", 0, 4);
	}

	public int displayReportsScreen() {
		System.out.println(Utils.BLUE + "\n\t-----LEDGER REPORT-----\n" + Utils.RESET);
		System.out.println("""
				-----OPTIONS-----
				1 - View All Transactions This Month
				2 - View All Transactions last month
				3 - View All Transactions this year
				4 - View All Transactions last year
				5 - Search by vendor
				
				6 - Return to Ledger Screen
				0 - Return to Main Menu
				""");

		return Utils.getUserInputIntMinMax("Enter your option: ", 0, 6);
	}

}
