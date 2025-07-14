import java.io.FileWriter;
import java.io.IOException;

public class Logic {

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
		String logDateTime = Utils.getFullDate();

		String userDescription = Utils.getUserInput("Enter the description: ");
		String userVendor = Utils.getUserInput("Enter the vendor: ");
		Double userAmount = Double.parseDouble(Utils.getUserInput("Enter the amount: "));

		try {
			FileWriter writer = new FileWriter(Utils.logFile, true);

			if (userChoice.equalsIgnoreCase("d")) {
				writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|" + userAmount);
				writer.close();
				System.out.printf("%s|%s|%s|%.2f\n", logDateTime, userDescription, userVendor, userAmount);
				System.out.println(Utils.GREEN + "Success! Deposit transaction logged!" + Utils.RESET);
			} else if (userChoice.equalsIgnoreCase("p")) {
				writer.write("\n" + logDateTime + "|" + userDescription + "|" + userVendor + "|-" + userAmount);
				writer.close();

				System.out.printf("%s|%s|%s|-%.2f\n", logDateTime, userDescription, userVendor, -userAmount);
				System.out.println(Utils.GREEN + "Success! Payment transaction logged!" + Utils.RESET);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Utils.pauseApp();
	}

}
